package slickTest.akkaHttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, get, parameters, path, pathPrefix, _}
import akka.stream.ActorMaterializer
import slick.driver.PostgresDriver.api._
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery
import slickTest.code.Scores

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn
import scala.language.postfixOps
import scala.util.{Failure, Success}

object httpSlick extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  val db = Database.forConfig("mydb")
  val scores = TableQuery[Scores]

  val route =
    pathPrefix("select") {
      path("scores") {
        (get & parameters('start.as[Int] ? -1, 'end.as[Int] ? 10)) { (start, end) =>
          val q2 = scores.filter(_.id > start)
          val res: Future[Seq[(Int, Int)]] = db.run(q2.result)
          onComplete(res) {
            case Success(x) => complete(x.toString)
            case Failure(e) => complete(e.toString)
          }
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/select/scores\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}