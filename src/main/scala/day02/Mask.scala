package day02

/**
  * @Author: gin
  * @Date: Created in 下午5:12 19-7-13
  * @Description:
  */
object Mask extends App {

  import MyInterpolator._

  val ssn = "123-45-6789"
  val account = "0246781263"
  val balance = "20145.23"

  println(
    mask"""Account:$account
          |Social Security Number:$ssn
          |Balance:$$^$balance
          |Thanks for your business.""".stripMargin)
}
