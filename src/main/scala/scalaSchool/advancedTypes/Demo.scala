package scalaSchool.advancedTypes

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:10 19-9-27
  * @Description:
  */
object Demo extends App {

  println("-------------------视界（“类型类”）------------------------")

  implicit def strToInt(x: String) = x.toInt

  implicit def strToInt(x: Float) = x.toInt

  val y: Int = "123"
  println(y)
  println(math.max("123", 121))

  class Container[A <% Int] {
    def addInt(x: A) = 123 + x
  }

  println(new Container[Int].addInt(123))
  println(new Container[String].addInt("123"))
  println(new Container[Float].addInt(123.2F))


  println("-------------------高阶多态性类型 和 特设多态性------------------------")

  trait Container2[M[_]] {
    def put[A](x: A): M[A]

    def get[A](m: M[A]): A
  }

  implicit val listContainer = new Container2[List] {
    override def put[A](x: A) = List(x)

    override def get[A](m: List[A]) = m.head
  }

  implicit val optionContainer = new Container2[Some] {
    override def put[A](x: A) = Some(x)

    override def get[A](m: Some[A]): A = m.get
  }

  def tupleize[M[_] : Container2, A, B](fst: M[A], snd: M[B]) = {
    val c = implicitly[Container2[M]]
    c.put(c.get(fst), c.get(snd))
  }

  println(tupleize(Some(1), Some("a")))
  println(tupleize(List(1), List("b")))

  println("-------------------结构类型,反射------------------------")

  def foo(x: {def get: Int}) = 123 + x.get

  val rel = foo(new {
    def get = 10
  })
  println(rel)


}
