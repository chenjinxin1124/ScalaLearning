package runoob

import cats.implicits._

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:18 19-9-29
  * @Description:
  */
object ListDemo extends App {

  val dim: Array[Array[Int]] =
    Array(
      Array(1, 3, 1),
      Array(1, 5, 1),
      Array(4, 2, 1)
    )
  val mul = List.tabulate(dim.length, dim(1).length) {
    (x, y) => {
      if (x === 0 && y === 0) {
        dim(x)(y)
      } else if (x === 0) {
        dim(x)(y) = dim(x)(y) + dim(x)(y - 1)
        dim(x)(y)
      } else if (y === 0) {
        dim(x)(y) = dim(x)(y) + dim(x - 1)(y)
        dim(x)(y)
      }
      else {
        dim(x)(y) = dim(x)(y) + math.min(dim(x - 1)(y), dim(x)(y - 1))
        dim(x)(y)
      }
    }
  }
  println("多维 : " + mul)
  println("多维 : " + mul)

}
