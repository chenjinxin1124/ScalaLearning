package gettingStarted.Future

import java.time._

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


/**
  * @Author: gin
  * @Date: Created in 下午2:27 19-7-15
  * @Description:
  */
object Futures extends App {
  val future1 = Future {
    Thread.sleep(3000)
    println(s"This is the future1 at ${LocalTime.now}")
  }
  val future2 = Future {
    Thread.sleep(3000)
    println(s"This is the future2 at ${LocalTime.now}")
  }
  future1 onComplete {
    case Success(n1) =>
      future2 onComplete {
        case Success(n2) => {
//          val n = n1 + n2
//          println(s"Result: $n")
        }
        case Failure(ex)=>{
          println("error")
        }
      }
    case Failure(ex)=>{
      println("error")
    }
  }
}
