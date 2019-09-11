package cats.foldableT

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:42 19-9-10
  * @Description:
  */
object foldableTest extends App {

  import cats._
  import cats.implicits._

  println(Foldable[List].fold(List("a", "b", "c")))
  println(Foldable[List].foldMap(List(1, 2, 3))(_.toString))
  println(Foldable[List].foldK(List(List(1, 2, 3), List(2, 3, 4))))
  println(Foldable[List].reduceLeftToOption(List[Int]())(_.toString)((s, i) => (s + i)))
  println(Foldable[List].reduceLeftToOption(List[Int](1, 2, 3, 4))(_.toString)((s, i) => (s + i)))
  println(Foldable[List].reduceRightToOption(List[Int](1, 2, 3, 4))(_.toString)((i, s) => Later(s.value + i)).value)
  println(Foldable[List].reduceRightToOption(List[Int]())(_.toString)((i, s) => Later(s.value + i)).value)
  println(Foldable[List].find(List[Int](1, 2, 3, 4))(_ > 2))
  println(Foldable[List].exists(List[Int](1, 2))(_ > 2))
  Foldable[List].forall(List(1, 2, 3))(_ > 2)
  // res9: Boolean = false

  Foldable[List].forall(List(1, 2, 3))(_ < 4)
  // res10: Boolean = true

  Foldable[Vector].filter_(Vector(1, 2, 3))(_ < 3)
  // res11: List[Int] = List(1, 2)

  Foldable[List].isEmpty(List(1, 2))
  // res12: Boolean = false

  Foldable[Option].isEmpty(None)
  // res13: Boolean = true

  Foldable[List].nonEmpty(List(1, 2))
  // res14: Boolean = true

  Foldable[Option].toList(Option(1))
  // res15: List[Int] = List(1)

  Foldable[Option].toList(None)
  // res16: List[Nothing] = List()

  def parseInt(s: String): Option[Int] = scala.util.Try(Integer.parseInt(s)).toOption

  // parseInt: (s: String)Option[Int]

  Foldable[List].traverse_(List("1", "2"))(parseInt)
  // res17: Option[Unit] = Some(())

  Foldable[List].traverse_(List("1", "A"))(parseInt)
  // res18: Option[Unit] = None

  Foldable[List].sequence_(List(Option(1), Option(2)))
  // res19: Option[Unit] = Some(())

  Foldable[List].sequence_(List(Option(1), None))
  // res20: Option[Unit] = None

  //  Foldable[List].forallM(List(1, 2, 3))(i => if (i < 2) Some(i % 2 == 0) else None)
  //  // res21: Option[Boolean] = Some(false)
  //
  //  Foldable[List].existsM(List(1, 2, 3))(i => if (i < 2) Some(i % 2 == 0) else None)
  //  // res22: Option[Boolean] = None
  //
  //  Foldable[List].existsM(List(1, 2, 3))(i => if (i < 3) Some(i % 2 == 0) else None)
  // res23: Option[Boolean] = Some(true)

  val prints: Eval[Unit] = List(Eval.always(println(1)), Eval.always(println(2))).sequence_
  println(prints.value)
  println(Foldable[List].dropWhile_(List[Int](2, 4, 5, 6, 7))(_ % 2 == 0))
  println(Foldable[List].dropWhile_(List[Int](1, 2, 4, 5, 6, 7))(_ % 2 == 0))

  import cats.data.Nested
  val listOption0 = Nested(List(Option(1),Option(2),Option(3)))
  val listOption1 = Nested(List(Option(1),Option(2),None))
  println(listOption0)
  println(listOption1)

//  Foldable[Nested[List, Option, Integral]].fold(listOption0)
//  println(Foldable[Nested[List, Option, *]].fold(listOption1))
}
