package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午8:55 19-8-21
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object ParameterizedQueries extends App {
  // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
  // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
  // on an our synchronous EC. See the chapter on connection handling for more info.
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:world", // connect URL (driver-specific)
    "postgres", // user
    "cjx123", // password
    ExecutionContexts.synchronous // just for testing
  )
  val y = xa.yolo

  import y._

  println("--------------------------------Adding a Parameter---------------------------------------------------------------------------------")

  case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

  sql"select code, name, population, gnp from country"
    .query[Country]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   Country(AFG,Afghanistan,22720000,Some(5976.0))
  //   Country(NLD,Netherlands,15864000,Some(371362.0))
  //   Country(ANT,Netherlands Antilles,217000,Some(1941.0))
  //   Country(ALB,Albania,3401200,Some(3205.0))
  //   Country(DZA,Algeria,31471000,Some(49982.0))

  println("--------------------------------PreparedStatement---------------------------------------------------------------------------------")

  def biggerThan(minPop: Int) = sql"""
  select code, name, population, gnp
  from country
  where population > $minPop
""".query[Country]

  biggerThan(150000000).quick.unsafeRunSync // Let's see them all
  //   Country(BRA,Brazil,170115000,Some(776739.0))
  //   Country(IDN,Indonesia,212107000,Some(84982.0))
  //   Country(IND,India,1013662000,Some(447114.0))
  //   Country(CHN,China,1277558000,Some(982268.0))
  //   Country(PAK,Pakistan,156483000,Some(61289.0))
  //   Country(USA,United States,278357000,Some(8510700.0))

  println("--------------------------------Multiple Parameters---------------------------------------------------------------------------------")

  def populationIn(range: Range) = sql"""
  select code, name, population, gnp
  from country
  where population > ${range.min}
  and   population < ${range.max}
""".query[Country]

  populationIn(150000000 to 200000000).quick.unsafeRunSync
  //   Country(BRA,Brazil,170115000,Some(776739.0))
  //   Country(PAK,Pakistan,156483000,Some(61289.0))

  println("--------------------------------Dealing with IN Clauses---------------------------------------------------------------------------------")

  def populationIn(range: Range, codes: NonEmptyList[String]) = {
    val q = fr"""
    select code, name, population, gnp
    from country
    where population > ${range.min}
    and   population < ${range.max}
    and   """ ++ Fragments.in(fr"code", codes) // code IN (...)
    q.query[Country]
  }

  populationIn(100000000 to 300000000, NonEmptyList.of("USA", "BRA", "PAK", "GBR")).quick.unsafeRunSync
  //   Country(BRA,Brazil,170115000,Some(776739.0))
  //   Country(PAK,Pakistan,156483000,Some(61289.0))
  //   Country(USA,United States,278357000,Some(8510700.0))

  println("--------------------------------Diving Deeper---------------------------------------------------------------------------------")

  import fs2.Stream

  val q =
    """
      |select code, name, population, gnp
      |from country
      |where population > ?
      |and population < ?
    """.stripMargin

  def proc(range: Range): Stream[ConnectionIO, Country] =
    HC.stream[Country](q, HPS.set((range.min, range.max)), 512)

  proc(150000000 to 200000000).quick.unsafeRunSync
  //   Country(BRA,Brazil,170115000,Some(776739.0))
  //   Country(PAK,Pakistan,156483000,Some(61289.0))

  Write[(String, Boolean)]
  // res5: Write[(String, Boolean)] = doobie.util.Write@52b2bba1
  Write[Country]
  // res6: Write[Country] = doobie.util.Write@27a1dcc7
  // Set parameters as (String, Boolean) starting at index 1 (default)
  HPS.set(("foo", true))

  // Set parameters as (String, Boolean) starting at index 1 (explicit)
  HPS.set(1, ("foo", true))

  // Set parameters individually
  HPS.set(1, "foo") *> HPS.set(2, true)

  // Or out of order, who cares?
  HPS.set(2, true) *> HPS.set(1, "foo")

  FPS.setString(1, "foo") *> FPS.setBoolean(2, true)
}
