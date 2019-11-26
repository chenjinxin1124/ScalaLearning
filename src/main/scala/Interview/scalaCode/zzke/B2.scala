package Interview.scalaCode.zzke

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:22 19-9-29
  * @Description:
  */
object B2 extends App {

  var res = 0
  for (i <- 1 to 9) {//i > 10　结果一样
    res = (res + math.pow(i.toDouble, i.toDouble).toInt)
  }
  println(res)

}
