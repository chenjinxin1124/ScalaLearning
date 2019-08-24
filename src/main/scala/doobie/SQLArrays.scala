package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午7:30 19-8-23
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.postgres._
import doobie.postgres.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object SQLArrays extends App {
  println("---------------------------------Setting Up--------------------------------------------")
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

  println("---------------------------------Reading and Writing Arrays--------------------------------------------")
  val drop = sql"DROP TABLE IF EXISTS person".update.quick

  val create =
    sql"""
      CREATE TABLE person (
      id SERIAL,
      name VARCHAR NOT NULL UNIQUE,
      pets VARCHAR[] NOT NULL
      )
      """.update.quick

  (drop *> create).unsafeRunSync

  case class Person(id: Long, name: String, pets: List[String])

  def insert(name: String, pets: List[String]): ConnectionIO[Person] = {
    sql"insert into person (name,pets) values ($name,$pets)"
      .update
      .withUniqueGeneratedKeys("id", "name", "pets")
  }

  insert("Bob", List("Nixon", "Slappy")).quick.unsafeRunSync
  //   Person(1,Bob,List(Nixon, Slappy))
  insert("Alice", Nil).quick.unsafeRunSync
  //   Person(2,Alice,List())
  println("---------------------------------Lamentations of NULL--------------------------------------------")
  //  在前两种情况下，读取NULL单元格会导致NullableCellRead异常。
  sql"select array['foo','bar','baz']".query[List[String]].quick.unsafeRunSync
  sql"select array['foo','bar','baz']".query[Option[List[String]]].quick.unsafeRunSync
  sql"select array['foo',NULL,'baz']".query[List[Option[String]]].quick.unsafeRunSync
  sql"select array['foo',NULL,'baz']".query[Option[List[Option[String]]]].quick.unsafeRunSync
  //  List(foo, bar, baz)
  //  Some(List(foo, bar, baz))
  //  List(Some(foo), None, Some(baz))
  //  Some(List(Some(foo), None, Some(baz)))
}
