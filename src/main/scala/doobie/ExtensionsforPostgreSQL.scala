package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:59 19-8-27
  * @Description:
  */

import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._

object ExtensionsforPostgreSQL extends App {
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
  println("------------------------------------------------------------------")

  //  create type myenum as enum ('foo', 'bar')
  object MyEnum extends Enumeration {
    val foo, bar = Value
  }

  implicit val MyEnumMeta = pgEnum(MyEnum, "myenum")
  sql"select 'foo'::myenum".query[MyEnum.Value].unique.transact(xa).unsafeRunSync

  sealed trait FooBar

  object FooBar {

    case object Foo extends FooBar

    case object Bar extends FooBar

    def toEnum(e: FooBar): String =
      e match {
        case Foo => "foo"
        case Bar => "bar"
      }

    def fromEnum(s: String): Option[FooBar] =
      Option(s) collect {
        case "foo" => Foo
        case "bar" => Bar
      }

  }

  implicit val FoobarMeta: Meta[FooBar] =
    pgEnumStringOpt("myenum", FooBar.fromEnum, FooBar.toEnum)

  sql"select 'foo'::myenum".query[FooBar].unique.transact(xa).unsafeRunSync

  val q = """
  copy country (name, code, population)
  to stdout (
    encoding 'utf-8',
    force_quote *,
    format csv
  )
  """

  val prog: ConnectionIO[Long] =
    PHC.pgGetCopyAPI(PFCM.copyOut(q, Console.out)) // return value is the row count

  val create: ConnectionIO[Unit] =
    sql"""
    CREATE TEMPORARY TABLE food (
      name       VARCHAR,
      vegetarian BOOLEAN,
      calories   INTEGER
    )
  """.update.run.void

  case class Food(name: String, isVegetarian: Boolean, caloriesPerServing: Int)

  val foods = List(
    Food("banana", true, 110),
    Food("cheddar cheese", true, 113),
    Food("Big Mac", false, 1120)
  )

  def insert[F[_]: Foldable](fa: F[Food]): ConnectionIO[Long] =
    sql"COPY food (name, vegetarian, calories) FROM STDIN".copyIn(fa)

  (create *> insert(foods)).transact(xa).unsafeRunSync
}
