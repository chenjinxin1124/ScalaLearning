package Interview.scalaCode

/**
  * @Author: chenjinxin
  * @Date: Created in 下午1:49 19-9-29
  * @Description:
  */
object A2 extends App {

  val users = Map(1 -> 1, 2 -> 2, 3 -> 2, 4 -> 3, 5 -> 5, 6 -> 7, 7 -> 7, 8 -> 8)
  var arr = Array.empty[Int]
  for (m <- users) {
    arr = Array.concat(arr, Array.fill(1 << m._2.toInt - 1)(m._1))
  }
  Array.fill(1000000)(arr(scala.util.Random.nextInt(arr.size))).map((_,1)).groupBy(_._1).map(t => (t._1, t._2.size)).toList.sortBy(_._2).foreach(println(_))

}
