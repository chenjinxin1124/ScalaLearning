package scalaSchool.coll2AndRunoob

//https://www.runoob.com/scala/scala-collections.html
/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:18 19-9-27
  * @Description:
  */
object ListDemo extends App {

  val mainList = List(3, 2, 1)
  val with4 = 4 :: mainList
  val with42 = 42 :: mainList
  val shorter = mainList.tail

  val days = List("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

  // Make a list element-by-element
  val when = "AM" :: "PM" :: Nil

  // Pattern match
  days match {
    case firstDay :: otherDays =>
      println("The first day of the week is: " + firstDay)
      println("The other day of the week is: " + otherDays)
    case Nil =>
      println("There don't seem to be any week days.")
  }

  // 二维列表
  val dim: List[List[Int]] =
    List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )

  val dim2 = (1 :: (0 :: (0 :: Nil))) ::
    (0 :: (1 :: (0 :: Nil))) ::
    (0 :: (0 :: (1 :: Nil))) :: Nil

  println(Seq(1, 2))
  println(Iterable(1, 2))

}
