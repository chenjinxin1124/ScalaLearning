package slickTest.code

object Tables extends {
  // or just use object demo.Tables, which is hard-wired to the driver stated during generation
  val profile = slick.driver.PostgresDriver
}

import scala.concurrent.duration._
import scala.language.postfixOps
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.{Await, Future}
import Tables._
import Tables.profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

case class TestCase(id: Int, name: String)

class TestTable(tag: Tag) extends Table[TestCase](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name")

  def * = (id, name) <> (TestCase.tupled, TestCase.unapply)
}

object Connection extends App {
  val url = "jdbc:postgresql://localhost:5432/exampledb?user=dbuser&password=cjx123"
  val db = Database.forURL(url = url, driver = "org.postgresql.Driver")

  val MyTests = TableQuery[TestTable]

  //    MyTests forceInsertAll (TestCase(4,"d"))
  val t1 = TestCase(5, "e")
  val t2 = TestCase(4, "f")


  println("users:")
  val q = MyTests.map(_.name)
  Await.result(db.run(q.result).map { result => println(result.mkString("\n")) }, 60 seconds)

  val q2 = MyTests.filter(_.id < 4)
  Await.result(db.run(q2.result).map { result => println(result.mkString("\n")) }, 60 seconds)

  val q3 = MyTests.filter(_.id === 1)
  Await.result(db.run(q3.delete), 60 seconds)

  val q4 = MyTests.insertOrUpdate(t1)
  Await.result(db.run(q4), 60 seconds)

  val q5 = MyTests.insertOrUpdate(t2)
  Await.result(db.run(q5), 60 seconds)

  val t3 = TestCase(4, "d")
  val q6 = MyTests.insertOrUpdate(t3)
  Await.result(db.run(q6), 60 seconds)
}