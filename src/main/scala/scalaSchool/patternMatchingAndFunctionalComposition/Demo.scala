package scalaSchool.patternMatchingAndFunctionalComposition

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:52 19-9-26
  * @Description:
  */
object Demo extends App {

  println("---------------------函数组合-----------------------------")

  def f(s: String) = "f(" + s + ")"

  def g(s: String) = "g(" + s + ")"

  val fComposeG = f _ compose g _
  println(fComposeG("yay")) //f(g(yay))
  val fAndThenG = f _ andThen g _
  println(fAndThenG("yay")) //g(f(yay))
  println("---------------------柯里化 vs 偏应用-----------------------------")

  println("---------------------偏函数 PartialFunctions-----------------------------")
  val one: PartialFunction[Int, String] = {
    case 1 => "one"
  }
  println(one.isDefinedAt(1))
  println(one.isDefinedAt(2))
  println(one(1))
  val two: PartialFunction[Int, String] = {
    case 2 => "two"
  }

  val three: PartialFunction[Int, String] = {
    case 3 => "three"
  }

  val wildcard: PartialFunction[Int, String] = {
    case _ => "something else"
  }

  val partial = one orElse two orElse three orElse wildcard

  println(partial(5))
  println(partial(3))
  println(partial(2))
  println(partial(1))
  println(partial(0))

  println("---------------------case 之谜-----------------------------")

  case class PhoneExt(name: String, ext: Int)

  val extensions = List(PhoneExt("steve", 100), PhoneExt("robey", 200))
  println(extensions.filter { case PhoneExt(name, extension) => extension < 200 })
  /*为什么这段代码可以工作？
  filter使用一个函数。在这个例子中是一个谓词函数(PhoneExt) => Boolean。
  PartialFunction是Function的子类型，所以filter也可以使用PartialFunction！*/

}
