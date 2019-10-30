package beginnersGuideToScala.chp7TheEitherType

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:56 19-10-29
  * @Description:
  */
object Demo extends App {

  //创建 Either
  import java.net.URL

  import scala.io.Source

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of people!")
    else
      Right(Source.fromURL(url))

  println(getContent(new URL("https://www.baidu.com/")))
  println(getContent(new URL("https://plus.google.com")))

  //Either 用法
  val r1: Unit = getContent(new URL("http://google.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines().foreach(println)
  }
  val r2: Unit = getContent(new URL("http://www.baidu.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines().foreach(println)
  }
  println(r1)
  println(r2)

  val content: Either[String, Iterator[String]] =
    getContent(new URL("http://www.baidu.com")).right.map(_.getLines())
  val moreContent: Either[String, Iterator[String]] =
    getContent(new URL("http://google.com")).right.map(_.getLines)
  println(content)//Right(<iterator>)
  println(moreContent)//Left(Requested URL is blocked for the good of people!)

  val content2: Either[Iterator[String], Source] =
    getContent(new URL("http://www.baidu.com")).left.map(Iterator(_))
  val moreContent2: Either[Iterator[String], Source] =
    getContent(new URL("http://google.com")).left.map(Iterator(_))
  println(content2)//Right(<iterator>)
  println(moreContent2)//Left(<iterator>)



}
