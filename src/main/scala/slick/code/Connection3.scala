package slick.code

//object Tables extends {
//  // or just use object demo.Tables, which is hard-wired to the driver stated during generation
//  val profile = slick.driver.PostgresDriver
//}

import scala.concurrent.duration._
import scala.language.postfixOps
import slick.jdbc.JdbcBackend.Database

import scala.util.{Failure, Success}
import scala.concurrent.{Await, Future}
import Tables.profile.api._
import slick.basic.DatabasePublisher

import scala.concurrent.ExecutionContext.Implicits.global

//case class TestCase(id: Int, name: String)

class Users(tag: Tag) extends Table[TestCase](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name")

  def * = (id, name) <> (TestCase.tupled, TestCase.unapply)
}

object Connection3 extends App {
  val users = TableQuery[Users]
  val db = Database.forConfig("mydb")

  try {
    {
      //#materialize
      val q = for (c <- users) yield c.name
      val a = q.result
      val f: Future[Seq[String]] = db.run(a)
      val sql: String =a.statements.head
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };
    {
      //#transaction
      val a = (for {
        ns <- users.filter(_.id > 4).map(_.name).result
        _ <- DBIO.seq(ns.map(n => users.filter(_.name === n).delete): _*)
      } yield ()).transactionally
      val f: Future[Unit] = db.run(a)
    };
    {
      //#rollback
      val countAction = users.length.result
      val t1 = TestCase(5, "e")
      val t2 = TestCase(6, "f")
      val rollbackAction = (users ++= Seq(
        t1,t2
      )).flatMap { _ =>
        DBIO.failed(new Exception("Roll it back"))
      }.transactionally

      val errorHandleAction = rollbackAction.asTry.flatMap {
        case Failure(e: Throwable) => DBIO.successful(e.getMessage)
        case Success(_) => DBIO.successful("never reached")
      }

      val f = db.run(countAction zip errorHandleAction zip countAction).map {
        case ((initialCount, result), finalCount) =>
          // init: 5, final: 5, result: Roll it back
          println(s"init: ${initialCount}, final: ${finalCount}, result: ${result}")
          result
      }
    }
  } finally db.close
}