package doobie

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:39 19-8-20
  * @Description:
  */
import cats._, cats.data._, cats.implicits._
import doobie._

object Introduction extends App {
  case class Person(name: String, age: Int)
  val nel = NonEmptyList.of(Person("Bob", 12), Person("Alice", 14))
  println(nel.head)
  // Person("Bob", 12)
  println(nel.tail)
  // List(Person("Alice", 14))
}
