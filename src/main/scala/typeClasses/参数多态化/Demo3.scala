package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:16 19-8-20
  * @Description:
  */
object Demo3 extends App {
  case class Dog(name: String)

  trait CanSpeak[T] {
    def voice: String
  }

  implicit object DogCanSpeak extends CanSpeak[Dog] {
    def voice = "Whoof"
  }

  def speak[T](speaker: T)(implicit ev: CanSpeak[T]) = {
    println(s"$speaker does ${ev.voice}")
  }

  val d1 = new Dog("aini")
  speak(Dog("tom"))
  speak(d1)
}
