package json解析问题

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:39 19-9-17
  * @Description:
  */
object oo extends App {

  import io.circe._
  import io.circe.parser._

  final case class Thing(foo: String, bar: Int)
  final case class Thing2(foo: String, bar: Seq[Thing])

  implicit val decodeFoo: Decoder[Thing] = { c: HCursor =>
    for {
      foo <- c.getOrElse[String]("foo")("00000")
      bar <- c.downField("bar").as[Int]
    } yield Thing(foo, bar)
  }

  implicit val decodeFoo2: Decoder[Thing2] = { c: HCursor =>
    for {
      foo <- c.getOrElse[String]("foo")("00000")
      bar <- c.getOrElse[Seq[Thing]]("bar")(Seq.empty[Thing])
    } yield Thing2(foo, bar)
  }

  println(decode[Thing]("""{"foo": "foo", "bar": 123}"""))
  println(decode[Thing]("""{"foo": "foo", "bar": null}"""))
  println(decode[Thing]("""{"bar": 123}"""))

  println(decode[Thing2]("""{"foo": "foo", "bar": null}"""))
  println(decode[Thing2]("""{"foo": "foo", "bar": []}"""))
}
