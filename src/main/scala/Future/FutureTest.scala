package Future

import java.time._
import scala.concurrent._
import ExecutionContext.Implicits.global

/**
  * @Author: gin
  * @Date: Created in 下午2:06 19-7-15
  * @Description:
  */
object FutureTest extends App {

  val value: Future[Int] = Future {
    Thread.sleep(3000)
    println(s"This is the future at ${LocalTime.now}")
    20
  }

  println(s"This is the present at ${LocalTime.now}")
  println(value)
  Thread.sleep(3000)
  println(value)
}
