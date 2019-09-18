package cats.showDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午8:15 19-9-17
  * @Description:
  */
object showDemo extends App {

  import cats.Show
  case class Person(name: String, age: Int)
  implicit val showPerson: Show[Person] = Show.show(person => person.name)
  case class Department(id: Int, name: String)
  implicit val showDep: Show[Department] = Show.fromToString
  import cats.implicits._
  val john = Person("John", 31)
  println(john.show)
  val engineering = Department(2, "Engineering")
  println(show"$john works at $engineering")

}
