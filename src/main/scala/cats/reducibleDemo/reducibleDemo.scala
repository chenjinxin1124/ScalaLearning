package cats.reducibleDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午8:30 19-9-17
  * @Description:
  */
object reducibleDemo extends App {

  import cats._
  import cats.data._
  import cats.implicits._

  Reducible[NonEmptyList].reduce(NonEmptyList.of("a", "b", "c"))
  // res0: String = abc

  Reducible[NonEmptyList].reduceMap(NonEmptyList.of(1, 2, 4))(_.toString)
  // res1: String = 124

  Reducible[NonEmptyVector].reduceK(NonEmptyVector.of(List(1, 2, 3), List(2, 3, 4)))
  // res2: List[Int] = List(1, 2, 3, 2, 3, 4)

  Reducible[NonEmptyVector].reduceLeft(NonEmptyVector.of(1, 2, 3, 4))((s, i) => s + i)
  // res3: Int = 10

  Reducible[NonEmptyList].reduceRight(NonEmptyList.of(1, 2, 3, 4))((i, s) => Later(s.value + i)).value
  // res4: Int = 10

  Reducible[NonEmptyList].reduceLeftTo(NonEmptyList.of(1, 2, 3, 4))(_.toString)((s, i) => s + i)
  // res5: String = 1234

  Reducible[NonEmptyList].reduceRightTo(NonEmptyList.of(1, 2, 3, 4))(_.toString)((i, s) => Later(s.value + i)).value
  // res6: String = 4321

  Reducible[NonEmptyList].nonEmptyIntercalate(NonEmptyList.of("a", "b", "c"), ", ")
  // res7: String = a, b, c

  def countChars(s: String) = s.toCharArray.groupBy(identity).mapValues(_.length)

  // countChars: (s: String)scala.collection.immutable.Map[Char,Int]

  println(Reducible[NonEmptyList].nonEmptyTraverse_(NonEmptyList.of("Hello", "World"))(countChars))
  // res8: scala.collection.immutable.Map[Char,Unit] = Map(l -> (), o -> ())

  println(Reducible[NonEmptyVector].nonEmptyTraverse_(NonEmptyVector.of("Hello", ""))(countChars))
  // res9: scala.collection.immutable.Map[Char,Unit] = Map()

  println(Reducible[NonEmptyList].nonEmptySequence_(NonEmptyList.of(Map(1 -> 'o'), Map(1 -> 'o'))))
  // res10: scala.collection.immutable.Map[Int,Unit] = Map(1 -> ())

}
