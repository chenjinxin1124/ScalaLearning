/*
package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:50 19-8-20
  * @Description:
  */

import simulacrum._

object SemigroupPlus extends App {

  @typeclass trait Semigroup[A] {
    @op("|+|") def combine(a1: A, a2: A): A
  }

  // 定义隐式实例
  implicit val intPlusInstance = new Semigroup[Int] {
    def combine(a1: Int, a2: Int): Int = a1 + a2
  }

  // 使用
  import Semigroup.ops._

  1 |+| 2 // 3
}
*/
