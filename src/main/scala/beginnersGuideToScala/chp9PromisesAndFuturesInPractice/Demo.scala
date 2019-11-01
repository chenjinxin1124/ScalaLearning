package beginnersGuideToScala.chp9PromisesAndFuturesInPractice

import scala.concurrent.Future

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:37 19-10-31
  * @Description:
  */
object Demo extends App {
  //给出承诺
  import concurrent.Promise

  case class TaxCut(reduction: Int)

  val taxCut: Promise[TaxCut] = Promise[TaxCut]()

  val taxCut2: Promise[TaxCut] = Promise()

  val taxCutF: Future[TaxCut] = taxCut.future
  println(taxCut)
  println(taxCut2)
  println(taxCutF)

  //


}
