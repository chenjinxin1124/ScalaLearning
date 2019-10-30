package beginnersGuideToScala.chp2ExtractingSequences

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:32 19-9-30
  * @Description:
  */
object Demo extends App {

  val xs = 3 :: 6 :: 12 :: Nil
  val rel = xs match {
    case List(a, b) => a * b
    case List(a, b, c) => a + b + c
    case _ => 0
  }
  println(rel)

  val xs2 = 3 :: 6 :: 12 :: 24 :: Nil
  val rel2 = xs2 match {
    case List(a, b, _*) => a * b
    case _ => 0
  }
  println(rel2)

  object GivenNames {
    def unapplySeq(name: String): Option[Seq[String]] = {
      val names = name.trim.split(" ")
      if (names.forall(_.isEmpty)) None
      else Some(names)
    }
  }

  def greetWithFirstName(name: String) = name match {
    case GivenNames(firstName, _*) => s"Good moring, $firstName"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  println(greetWithFirstName("zhangsan"))
  println(greetWithFirstName("zhangsan lisi"))

  object Names {
    def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
      val names = name.trim.split(" ")
      if (names.size < 2) None
      else Some((names.last,names.head,names.drop(1).dropRight(1)))
    }
  }

  def greet(fullName: String) = fullName match {
    case Names(lastName,firstName, _*) => s"Good morning, $firstName $lastName!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  println(greet("zhangsan"))
  println(greet("zhangsan lisi"))
  println(greet("Matthew John Michael"))

}
