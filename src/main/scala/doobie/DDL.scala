package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:30 19-8-21
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object DDL extends App {

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

  /*println("-------------------------------Data Definition-----------------------------------------")
  val drop =
    sql"""
       DROP TABLE IF EXISTS person
      """.update.run

  val create =
    sql"""
       CREATE TABLE person (
          id SERIAL,
          name VARCHAR NOT NULL UNIQUE,
          age SMALLINT
        )
      """.update.run

  (drop, create).mapN(_ + _).transact(xa).unsafeRunSync*/

  case class Person(id: Long, name: String, age: Option[Short])

  /*println("-------------------------------Inserting-----------------------------------------")

  def insert1(name: String, age: Option[Short]): Update0 =
    sql"insert into person (name, age) values ($name, $age)".update

  insert1("Alice", Some(12)).run.transact(xa).unsafeRunSync
  insert1("Bob", None).quick.unsafeRunSync

  case class Person(id: Long, name: String, age: Option[Short])

  sql"select id, name, age from person".query[Person].quick.unsafeRunSync*/

  /*println("-------------------------------Updating-----------------------------------------")
  sql"update person set age = 15 where name = 'Alice'".update.quick.unsafeRunSync
  sql"select id, name, age from person".query[Person].quick.unsafeRunSync*/

  /*println("-------------------------------Retrieving Results-----------------------------------------")

  def insert2(name: String, age: Option[Short]): ConnectionIO[Person] =
    for {
      _ <- sql"insert into person (name, age) values ($name, $age)".update.run
      id <- sql"select lastval()".query[Long].unique
      p <- sql"select id, name, age from person where id = $id".query[Person].unique
    } yield p
  insert2("Jimmy", Some(42)).quick.unsafeRunSync

  def insert3(name: String, age: Option[Short]): ConnectionIO[Person] = {
    sql"insert into person (name, age) values ($name, $age)"
      .update
      .withUniqueGeneratedKeys("id", "name", "age")
  }
  insert3("Elvis", None).quick.unsafeRunSync*/

  /*val up = {
    sql"update person set age = age + 1 where age is not null"
      .update
      .withGeneratedKeys[Person]("id","name","age")
  }
  up.quick.unsafeRunSync
  up.quick.unsafeRunSync*/
  println("-------------------------------Batch Updates-----------------------------------------")
  /*val a = 1
  val b = "foo"
  sql"...$a $b ..."
  Update[(Int,String)]("... ? ? ...").run((a,b))

  type PersonInfo = (String, Option[Short])

  def insertMany(ps: List[PersonInfo]): ConnectionIO[Int] = {
    val sql = "insert into person (name, age) values (?, ?)"
    Update[PersonInfo](sql).updateMany(ps)
  }

  val data = List[PersonInfo](
    ("Frank", Some(12)),
    ("Daddy", None)
  )

  insertMany(data).quick.unsafeRunSync*/

  import fs2.Stream

  type PersonInfo = (String, Option[Short])
  def insertMany2(ps: List[PersonInfo]): Stream[ConnectionIO, Person] = {
    val sql = "insert into person (name, age) values (?, ?)"
    Update[PersonInfo](sql).updateManyWithGeneratedKeys[Person]("id", "name", "age")(ps)
  }

  val data2 = List[PersonInfo](
    ("Banjo", Some(39)),
    ("Skeeter", None),
    ("Jim-Bob", Some(12))
  )

  insertMany2(data2).quick.unsafeRunSync

}
