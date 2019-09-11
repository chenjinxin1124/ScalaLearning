package cats.Eq2

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:25 19-9-10
  * @Description:
  */
object EqTest extends App {
  import cats.kernel.Eq
  import cats.implicits._

  println(1 === 1)
  println("hello" =!= "world")

  case class Foo(a: Int, b: String)

  implicit val eqFoo: Eq[Foo] = Eq.fromUniversalEquals

  println(Foo(10,"")===Foo(10,""))
}
