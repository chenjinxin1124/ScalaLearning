package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:50 19-8-20
  * @Description:
  */
object Demo2 extends App {

  trait Hasher[T] {
    def hash(arg: T): Int
  }

  implicit object IntHasher extends Hasher[Int] {
    def hash(arg: Int): Int = arg
  }

  implicit object StringHasher extends Hasher[String] {
    def hash(arg: String): Int = arg.length
  }

  def hash[T](v: T)(implicit h: Hasher[T]): Int = h.hash(v)

  def hash[T](v: List[T])(implicit h: Hasher[T]): Int = {
    v.map(h.hash).sum
  }

  println(hash(List("22", "333", "4444")))
  println(hash(List(1, 2, 3)))
  println(hash(4))

}
