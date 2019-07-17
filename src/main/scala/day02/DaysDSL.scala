package day02

import DateHelper._

/**
  * @Author: gin
  * @Date: Created in 下午3:44 19-7-13
  * @Description:
  */
object DaysDSL extends App {
  val past = 2 days ago
  val appointment = 5 days from_now
  println(past)
  println(appointment)
  println(3 days ago)

  val ago3 = new DateHelper(3)
  println(ago3.days("ago"))
  println(ago3 days ago)
  println(ago3 days "a")
}
