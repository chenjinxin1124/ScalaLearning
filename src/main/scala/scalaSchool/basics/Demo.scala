package scalaSchool.basics

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:05 19-9-25
  * @Description:
  */
object Demo extends App {

  println("----------------------匿名函数----------------------")
  //　将匿名函数 (x: Int) => x + 1 ,保存为 addOne
  val addOne = (x: Int) => x + 1
  println(addOne(1)) //2

  println("----------------------Partial application----------------------")

  def adder(m: Int, n: Int) = m + n

  val add2 = adder(2, _: Int)
  println(add2(3))

  println("----------------------柯里化函数----------------------")

  def multiply(m: Int)(n: Int) = m * n

  println(multiply(2)(3))
  val timeTwo = multiply(2) _
  println(timeTwo(3))

  println("----------------------可变长度参数----------------------")

  def capitalizeAll(args: String*) = {
    args.map {
      arg => arg.capitalize
    }
  }

  println(capitalizeAll("hadopp", "hdfs", "yarn"))

  println("----------------------类，构造函数----------------------")

  //参数默认是val类型
  class Student(name: String) {
    def show() = {
      println(s"name is $name")
    }
  }

  val s1 = new Student("aa")
  s1.show()

  println("----------------------函数vs方法----------------------")

  class C {
    var acc = 0

    def minc = {
      acc = acc + 1
    }

    val finc = { () => acc += 1 }
  }

  val c = new C
  println(c.minc, c.acc) //((),1)
  println(c.finc, c.acc) //(scalaSchool.basics.Demo$C$$Lambda$30/0x0000000840100040@71423665,1),不加括号表示是函数的引用地址
  println(c.finc(), c.acc) //((),2)

  println("----------------------继承&重载----------------------")

  class Calculator(brand: String) {
    var model = "2GB"
    /**
      * A constructor.
      */
    val color: String = if (brand == "TI") {
      "blue"
    } else if (brand == "HP") {
      "black"
    } else {
      "white"
    }

    // An instance method.
    def add(m: Int, n: Int): Int = m + n
  }

  class ScientificCalculator(brand: String) extends Calculator(brand) {
    def log(m: Double, base: Double) = math.log(m) / math.log(base)
  }

  class EvenMoreScientificCalculator(brand: String) extends ScientificCalculator(brand) {
    def log(m: Int): Double = log(m, math.exp(1))
  }

  println("----------------------抽象类----------------------")

  abstract class Shape {
    def getArea(): Int // subclass should define this
  }

  class Circle(r: Int) extends Shape {
    def getArea(): Int = {
      r * r * 3
    }
  }

  println(new Circle(2).getArea())

  println("----------------------特质（Traits）----------------------")
  println("与抽象类对比，优先使用特质（Traits）")
  trait Car {
    val brand: String
  }

  trait Shiny {
    val shineRefraction: Int
  }

  class BMW extends Car with Shiny{
    val brand = "BMW"
    val shineRefraction = 12
  }

  println("----------------------泛型----------------------")

  trait Cache[K, V] {
    def get(key: K): V
    def put(key: K, value: V)
    def delete(key: K)
  }
  /*class CacheC extends Cache{
    override def get(key: String): String = key

    override def put(key: String, value: Int): Unit = println(s"key: value=>$key: $value")

    override def delete(key: String): Unit = println(s"key => $key")
  }
  println(new CacheC().get(12))*/

}
