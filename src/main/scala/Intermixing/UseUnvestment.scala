package Intermixing

/**
  * @Author: gin
  * @Date: Created in 上午10:36 19-7-15
  * @Description:
  */
object UseUnvestment extends App {

  import java._

  val instrument = new Investment("XYZ Corporation", InvestmentType.STOCK)
  println(instrument.getClass)
}
