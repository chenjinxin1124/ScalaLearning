package Interview
import java.util.Date

class Result(val r: Long, val i: Int, val j: Int, val k: Int, val l: Int) {}

object B1 extends App {

  def f(up: Int) = {
    var result: Set[String] = Set("")
    var sum = 0
    for (i <- 1 to 99) {
      for (j <- i + 1 to i + 99) {
        for (k <- j + 1 to j + 99) {
          for (l <- k + 1 to k + 99) {
            if (Math.pow(i, 3) + Math.pow(l, 3) == Math.pow(j, 3) + Math.pow(k, 3)) {
              sum += 1
              val r = (Math.pow(i, 3) + Math.pow(l, 3))
              val t = new Result(r.toLong, i, j, k, l)
              result += r.toString
              println(s"No $sum : $r = $i^3 + $l^3 = $j^3 + $k^3 ")
            }
          }
        }
      }
    }
    result
  }

  val start = new Date().getTime
  val s: Set[String] = f(1)
  //  println(s.-(""))
  val list = s.-("").toList
  val t = list.map(_.toDouble.toLong)
  val list2 = t.sortWith(_.compareTo(_) < 0)
  println(list2)
  val end = new Date().getTime
  println(end - start)

}
