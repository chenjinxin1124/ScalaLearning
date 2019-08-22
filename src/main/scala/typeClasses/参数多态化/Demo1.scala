package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:02 19-8-20
  * @Description:
  */


object Demo1 extends App {
  // 1
  trait Hasher[T] {
    def hash(arg: T): Int
  }
  // 2
  implicit object IntHasher extends Hasher[Int] {
    def hash(arg: Int): Int = arg
  }

  def hash[T](v: T)(implicit h: Hasher[T]): Int = h.hash(v)

  def hash[T](v: List[T])(implicit h: Hasher[T]): Int = {
    v.map(h.hash).sum
  }

  val str = (List("2", "3", "4"))
  println(hash(List(1, 2, 3)))

}
