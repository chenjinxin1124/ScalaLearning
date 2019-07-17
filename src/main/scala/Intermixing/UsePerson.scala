package Intermixing

/**
  * @Author: gin
  * @Date: Created in 上午10:20 19-7-15
  * @Description:
  */
object UsePerson extends App {
  val george = new Person("George", "Washington")

  val georgesDogs = List(new Dog("Captain"), new Dog("Clode"), new Dog("Forester"), new Dog("Searcher"))

  println(s"$george had several dogs ${georgesDogs.mkString(", ")}...")
}
