package Interview

import java.util
import java.util.ArrayList

import scala.collection.mutable.ArrayBuffer

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:59 19-9-29
  * @Description:
  */
object B5 extends App {

  var a = new Array[Char](13) // 所求排列组合
  var i = -1
  var arr = ArrayBuffer.fill(13)({
    i += 1
    ('a' + i).toChar
  })
  //  val FAC = Array(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880) // 阶乘
  i = 1
  var j = 1
  val FAC: Array[Long] = Array.concat(Array(1), Array.fill(12)({
    j *= i
    i += 1
    j
  })
  )
  FAC.foreach(println(_))
    var x = 1000000 - 1
    i = 0
    while (i < 13) {
      val r = x % FAC(13 - 1 - i)
      val t = x / FAC(13 - 1 - i)
      a(i) = arr(t.toInt)
      x = r.toInt
      arr -= (a(i))
      i += 1
    }

    a.foreach(print(_))

}
