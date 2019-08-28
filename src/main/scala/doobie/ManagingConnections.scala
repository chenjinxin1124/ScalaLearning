package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午8:38 19-8-27
  * @Description:
  */
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts

object ManagingConnections {
  println("-------------------------------Aboutb Transactors---------------------------------------------")
  println("-------------------------------About Threading---------------------------------------------")
  println("-------------------------------Using the JDBC DriverManager---------------------------------------------")

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
  println("-------------------------------Using a HikariCP Connection Pool---------------------------------------------")
  /*import doobie.hikari._
  object HikariApp extends IOApp {

    // Resource yielding a transactor configured with a bounded connect EC and an unbounded
    // transaction EC. Everything will be closed and shut down cleanly after use.
    val transactor: Resource[IO, HikariTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      te <- ExecutionContexts.cachedThreadPool[IO]    // our transaction EC
      xa <- HikariTransactor.newHikariTransactor[IO](
        "org.h2.Driver",                        // driver classname
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",   // connect URL
        "sa",                                   // username
        "",                                     // password
        ce,                                     // await connection here
        te                                      // execute JDBC operations here
      )
    } yield xa


    def run(args: List[String]): IO[ExitCode] =
      transactor.use { xa =>

        // Construct and run your server here!
        for {
          n <- sql"select 42".query[Int].unique.transact(xa)
          _ <- IO(println(n))
        } yield ExitCode.Success

      }

  }*/
  println("-------------------------------Using an existing DataSource---------------------------------------------")
  import javax.sql.DataSource

  // Resource yielding a DataSourceTransactor[IO] wrapping the given `DataSource`
  /*def transactor(ds: DataSource)(
    implicit ev: ContextShift[IO]
  ): Resource[IO, DataSourceTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      te <- ExecutionContexts.cachedThreadPool[IO]    // our transaction EC
    } yield Transactor.fromDataSource[IO](ds, ce, te)*/
  println("-------------------------------Using an Existing JDBC Connection---------------------------------------------")
  import java.sql.Connection

  // Resource yielding a Transactor[IO] wrapping the given `Connection`
  /*def transactor(c: Connection)(
    implicit ev: ContextShift[IO]
  ): Resource[IO, Transactor[IO]] =
    ExecutionContexts.cachedThreadPool[IO].map { te =>
      Transactor.fromConnection[IO](c, te)
    }*/
  println("-------------------------------Customizing Transactors---------------------------------------------")
  val testXa = Transactor.after.set(xa, HC.rollback)
  import doobie.free.connection.unit

//  val hiveXa = Transactor.strategy.set(xa, Strategy.default.copy(after = unit, oops = unit))
}
