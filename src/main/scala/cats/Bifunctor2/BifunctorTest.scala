package cats.Bifunctor2

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:48 19-9-10
  * @Description:
  */

import cats._
import cats.implicits._
import java.time._

object BifunctorTest2 extends App {

  case class DomainError(message: String)

  def dateTimeFromUser: Either[Throwable, ZonedDateTime] =
    Right(ZonedDateTime.now())

  dateTimeFromUser.bimap(
    error => DomainError(error.getMessage),
    dateTime => dateTime.toEpochSecond
  )

  val records: List[(Int, Int)] = List((450000, 3), (770000, 4), (990000, 2), (2100, 4), (43300, 3))

  def calculateContributionPerMonth(balance: Int, lifetime: Int) = balance / lifetime

//  val result: List[Int] =
//    records.map(
//      record => record.bimap(
//        cents => cents / 100,
//        years => 12 * years
//      )
//    ).map((calculateContributionPerMonth _).tupled)
}
