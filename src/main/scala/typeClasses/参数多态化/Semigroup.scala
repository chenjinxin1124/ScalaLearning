package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:40 19-8-20
  * @Description:
  */

object Semigroup extends App {

  trait Semigroup[A] {
    def combine(a1: A, a2: A): A
  }

  def apply[A](implicit instance: Semigroup[A]) = instance

  implicit val intPlusInstance = new Semigroup[Int] {
    def combine(a1: Int, a2: Int): Int = a1 + a2
  }

  println(Semigroup[Int].combine(1, 2))
}
