package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:58 19-8-20
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.effect._
import cats.implicits._

object JdbcDemo extends App {
  val program1 = 42.pure[ConnectionIO]
  // program1: ConnectionIO[Int] = Pure(42)
  // println(program1)

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:world", // connect URL (driver-specific)
    "postgres", // user
    "cjx123", // password
    ExecutionContexts.synchronous // just for testing
  )

  val io = program1.transact(xa)
  // io: IO[Int] = Async(
  //   cats.effect.internals.IOBracket$$$Lambda$15047/791253558@10d9c307,
  //   false
  // )
  println(io.unsafeRunSync)
  // res0: Int = 42
  //-------------------------------------------------------------------------------------------------------------
  val program2 = sql"select 42".query[Int].unique
  // program2: ConnectionIO[Int] = Suspend(
  //   BracketCase(
  //     Suspend(PrepareStatement("select 42")),
  //     doobie.hi.connection$$$Lambda$15121/322757691@37408bc2,
  //     cats.effect.Bracket$$Lambda$15123/1232635455@125bc8ed
  //   )
  // )
  val io2 = program2.transact(xa)
  // io2: IO[Int] = Async(
  //   cats.effect.internals.IOBracket$$$Lambda$15047/791253558@737a627e,
  //   false
  // )
  println(io2.unsafeRunSync)
  // res1: Int = 42
  //-------------------------------------------------------------------------------------------------------------
  val program3: ConnectionIO[(Int, Double)] =
  for {
    a <- sql"select 42".query[Int].unique
    b <- sql"select random()".query[Double].unique
  } yield (a, b)
  println(program3.transact(xa).unsafeRunSync)
  // res2: (Int, Double) = (42, 0.7580032763071358)
  val program3a = {
    val a: ConnectionIO[Int] = sql"select 42".query[Int].unique
    val b: ConnectionIO[Double] = sql"select random()".query[Double].unique
    (a, b).tupled
  }
  println(program3a.transact(xa).unsafeRunSync)
  // res3: (Int, Double) = (42, 0.42085209023207426)

  val valuesList = program3a.replicateA(5)
  // valuesList: ConnectionIO[List[(Int, Double)]] = FlatMapped(
  //   FlatMapped(
  //     FlatMapped(
  //       Suspend(
  //         BracketCase(
  //           Suspend(PrepareStatement("select 42")),
  //           doobie.hi.connection$$$Lambda$15121/322757691@76d553db,
  //           cats.effect.Bracket$$Lambda$15123/1232635455@3a05e14c
  //         )
  //       ),
  //       cats.FlatMap$$Lambda$15114/828707268@1a4c0de0
  //     ),
  //     cats.Monad$$Lambda$15037/441700193@19c621a2
  //   ),
  //   cats.FlatMap$$Lambda$15171/597342699@4551e2ca
  // )
  val result = valuesList.transact(xa)
  // result: IO[List[(Int, Double)]] = Async(
  //   cats.effect.internals.IOBracket$$$Lambda$15047/791253558@50cb74ad,
  //   false
  // )
  result.unsafeRunSync.foreach(println)
  // (42,0.8911416241899133)
  // (42,0.9455912839621305)
  // (42,0.42450264655053616)
  // (42,0.9883306957781315)
  // (42,0.3037430923432112)
  //-------------------------------------------------------------------------------------------------------------
  import cats.~>
  import cats.data.Kleisli
  import doobie.free.connection.ConnectionOp
  import java.sql.Connection

  val interpreter = KleisliInterpreter[IO](ExecutionContexts.synchronous).ConnectionInterpreter
  // interpreter: ConnectionOp ~> Kleisli[IO, Connection, γ$9$] = doobie.free.KleisliInterpreter$$anon$10@2a125059
  val kleisli = program1.foldMap(interpreter)
  // kleisli: Kleisli[IO, Connection, Int] = Kleisli(
  //   cats.data.KleisliFlatMap$$Lambda$15070/233830355@2f1bf88
  // )
  val io3 = IO(null: java.sql.Connection) >>= kleisli.run
  // io3: IO[Int] = Bind(
  //   Delay(<function0>),
  //   cats.data.KleisliFlatMap$$Lambda$15070/233830355@2f1bf88
  // )
  println(io3.unsafeRunSync) // sneaky; program1 never looks at the connection
  // res5: Int = 42
  //-------------------------------------------------------------------------------------------------------------
  import monix.eval.Task

  val mxa = Transactor.fromDriverManager[Task](
    "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "cjx123"
  )

  val res = sql"select 43".query[Int].unique.transact(mxa)
  println(res.toString())
}
