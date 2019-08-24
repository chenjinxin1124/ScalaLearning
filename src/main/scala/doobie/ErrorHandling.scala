package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午8:50 19-8-23
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object ErrorHandling extends App {
  println("------------------------------------Setting Up--------------------------------------------")
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql:world",
    "postgres",
    "cjx123",
    ExecutionContexts.synchronous
  )

  val y = xa.yolo

  import y._

  println("------------------------------------Example: Unique Constraint Violation--------------------------------------------")
  List(
    sql"""DROP TABLE IF EXISTS person""",
    sql"""CREATE TABLE person (
      id SERIAL,
      name VARCHAR NOT NULL UNIQUE
      )"""
  ).traverse(_.update.quick).void.unsafeRunSync

  case class Person(id: Int, name: String)

  def insert(s: String): ConnectionIO[Person] = {
    sql"insert into person (name) values ($s)"
      .update
      .withUniqueGeneratedKeys("id", "name")
  }

  insert("bob").quick.unsafeRunSync
  try {
    insert("bob").quick.unsafeRunSync
  } catch {
    case e: java.sql.SQLException =>
      println(e.getMessage)
      println(e.getSQLState)
  }
  //  0 row(s) updated
  //  0 row(s) updated
  //  Person(1,bob)
  //  ERROR: duplicate key value violates unique constraint "person_name_key"
  //  详细：Key (name)=(bob) already exists.
  //  23505

  import doobie.postgres._

  def safeInsert(s: String): ConnectionIO[Either[String, Person]] =
    insert(s).attemptSomeSqlState {
      case sqlstate.class23.UNIQUE_VIOLATION => "Oops!"
    }

  safeInsert("bob").quick.unsafeRunSync
  //  Left(Oops!)
  safeInsert("steve").quick.unsafeRunSync
  //  Right(Person(4,steve))
}
