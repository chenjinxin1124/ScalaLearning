package cats.MonoidKDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:06 19-9-17
  * @Description:
  */
object MonoidKDemo extends App {

  import cats.{Monoid, MonoidK}
  import cats.implicits._

  Monoid[List[String]].empty
  // res0: List[String] = List()

  MonoidK[List].empty[String]
  // res1: List[String] = List()

  MonoidK[List].empty[Int]
  // res2: List[Int] = List()

  Monoid[List[String]].combine(List("hello", "world"), List("goodbye", "moon"))
  // res3: List[String] = List(hello, world, goodbye, moon)

  MonoidK[List].combineK[String](List("hello", "world"), List("goodbye", "moon"))
  // res4: List[String] = List(hello, world, goodbye, moon)

  MonoidK[List].combineK[Int](List(1, 2), List(3, 4))
  // res5: List[Int] = List(1, 2, 3, 4)

  MonoidK[List].combineK(List("hello", "world"), List("goodbye", "moon"))
  // res6: List[String] = List(hello, world, goodbye, moon)

  MonoidK[List].combineK(List(1, 2), List(3, 4))
  // res7: List[Int] = List(1, 2, 3, 4)

}
