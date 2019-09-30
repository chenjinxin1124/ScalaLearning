package beginnersGuideToScala.extractors

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:59 19-9-30
  * @Description:
  */
object Demo4 extends App {

  val xs = 58 #:: 43 #:: 93 #:: Stream.empty
  val rel = xs match {
    case first #:: second #:: _ => first - second
    case _ => -1
  }
  println(rel)

  val xs2 = 58 #:: 43#:: 93#:: Stream.empty
  val rel2 = xs2 match {
    case #::(first,#::(second,_))=>first - second
    case _ => -1
  }
  println(rel2)

}
