package cats.ComonadP

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:12 19-9-4
  * @Description:
  */

import cats._
import cats.data._
import cats.implicits._
import cats.syntax.comonad._
import cats.instances.list._

object ComonadTest extends App {

//  val r1: Int = NonEmptyList.of(1, 2, 3).extract
  val r1: Int = NonEmptyList.of(1, 2, 3).head
  println(r1)

  val r2: NonEmptyList[NonEmptyList[Int]] = NonEmptyList.of(1, 2, 3, 4, 5).coflatMap(identity)
  println(r2)

//  val r3 = List(1, 2, 3, 4, 5).coflatMap(identity)
//  println(r3)
}
