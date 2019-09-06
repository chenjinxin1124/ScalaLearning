package cats.TypeClasses

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:48 19-9-3
  * @Description:
  */
object Demo1 extends App {
  def sumInts(list: List[Int]): Int = list.foldRight(0)(_ + _)

  def concatStrings(list: List[String]): String = list.foldRight("")(_ ++ _)

  def unionSets[A](list: List[Set[A]]): Set[A] = list.foldRight(Set.empty[A])(_ union _)

  trait Monoid[A] {
    def empty: A
    def combine(x: A, y: A): A
  }

  val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def combine(x: Int, y: Int): Int = x + y
  }

  def combineAll[A](list: List[A], A: Monoid[A]): A = list.foldRight(A.empty)(A.combine)

  val list: List[Int] = List(1,2,3)
  println(combineAll(list,intAdditionMonoid))
  println(sumInts(list))

}
