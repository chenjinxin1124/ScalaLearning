package cats.SemigroupsAndMonoids

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:16 19-9-3
  * @Description:
  */
object MonoidTest extends App {

  import cats.Monoid

  implicit val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0

    def combine(x: Int, y: Int): Int = x + y
  }

  val x = 1

  val r1 = Monoid[Int].combine(x, Monoid[Int].empty)
  val r2 = Monoid[Int].combine(Monoid[Int].empty, x)
  println(r1, r2)

  def combineAll[A: Monoid](as: List[A]): A =
    as.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  import cats.implicits._

  val r3 = combineAll(List(1, 2, 3))
  val r4 = combineAll(List("hello", " ", "world"))
  val r5 = combineAll(List(Map('a' -> 1), Map('a' -> 2, 'b' -> 3), Map('b' -> 4, 'c' -> 5)))
  val r6 = combineAll(List(Set(1, 2), Set(2, 3, 4, 5)))
  println(r3, r4, r5, r6)

  import cats.Semigroup

  final case class NonEmptyList[A](head: A, tail: List[A]) {
    def ++(other: NonEmptyList[A]): NonEmptyList[A] = NonEmptyList(head, tail ++ other.toList)

    def toList: List[A] = head :: tail
  }

  object NonEmptyList {
    implicit def nonEmptyListSemigroup[A]: Semigroup[NonEmptyList[A]] =
      new Semigroup[NonEmptyList[A]] {
        def combine(x: NonEmptyList[A], y: NonEmptyList[A]): NonEmptyList[A] = x ++ y
      }
  }

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def empty: Option[A] = None

    def combine(x: Option[A], y: Option[A]): Option[A] =
      x match {
        case None => y
        case Some(xv) =>
          y match {
            case None => x
            case Some(yv) => Some(xv |+| yv)
          }
      }
  }

}
