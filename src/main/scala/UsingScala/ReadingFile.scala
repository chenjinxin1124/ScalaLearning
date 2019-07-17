package UsingScala

/**
  * @Author: gin
  * @Date: Created in 上午11:10 19-7-15
  * @Description:
  */
object ReadingFile extends App {

  import scala.io._

  println("*** The content of the file you read is:")
  Source.fromFile("symbols.txt").foreach {
    print
  }
}
