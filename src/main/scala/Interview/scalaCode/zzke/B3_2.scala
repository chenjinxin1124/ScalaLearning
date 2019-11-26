package Interview.scalaCode.zzke

import java.util.Date

class Matrix(private var n: Long) {
  val mod = 1000000007
  var m = n-1

  def ope(a: (Long, Long), b: (Long, Long)): (Long, Long) = ((a._1 * b._1 + a._2 * b._2) % mod, (a._1 * b._2 + a._2 * b._1 + a._2 * b._2) % mod)

  def res: Long = {
    var a: (Long, Long) = (1, 0)
    var b: (Long, Long) = (0, 1)

    while (m != 0) {
      if (m % 2 == 1) a = ope(a, b)
      b = ope(b, b)
      m = m >> 1
    }
    (a._1 + a._2) % mod

  }

}

object B3_2 extends App {

  val n = 1000000000
  val a1: Long = new Matrix(n).res
  val start = new Date().getTime
  println(a1)
  val end = new Date().getTime
  println(end - start)

}
