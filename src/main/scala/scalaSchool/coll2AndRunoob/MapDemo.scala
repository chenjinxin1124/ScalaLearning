package scalaSchool.coll2AndRunoob

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:34 19-9-27
  * @Description:
  */
object MapDemo extends App {

  println(Map("a" -> 1, "b" -> 2))
  println(Map(("a", 2), ("b", 2)))
  println("a" -> 2)
  println("a".->(2))
  println(Map.empty ++ List(("a", 1), ("b", 2), ("c", 3)))


}
