package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:54 19-8-23
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object Logging extends App {
  println("----------------------------------------Setting Up------------------------------------------------------------")
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:world", // connect URL (driver-specific)
    "postgres", // user
    "cjx123", // password
    ExecutionContexts.synchronous // just for testing
  )
  /*println("----------------------------------------Basic Statement Logging------------------------------------------------------------")
  def byName(pat: String)={
    sql"select name, code from country where name like $pat"
      .queryWithLogHandler[(String, String)](LogHandler.jdkLogHandler)
      .to[List]
      .transact(xa)
  }
  byName("U%").unsafeRunSync
  println("----------------------------------------Implicit Logging------------------------------------------------------------")
  implicit val han = LogHandler.jdkLogHandler

  def byName2(pat: String) = {
    sql"select name, code from country where name like $pat"
      .query[(String, String)] // handler will be picked up here
      .to[List]
      .transact(xa)
  }*/
  println("----------------------------------------Writing Your Own LogHandler------------------------------------------------------------")

  val trivial = LogHandler(e => Console.println("*** " + e))
  sql"select 42".queryWithLogHandler[Int](trivial).unique.transact(xa).unsafeRunSync

  import java.util.logging.Logger
  import doobie.util.log.{Success, ProcessingFailure, ExecFailure}

  val jdkLogHandler: LogHandler = {
    val jdkLogger = Logger.getLogger(getClass.getName)
    LogHandler {

      case Success(s, a, e1, e2) =>
        jdkLogger.info(
          s"""Successful Statement Execution:
             |
             |${s.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n ")}
             |
             |arguments = [${a.mkString(", ")}]
             |  elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (${(e1 + e2).toMillis} ms total)
           """.stripMargin)

      case ProcessingFailure(s, a, e1, e2, t) =>
        jdkLogger.severe(
          s"""Failed Resultset Processing:
             |
             |${s.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n ")}
             |
             |arguments = [${a.mkString(", ")}]
             |  elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (${(e1 + e2).toMillis} ms total)
             |  failure = ${t.getMessage}
           """.stripMargin)

      case ExecFailure(s, a, e1, t) =>
        jdkLogger.severe(
          s"""Failed Statement Execution:
             |
             |${s.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n ")}
             |
             |arguments = [${a.mkString(", ")}]
             |   elapsed = ${e1.toMillis} ms exec (failed)
             |   failure = ${t.getMessage}
           """.stripMargin)
    }
  }

}
