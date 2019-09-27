package scalaSchool.basics2

import doobie.util.log
import scalaSchool.basics.Demo.Calculator

/**
  * @Author: chenjinxin
  * @Date: Created in 上午10:41 19-9-26
  * @Description:
  */
object Demo extends App {

  println("-------------------------------------apply 方法-----------------------------------------")

  class Foo {}

  object FooMaker {
    def apply() = new Foo
  }

  val newFoo = FooMaker

  class Bar {
    def apply() = "apply"
  }

  val bar = new Bar
  println(bar())

  println("-------------------------------------单例对象，通常用于工厂模式，通常将伴生对象作为工厂使用----------------------------------------")

  object Timer {
    var count = 0

    def currentCount(): Long = {
      count += 1
      count
    }
  }

  println(Timer.currentCount())

  class Bar2(foo: String)

  object Bar2 {
    def apply(foo: String): Bar2 = new Bar2(foo)
  }

  println("-------------------------------------函数即对象----------------------------------------")

  //Function0->Function22
  object addOne extends Function1[Int, Int] {
    def apply(m: Int): Int = m + 1
  }

  println(addOne(1))

  class AddOne extends (Int => Int) {
    def apply(m: Int): Int = m + 1
  }

  val plusOne = new AddOne()
  println(plusOne(1))

  println("-------------------------------------包----------------------------------------")

  object colorHolder {
    val BLUE = "Blue"
    val RED = "Red"
  }

  println("the color is: " + scalaSchool.basics2.Demo.colorHolder.BLUE)

  println("-------------------------------------模式匹配----------------------------------------")
  val times = 1

  times match {
    case 1 => "one"
    case 2 => "two"
    case _ => "some other number"
  }

  times match {
    case i if i == 1 => "one"
    case i if i == 2 => "two"
    case _ => "some other number"
  }

  def bigger(o: Any): Any = {
    o match {
      case i: Int if i < 0 => i - 1
      case i: Int => i + 1
      case d: Double if d < 0.0 => d - 0.1
      case d: Double => d + 0.1
      case text: String => text + "s"
    }
  }

  def calcType(calc: Calculator) = calc match {
    case _ if calc.model == "20B" => "financial"
    case _ if calc.model == "48G" => "scientific"
    case _ if calc.model == "30B" => "business"
    case _ => "unknown"
  }

  //样本类 Case Classes，样本类也可以像普通类那样拥有方法。
  case class Calculator(brand: String, model: String)

  val hp20b = Calculator("HP", "20b")
  val hp20B = Calculator("HP", "20b")
  println(hp20b == hp20B)

  def calcType2(calc: Calculator) = calc match {
    case Calculator("HP", "20B") => "financial"
    case Calculator("HP", "48G") => "scientific"
    case Calculator("HP", "30B") => "business"
    case Calculator(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
    case Calculator(_, _) => "Calculator of unknown type"
    case _ => "Calculator of unknown type"
    case c@Calculator(_, _) => "Calculator: %s of unknown type".format(c)//将匹配的值重新命名
  }

  println("-------------------------------------异常----------------------------------------")
//  try也是面向表达式的

//  val result: Int = try {
//    remoteCalculatorService.add(1, 2)
//  } catch {
//    case e: ServerIsDownException => {
//      log.error(e, "the remote calculator service is unavailable. should have kept your trusty HP.")
//      0
//    }
//  } finally {
//    remoteCalculatorService.close()
//  }

//  这并不是一个完美编程风格的展示，而只是一个例子，用来说明try-catch-finally和Scala中其他大部分事物一样是表达式。
//  当一个异常被捕获处理了，finally块将被调用；它不是表达式的一部分。

}
