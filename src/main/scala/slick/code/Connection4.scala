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
import slick.code.Connection.{db, q}

import scala.concurrent.ExecutionContext.Implicits.global

//case class TestCase(id: Int, name: String)
class Scores(tag: Tag) extends Table[(Int, Int)](tag, "scores") {
  def id = column[Int]("id", O.PrimaryKey)

  def score = column[Int]("score")

  def * = (id, score)
}

object Connection4 extends App {
  val users = TableQuery[Users]
  val scores = TableQuery[Scores]
  val db = Database.forConfig("mydb")

  try {
    /*{
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
      val q = users.filter(_.id === 3)
      Await.result(db.run(q.result).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String =q.result.statements.head
      println(s"sql : $sql")
    };
    {
      val q = users.drop(1).take(3)
      Await.result(db.run(q.result).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String =q.result.statements.head
      println(s"sql : $sql")
    };
    {
      val q = scores.sortBy(_.score)
      Await.result(db.run(q.result).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String =q.result.statements.head
      println(s"sql : $sql")
    };*/
    {
      val optionFromPrice = Option(3)
      val q = scores.filterOpt(optionFromPrice)(_.score > _)
      Await.result(db.run(q.result).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String =q.result.statements.head
      println(s"sql : $sql")
    };

    //join
    /*{
      val crossJoin = for {
        (u, s) <- users join scores
      } yield (u, s)
      val a = crossJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/
    /*{
      val innerJoin = for {
        (u, s) <- users join scores on (_.id === _.id)
      } yield (u, s)
      val a = innerJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/
    /*{
      val fullOuterJoin = for {
        (u, s) <- users joinFull scores on (_.id === _.id)
      } yield (u, s)
      val a = fullOuterJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/

    //Monadic
    /*{//等价于 join
      val monadicCrossJoin = for {
        u <- users
        s <- scores
      } yield (u, s)
      val a = monadicCrossJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/
    /*{ //等价于 inner join
      val monadicCrossJoin = for {
        u <- users
        s <- scores if u.id === s.id
      } yield (u, s)
      val a = monadicCrossJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/

    //Zip
    /*{ //等价于 inner join
      val zipJoinQuery = for {
        (u, s) <- users zip scores
      } yield (u, s)
      val a = zipJoinQuery.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };
    { //等价于 inner join
      val zipWithJoin = for {
        res <- users.zipWith(scores,(u:Users,s:Scores)=>(u,s))
      } yield (res)
      val a = zipWithJoin.result
      Await.result(db.run(a).map { result => println(result.mkString("\n")) }, 60 seconds)
      val sql: String = a.statements.head
      println(s"sql : $sql")
    };*/

    //Unions
    /*{//过滤重复值
      val q1 = users.filter(_.id > 2)
      val q2 = users.filter(_.id > 2)
      val unionQuery = q1 union q2
      val a = unionQuery.result
      val f: Future[Seq[TestCase]] = db.run(a)
      val sql: String =a.statements.head
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };
    {//只合并，不去重
      val q1 = users.filter(_.id > 2)
      val q2 = users.filter(_.id > 2)
      val unionAllQuery = q1 ++ q2
      val a = unionAllQuery.result
      val f: Future[Seq[TestCase]] = db.run(a)
      val sql: String =a.statements.head
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };*/

    //Aggregation
    /*{//min
      val q = scores.map(_.score)
      val q1 = q.min
      val a = q1.result
      val f: Future[Option[Int]] = db.run(a)
      val sql: String =a.statements.head
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };
    {//min
      val q = scores.map(_.score)
      val q1 = q.avg
      val a = q1.result
      val f: Future[Option[Int]] = db.run(a)
      val sql: String =a.statements.head
      println(s"sql2 : $sql")
      f.onComplete {
        case Success(s) => println(s"Result2: $s")
        case Failure(t) => t.printStackTrace()
      }
    };*/
    /*{ //count(*)
      val q = scores.length
      val a = q.result
      val f: Future[Int] = db.run(a)
      val sql: Option[String] = a.statements.headOption
      println(s"sql1 : $sql")
      f.onComplete {
        case Success(s) => println(s"Result1: $s")
        case Failure(t) => t.printStackTrace()
      }
    };
    { //
      val q = scores.exists
      val a = q.result
      val f: Future[Boolean] = db.run(a)
      val sql: Option[String] = a.statements.headOption
      println(s"sql2 : $sql")
      f.onComplete {
        case Success(s) => println(s"Result2: $s")
        case Failure(t) => t.printStackTrace()
      }
    };*/

    //Inserting
    /*{ //
      val insertActions = DBIO.seq(
        users += (TestCase(6, "f")),

        users ++= Seq(
          TestCase(7, "g"),
          TestCase(8, "h")
        ),

        users.map(u => (u.id, u.name)) += (9, "i")
      )
      val a = insertActions
      val f: Future[Unit] = db.run(a)
      val sql = users.insertStatement
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };
    { //
      val insertActions = DBIO.seq(
        scores += (6, 1),

        scores ++= Seq(
          (7, 3),
          (8, 5)
        ),

        scores.map(u => (u.id, u.score)) += (9, 2)
      )
      val a = insertActions
      val f: Future[Unit] = db.run(a)
      val sql = scores.insertStatement
      println(s"sql : $sql")
      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }
    };*/

    //Updating
    /*{
      val q = for { s <- scores if s.score < 2 } yield s.score
      val updateAction = q.update(0)
      val f: Future[Int] = db.run(updateAction)
      val sql: String = q.updateStatement
      println(sql)
    }*/

    //Upserting
    /*{
      val updated = scores.insertOrUpdate((1, 1))
      val f: Future[Int] = db.run(updated)
      val sql = updated.statements.head
      println(sql)
    }*/
    /*{
      val isRoast = true
      val isEspresso = false

      val q6 = users
        .filterIf(isRoast)(_.id > 2)
        .filterIf(isEspresso)(_.id > 2)
      println(q6.result.statements.head)
    }*/

  } finally db.close
}