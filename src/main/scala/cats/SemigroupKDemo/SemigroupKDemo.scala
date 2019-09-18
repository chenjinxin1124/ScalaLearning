package cats.SemigroupKDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:57 19-9-17
  * @Description:
  */
import cats._
import cats.implicits._
object SemigroupKDemo extends App {

  println(SemigroupK[List].combineK(List(1,2,3), List(4,5,6)) == Semigroup[List[Int]].combine(List(1,2,3), List(4,5,6)))

  Semigroup[Option[Int]].combine(Some(1), Some(2))
  // res1: Option[Int] = Some(3)

  SemigroupK[Option].combineK(Some(1), Some(2))
  // res2: Option[Int] = Some(1)

  SemigroupK[Option].combineK(Some(1), None)
  // res3: Option[Int] = Some(1)

  SemigroupK[Option].combineK(None, Some(2))
  // res4: Option[Int] = Some(2)

  val one = Option(1)
  val two = Option(2)
  val n: Option[Int] = None
  println(one |+| two)
  println(one <+> two)
  println(two <+> one)
  println(n |+| two)
  println(n <+> two)
  println(n |+| n)
  println(n <+> n)
  println(two |+| n)
  println(two <+> n)
}
