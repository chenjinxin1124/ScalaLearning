package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:43 19-8-21
  * @Description:
  */
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object TypecheckingQueries extends App {

  // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
  // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
  // on an our synchronous EC. See the chapter on connection handling for more info.
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",     // driver classname
    "jdbc:postgresql:world",     // connect URL (driver-specific)
    "postgres",                  // user
    "cjx123",                          // password
    ExecutionContexts.synchronous // just for testing
  )

  val y = xa.yolo
  import y._

  case class Country2(code: String, name: String, pop: Int, gnp: Option[BigDecimal])

  def biggerThan(minPop: Int) =
    sql"""
    select code, name, population, gnp
    from country
    where population > $minPop
  """.query[Country2]

  biggerThan(0).check.unsafeRunSync
  //   Query0[Session.App.Country2] defined at 06-Checking.md:83
  //   select code, name, population, gnp
  //   from country
  //   where population > ?
  //   ✓ SQL Compiles and TypeChecks
  //   ✓ P01 Int  →  INTEGER (int4)
  //   ✓ C01 code       CHAR    (bpchar)  NOT NULL  →  String
  //   ✓ C02 name       VARCHAR (varchar) NOT NULL  →  String
  //   ✓ C03 population INTEGER (int4)    NOT NULL  →  Int
  //   ✓ C04 gnp        NUMERIC (numeric) NULL      →  Option[BigDecimal]

  biggerThan(0).checkOutput.unsafeRunSync
  //   Query0[Session.App.Country2] defined at 06-Checking.md:83
  //   select code, name, population, gnp
  //   from country
  //   where population > ?
  //   ✓ SQL Compiles and TypeChecks
  //   ✓ C01 code       CHAR    (bpchar)  NOT NULL  →  String
  //   ✓ C02 name       VARCHAR (varchar) NOT NULL  →  String
  //   ✓ C03 population INTEGER (int4)    NOT NULL  →  Int
  //   ✓ C04 gnp        NUMERIC (numeric) NULL      →  Option[BigDecimal]

}
