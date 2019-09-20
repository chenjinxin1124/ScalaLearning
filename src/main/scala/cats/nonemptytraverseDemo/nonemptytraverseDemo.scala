package cats.nonemptytraverseDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:34 19-9-18
  * @Description:
  */
object nonemptytraverseDemo extends App {

  import cats.implicits._
  import cats.data.NonEmptyList

  val snippets = NonEmptyList.of("What do you do", "What are you doing do")

  def countWords(text: String): Map[String, Int] =
    text.split(" ").groupBy(identity).mapValues(_.length)

  val r1 = snippets.nonEmptyTraverse(countWords)
  val r2 = snippets.map(countWords).nonEmptySequence
  println(r1)
  println(r2)
  //  Map(do -> NonEmptyList(2, 1), you -> NonEmptyList(1, 1), What -> NonEmptyList(1, 1))
  //  Map(do -> NonEmptyList(2, 1), you -> NonEmptyList(1, 1), What -> NonEmptyList(1, 1))
}
