package slick.code

//#imports
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob

import org.reactivestreams.Publisher

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.higherKinds
import scala.util.{Failure, Success}
import slick.basic.DatabasePublisher
import slick.jdbc.H2Profile.api._
//#imports

object Connection2 extends App {

//  case class TestCase(id: Int, name: String)

  class Users(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey)

    def name = column[String]("name")

    def * = (id, name)
  }

  val users = TableQuery[Users]
  if (false) {
    val dataSource = null.asInstanceOf[javax.sql.DataSource]
    val size = 42
    //#forDataSource
    val db = Database.forDataSource(dataSource: javax.sql.DataSource,
      Some(size: Int))
    //#forDataSource
  }
  if (false) {
    val dataSource = null.asInstanceOf[slick.jdbc.DatabaseUrlDataSource]
    //#forDatabaseURL
    val db = Database.forDataSource(dataSource: slick.jdbc.DatabaseUrlDataSource,
      None)
    //#forDatabaseURL
  }
  if (false) {
    val jndiName = ""
    val size = 42
    //#forName
    val db = Database.forName(jndiName: String, Some(size: Int))
    //#forName
  };
  {
    //#forConfig
    val db = Database.forConfig("mydb")
    //#forConfig
    db.close
  }
  //  ;{
  //    //#forURL2
  //    val db = Database.forURL("jdbc:postgresql://localhost:5432/exampledb?user=dbuser&password=cjx123",
  //      driver = "org.postgresql.Driver",
  //      executor = AsyncExecutor("test1", numThreads = 10, queueSize = 1000))
  //    //#forURL2
  //    db.close
  //  }
  //  val db = Database.forURL("jdbc:postgresql://localhost:5432/exampledb?user=dbuser&password=cjx123" , driver = "org.postgresql.Driver")
  val db = Database.forConfig("mydb")
  try {
    val lines = new ArrayBuffer[Any]()

    def println(s: Any) = lines += s;
    {
      //#materialize
      val q = for (c <- users) yield c.name
      val a = q.result
      val f: Future[Seq[String]] = db.run(a)

      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
      //#materialize
      Await.result(f, Duration.Inf)
    };
    {
      //#stream
      val q = for (c <- users) yield c.name
      val a = q.result
      val p: DatabasePublisher[String] = db.stream(a)

      // .foreach is a convenience method on DatabasePublisher.
      // Use Akka Streams for more elaborate stream processing.
      //#stream
      val f =
      //#stream
      p.foreach { s => println(s"Element: $s") }
      //#stream
      Await.result(f, Duration.Inf)
    };
    {
      //#transaction
      val a = (for {
        ns <- users.filter(_.id > 4).map(_.name).result
        _ <- DBIO.seq(ns.map(n => users.filter(_.name === n).delete): _*)
      } yield ()).transactionally

      val f: Future[Unit] = db.run(a)
      //#transaction
      Await.result(f, Duration.Inf)
    };
    {
      //#rollback
      val countAction = users.length.result

      val rollbackAction = (users ++= Seq(
        (6, "f"),
        (7, "g")
      )).flatMap { _ =>
        DBIO.failed(new Exception("Roll it back"))
      }.transactionally

      val errorHandleAction = rollbackAction.asTry.flatMap {
        case Failure(e: Throwable) => DBIO.successful(e.getMessage)
        case Success(_) => DBIO.successful("never reached")
      }

      // Here we show that that coffee count is the same before and after the attempted insert.
      // We also show that the result of the action is filled in with the exception's message.
      val f = db.run(countAction zip errorHandleAction zip countAction).map {
        case ((initialCount, result), finalCount) =>
          // init: 5, final: 5, result: Roll it back
          println(s"init: ${initialCount}, final: ${finalCount}, result: ${result}")
          result
      }

      //#rollback
      assert(Await.result(f, Duration.Inf) == "Roll it back")
    }
    lines.foreach(Predef.println _)
  } finally db.close

  //#simpleaction
  val getAutoCommit = SimpleDBIO[Boolean](_.connection.getAutoCommit)
  //#simpleaction
}