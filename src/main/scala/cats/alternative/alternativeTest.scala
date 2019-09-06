package cats.alternative

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:21 19-9-4
  * @Description:
  */
object alternativeTest extends App {

  import cats.Alternative
  import cats.implicits._

  val empty = Alternative[Vector].empty[Int]
  val pureOfFive = 5.pure[Vector]
  val concatenated = 7.pure[Vector] <+> 8.pure[Vector]
  val double: Int => Int = _ * 2
  val addFive: Int => Int = _ + 5
  val apForVectors = (double.pure[Vector] <+> addFive.pure[Vector]) ap concatenated
  println(empty)
  println(pureOfFive)
  println(concatenated)
  println(double)
  println(addFive)
  println(apForVectors)



}
