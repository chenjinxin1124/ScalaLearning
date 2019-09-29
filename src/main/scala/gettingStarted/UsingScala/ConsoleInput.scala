package gettingStarted.UsingScala

/**
  * @Author: gin
  * @Date: Created in 上午11:04 19-7-15
  * @Description:
  */
object ConsoleInput extends App {

  import scala.io._

  print("Please enter a ticker symbol:")
  val symbol = StdIn.readLine()
  println(s"OK, got it, you owm $symbol")
}
