package scalaSchool.typeBasics

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:07 19-9-27
  * @Description:
  */
object Demo extends App {

  println("----------------------参数化多态性-----------------------")
  val list1 = 2 :: 1 :: "bar" :: "foo" :: Nil
  println(list1.head)

  def drop1[A](l: List[A]) = l.tail

  println(drop1(list1))

  def id[T](x: T) = x

  val x = id(3)
  val y = id("h")
  val z = id(Array(1, 2, 3, 4))
  println(x, y, z.foreach(print(_)))


}
