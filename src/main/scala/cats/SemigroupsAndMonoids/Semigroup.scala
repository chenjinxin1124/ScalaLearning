package cats.SemigroupsAndMonoids

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:51 19-9-3
  * @Description:
  */
object SemigroupTest extends App {

  import cats.Semigroup

  implicit val intAdditionSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1
  val y = 2
  val z = 3

  println(Semigroup[Int].combine(x, y))

  println(Semigroup[Int].combine(x, Semigroup[Int].combine(y, z)))

  import cats.implicits._

  println(1 |+| 2)
  val map1 = Map("hello" -> 0, "world" -> 1)
  val map2 = Map("hello" -> 2, "cats" -> 3)
  Semigroup[Map[String, Int]].combine(map1, map2)
  println(map1 |+| map2)

  def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
    opt.map(a |+| _).getOrElse(a)

  def mergeMap[K, V: Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] =
    lhs.foldLeft(rhs) {
      case (acc, (k, v)) => acc.updated(k, optionCombine(v, acc.get(k)))
    }

  {
    val xm1 = Map('a' -> 1, 'b' -> 2)
    val xm2 = Map('b' -> 3, 'c' -> 4)
    val x = mergeMap(xm1, xm2)

    val ym1 = Map(1 -> List("hello"))
    val ym2 = Map(2 -> List("cats"), 1 -> List("world"))
    val y = mergeMap(ym1, ym2)

    println(x)
    println(y)
  }

  {
    val leftwards = List(1, 2, 3).foldLeft(0)(_ |+| _)
    val rightwards = List(1, 2, 3).foldRight(0)(_ |+| _)
    println(leftwards, rightwards)
  }

  {
    val list = List(1, 2, 3, 4, 5)
    val (left, right) = list.splitAt(2)
    println(left, right)
    val sumLeft = left.foldLeft(0)(_ |+| _)
    val sumRight = right.foldLeft(0)(_ |+| _)
    val result = sumLeft |+| sumRight
    println(sumLeft, sumRight, result)
  }

  {

  }
}
