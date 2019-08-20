package typeClasses.参数多态化

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:22 19-8-20
  * @Description:
  */
object Demo4 extends App {

  trait Deployable[D] {
    def dbkey: String

    def serialize(d: D): String

    def deserialize(s: String): D
  }

  type Model = Long

//  val storage = muatable.Map.empty[String, String]
}
