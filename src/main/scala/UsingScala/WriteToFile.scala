package UsingScala

/**
  * @Author: gin
  * @Date: Created in 上午11:07 19-7-15
  * @Description:
  */
object WriteToFile extends App {

  import java.io._

  val writer = new PrintWriter(new File("symbols.txt"))
  writer write "AAPL"
  writer.close()
  println(scala.io.Source.fromFile("symbols.txt").mkString)
}
