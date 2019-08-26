package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:21 19-8-24
  * @Description:
  */

import cats._, cats.data._, cats.implicits._
import doobie._, doobie.implicits._
import io.circe._, io.circe.jawn._, io.circe.syntax._
import java.awt.Point
import org.postgresql.util.PGobject

object CustomMappings {
  println("---------------------------------Setup-----------------------------------------------")
  println("---------------------------------When do I need a custom type mapping?-----------------------------------------------")
  println("---------------------------------Single-Column Type Mappings----------------------------------------------")
  import NatModule._

  implicit val natGet: Get[Nat] = Get[Int].map(fromInt)
  implicit val natPut: Put[Nat] = Put[Int].contramap(toInt)

  implicit val natMeta: Meta[Nat] = Meta[Int].imap(fromInt)(toInt)
  implicit val natMeta2: Meta[Nat] = Meta[Int].timap(fromInt)(toInt)

  implicit val showPGobject: Show[PGobject] = Show.show(_.getValue.take(250))

  implicit val jsonGet: Get[Json] =
    Get.Advanced.other[PGobject](NonEmptyList.of("json")).temap[Json] { o =>
      parse(o.getValue).leftMap(_.show)
    }
  implicit val jsonPut: Put[Json] =
    Put.Advanced.other[PGobject](NonEmptyList.of("json")).tcontramap[Json] { j =>
      val o = new PGobject
      o.setType("json")
      o.setValue(j.noSpaces)
      o
    }
  implicit val jsonMeta: Meta[Json] =
    Meta.Advanced.other[PGobject]("json").timap[Json](
      a => parse(a.getValue).leftMap[Json](e => throw e).merge)(
      a => {
        val o = new PGobject
        o.setType("json")
        o.setValue(a.noSpaces)
        o
      }
    )
  println("---------------------------------Column Vector Mappings-----------------------------------------------")
  implicit val pointRead: Read[Point] =
    Read[(Int, Int)].map { case (x, y) => new Point(x, y) }
  // pointRead: Read[Point] = doobie.util.Read@698e07b1

  implicit val pointWrite: Write[Point] =
    Write[(Int, Int)].contramap(p => (p.x, p.y))
  // pointWrite: Write[Point] = doobie.util.Write@186345c0
}

object NatModule {

  sealed trait Nat

  case object Zero extends Nat

  case class Succ(n: Nat) extends Nat

  def toInt(n: Nat): Int = {
    def go(n: Nat, acc: Int): Int =
      n match {
        case Zero => acc
        case Succ(n) => go(n, acc + 1)
      }

    go(n, 0)
  }

  def fromInt(n: Int): Nat = {
    def go(n: Int, acc: Nat): Nat =
      if (n <= 0) acc else go(n - 1, Succ(acc))

    go(n, Zero)
  }

}
