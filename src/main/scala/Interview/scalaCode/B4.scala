package Interview.scalaCode

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:05 19-9-29
  * @Description:
  */
object B4 extends App {

  var maxN: Long = 0
  var max = 0
  var a = 0
  var b = 0
  //  val res = math.pow(7, 10).toInt.toString.toList.foldLeft(0)((x, y) => {
  //    x + y.toInt - '0'.toInt
  //  })
  //  println(math.pow(7, 10).toInt)
  //  println(res)
  for (i <- 0 to 100) {
    for (j <- 0 to 100) {
      val r = math.pow(i, j).toLong.toString.toList.foldLeft(0)((x, y) => {
        x + y.toInt - '0'.toInt
      })
      if (r > max) {
        max = r
        maxN = math.pow(i, j).toLong
        a = i
        b = j
      }
    }
  }
  println(a + "^" + b, maxN)
  println(max)

}
