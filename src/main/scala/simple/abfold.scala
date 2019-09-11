package simple

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:41 19-9-11
  * @Description:
  */
object abfold extends App {

  val a = 2
  val b = 50

  val res = List.range(0, b).foldLeft(0L) { (x, y) => x + scala.math.pow(a, y).toLong }
  println(res)

}
