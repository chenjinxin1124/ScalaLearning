package cats.TypeClasses

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:13 19-9-3
  * @Description:
  */
trait Monoid[A] {
  def empty: A

  def combine(x: A, y: A): A
}

object Demo extends App {

  final case class Pair[A, B](first: A, second: B)

  object Pair {
    implicit def tuple2Instance[A, B](implicit A: Monoid[A], B: Monoid[B]): Monoid[Pair[A, B]] =
      new Monoid[Pair[A, B]] {
        def empty: Pair[A, B] = Pair(A.empty, B.empty)

        def combine(x: Pair[A, B], y: Pair[A, B]): Pair[A, B] =
          Pair(A.combine(x.first, y.first), B.combine(x.second, y.second))
      }
  }
  implicit val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def combine(x: Int, y: Int): Int = x + y
  }
  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def empty: String = ""
    def combine(x: String, y: String): String = x ++ y
  }

  def combineAll[A](list: List[A])(implicit A: Monoid[A]): A = list.foldRight(A.empty)(A.combine)

  import Demo.{Pair => Paired}
  println(combineAll(List(Paired(1, "hello"), Paired(2, " "), Paired(3, "world"))))

  
}
