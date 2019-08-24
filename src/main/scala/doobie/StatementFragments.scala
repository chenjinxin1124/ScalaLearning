package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:24 19-8-22
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object StatementFragments extends App {
  println("-----------------------------------------Setting Up------------------------------------")
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

  println("-----------------------------------------Composing SQL literals------------------------")
  val a = fr"select name from country"
  val b = fr"where code  = 'USA'"
  val c = a ++ b
  c.query[String].unique.quick.unsafeRunSync

  def whereCode(s: String) = fr"where code = $s"

  val fra = whereCode("FRA")
  (fr"select name from country" ++ fra).query[String].quick.unsafeRunSync

  def count(table: String) = (fr"select count(*) from" ++ Fragment.const(table)).query[Int].unique

  count("city").quick.unsafeRunSync

  println("-------------------------------------Whitespace handling--------------------------------------------------")
  fr"IN (" ++ List(1, 2, 3).map(n => fr"$n").intercalate(fr",") ++ fr")"
  // res3: Fragment = Fragment("IN ( ? , ? , ? ) ")
  fr0"IN (" ++ List(1, 2, 3).map(n => fr0"$n").intercalate(fr",") ++ fr")"
  // res4: Fragment = Fragment("IN (?, ?, ?) ")
  println("-------------------------------------The Fragments Module--------------------------------------------------")

  import Fragments.{in, whereAndOpt}

  case class Info(name: String, code: String, population: Int)

  def select(name: Option[String], pop: Option[Int], codes: List[String], limit: Long) = {
    val f1 = name.map(s => fr"name LIKE $s")
    val f2 = pop.map(n => fr"population > $n")
    val f3 = codes.toNel.map(cs => in(fr"code", cs))

    val q: Fragment =
      fr"SELECT name,code,population FROM country" ++
        whereAndOpt(f1, f2, f3) ++
        fr"LIMIT $limit"

    q.query[Info]

  }

  select(None, None, Nil,10).check.unsafeRunSync
  select(Some("U%"), None, Nil,10).check.unsafeRunSync
  select(Some("U%"), Some(12345), List("FRA","GBR"),10).check.unsafeRunSync

}
