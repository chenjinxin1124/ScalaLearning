package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:32 19-8-20
  * @Description:
  */

import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._
import fs2.Stream

object SelectData extends App {
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

  println("---------------------------------select name from country . top 5----------------------------------------------")
  sql"select name from country"
    .query[String] // Query0[String]
    .to[List] // ConnectionIO[List[String]]
    .transact(xa) // IO[List[String]]
    .unsafeRunSync // List[String]
    .take(5) // List[String]
    .foreach(println) // Unit
  // Afghanistan
  // Netherlands
  // Netherlands Antilles
  // Albania
  // Algeria
  println("---------------------------------select top 5 name from country----------------------------------------------")
  sql"select name from country"
    .query[String] // Query0[String]
    .stream // Stream[ConnectionIO, String]
    .take(5) // Stream[ConnectionIO, String]
    .compile.toList // ConnectionIO[List[String]]
    .transact(xa) // IO[List[String]]
    .unsafeRunSync // List[String]
    .foreach(println) // Unit
  // Afghanistan
  // Netherlands
  // Netherlands Antilles
  // Albania
  // Algeria
  println("--------------------------------------quick-----------------------------------------")
  val y = xa.yolo // a stable reference is required
  import y._

  sql"select code, name, population, gnp from country"
    .query[(String, String, Int, Option[Double])]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   (AFG,Afghanistan,22720000,Some(5976.0))
  //   (NLD,Netherlands,15864000,Some(371362.0))
  //   (ANT,Netherlands Antilles,217000,Some(1941.0))
  //   (ALB,Albania,3401200,Some(3205.0))
  //   (DZA,Algeria,31471000,Some(49982.0))
  println("-----------------------------------shapeless--------------------------------------------")

  import shapeless._

  sql"select code, name, population, gnp from country"
    .query[String :: String :: Int :: Option[Double] :: HNil]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   AFG :: Afghanistan :: 22720000 :: Some(5976.0) :: HNil
  //   NLD :: Netherlands :: 15864000 :: Some(371362.0) :: HNil
  //   ANT :: Netherlands Antilles :: 217000 :: Some(1941.0) :: HNil
  //   ALB :: Albania :: 3401200 :: Some(3205.0) :: HNil
  //   DZA :: Algeria :: 31471000 :: Some(49982.0) :: HNil
  println("---------------------------------------.T----------------------------------------")

  import shapeless.record.Record

  type Rec = Record.`'code -> String, 'name -> String, 'pop -> Int, 'gnp -> Option[Double]`.T

  sql"select code, name, population, gnp from country"
    .query[Rec]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   AFG :: Afghanistan :: 22720000 :: Some(5976.0) :: HNil
  //   NLD :: Netherlands :: 15864000 :: Some(371362.0) :: HNil
  //   ANT :: Netherlands Antilles :: 217000 :: Some(1941.0) :: HNil
  //   ALB :: Albania :: 3401200 :: Some(3205.0) :: HNil
  //   DZA :: Algeria :: 31471000 :: Some(49982.0) :: HNil
  println("------------------------------------case class Country-------------------------------------------")

  case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

  sql"select code, name, population, gnp from country"
    .query[Country]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   Country(AFG,Afghanistan,22720000,Some(5976.0))
  //   Country(NLD,Netherlands,15864000,Some(371362.0))
  //   Country(ANT,Netherlands Antilles,217000,Some(1941.0))
  //   Country(ALB,Albania,3401200,Some(3205.0))
  //   Country(DZA,Algeria,31471000,Some(49982.0))
  println("------------------------------case class Code-----Country2--------------------------------------------")

  case class Code(code: String)

  case class Country2(name: String, pop: Int, gnp: Option[Double])

  sql"select code, name, population, gnp from country"
    .query[(Code, Country2)]
    .stream
    .take(5)
    .quick
    .unsafeRunSync
  //   (Code(AFG),Country2(Afghanistan,22720000,Some(5976.0)))
  //   (Code(NLD),Country2(Netherlands,15864000,Some(371362.0)))
  //   (Code(ANT),Country2(Netherlands Antilles,217000,Some(1941.0)))
  //   (Code(ALB),Country2(Albania,3401200,Some(3205.0)))
  //   (Code(DZA),Country2(Algeria,31471000,Some(49982.0)))
  println("---------------------------------------Map----------------------------------------")
  sql"select code, name, population, gnp from country"
    .query[(Code, Country2)]
    .stream.take(5)
    .compile.toList
    .map(_.toMap)
    .quick
    .unsafeRunSync
  //   Map(Code(ANT) -> Country2(Netherlands Antilles,217000,Some(1941.0)), Code(DZA) -> Country2(Algeria,31471000,Some(49982.0)), Code(ALB) -> Country2(Albania,3401200,Some(3205.0)), Code(NLD) -> Country2(Netherlands,15864000,Some(371362.0)), Code(AFG) -> Country2(Afghanistan,22720000,Some(5976.0)))
  println("---------------------------------------Final Streaming----------------------------------------")
  val p: Stream[IO, Country2] =
    sql"select name, population, gnp from country"
      .query[Country2] // Query0[Country2]
      .stream          // Stream[ConnectionIO, Country2]
      .transact(xa)    // Stream[IO, Country2]
  // p: Stream[IO, Country2] = Stream(..)    // Stream[IO, Country2]
  p.take(5).compile.toVector.unsafeRunSync.foreach(println)
  // Country2(Afghanistan,22720000,Some(5976.0))
  // Country2(Netherlands,15864000,Some(371362.0))
  // Country2(Netherlands Antilles,217000,Some(1941.0))
  // Country2(Albania,3401200,Some(3205.0))
  // Country2(Algeria,31471000,Some(49982.0))
  println("---------------------------------------Diving Deeper----------------------------------------")
  val proc = HC.stream[(Code, Country2)](
    "select code, name, population, gnp from country", // statement
    ().pure[PreparedStatementIO],                      // prep (none)
    512                                                // chunk size
  )
  // proc: Stream[ConnectionIO, (Code, Country2)] = Stream(..)

  proc.take(5)        // Stream[ConnectionIO, (Code, Country2)]
    .compile.toList // ConnectionIO[List[(Code, Country2)]]
    .map(_.toMap)   // ConnectionIO[Map[Code, Country2]]
    .quick
    .unsafeRunSync
  //   Map(Code(ANT) -> Country2(Netherlands Antilles,217000,Some(1941.0)), Code(DZA) -> Country2(Algeria,31471000,Some(49982.0)), Code(ALB) -> Country2(Albania,3401200,Some(3205.0)), Code(NLD) -> Country2(Netherlands,15864000,Some(371362.0)), Code(AFG) -> Country2(Afghanistan,22720000,Some(5976.0)))
}
