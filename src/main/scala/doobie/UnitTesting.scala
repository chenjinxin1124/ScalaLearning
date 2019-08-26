package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:44 19-8-26
  * @Description:
  */
import doobie._
import doobie.implicits._
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._
object UnitTesting extends App {
  println("--------------------------------------Setting Up--------------------------------------------")
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

  case class Country(code: Int, name: String, pop: Int, gnp: Double)

  val trivial =
    sql"""
    select 42, 'foo'::varchar
  """.query[(Int, String)]

  def biggerThan(minPop: Short) =
    sql"""
    select code, name, population, gnp, indepyear
    from country
    where population > $minPop
  """.query[Country]

  def update(oldName: String, newName: String) =
    sql"""
    update country set name = $newName where name = $oldName
  """.update
  println("--------------------------------------The Specs2 Package--------------------------------------------")
  import org.specs2.mutable.Specification

  object AnalysisTestSpec extends Specification with doobie.specs2.IOChecker {

    val transactor = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "cjx123"
    )

    check(trivial)
    check(biggerThan(0))
    check(update("", ""))

  }

  import _root_.specs2.{ run => runTest }
  import _root_.org.specs2.main.{ Arguments, Report }

  // Run a test programmatically. Usually you would do this from sbt, bloop, etc.
  runTest(AnalysisTestSpec)(Arguments(report = Report(_color = Some(false))))
  println("--------------------------------------The ScalaTest Package--------------------------------------------")

  /*import org.scalatest._

  class AnalysisTestScalaCheck extends FunSuite with Matchers with doobie.scalatest.IOChecker {

    override val colors = doobie.util.Colors.None // just for docs

    val transactor = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "cjx123"
    )

    test("trivial")    { check(trivial)        }
    test("biggerThan") { check(biggerThan(0))  }
    test("update")     { check(update("", "")) }

  }
  (new AnalysisTestScalaCheck).execute(color = false)
  */

}
