package gettingStarted.day02

/**
  * @Author: gin
  * @Date: Created in 下午4:16 19-7-13
  * @Description:
  */
object DateUtil2 {
  val ago = "ago"
  val from_now = "from_now"

  implicit class DateHelper(val offset: Int) extends AnyVal {

    import java.time.LocalDate

    def days(when: String): LocalDate = {
      val today = LocalDate.now
      when match {
        case "ago" => today.minusDays(offset)
        case "from_now" => today.plusDays(offset)
        case _ => today
      }
    }
  }

}

object UseDateUtil2 extends App {

  import DateUtil._

  val past = 2 days ago
  val appointment = 5 days from_now
  println(past)
  println(appointment)
}
