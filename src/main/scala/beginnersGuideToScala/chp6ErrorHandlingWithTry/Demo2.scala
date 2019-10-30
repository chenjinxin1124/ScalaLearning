package beginnersGuideToScala.chp6ErrorHandlingWithTry

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:27 19-10-30
  * @Description:
  */
object Demo2 extends App {

  import scala.util.Try
  import java.net.URL

  def parseURL(url: String): Try[URL] = Try(new URL(url))

  val r1 = parseURL("http://danielwestheide.com")
  println(r1) //Success(http://danielwestheide.com)

  val r2 = parseURL("garbage")
  println(r2) //Failure(java.net.MalformedURLException: no protocol: garbage)

  //链式操作
  //Mapping 和 Flat Mapping
  val r3 = parseURL("http://danielwestheide.com").map(_.getProtocol)
  println(r3) //Success(http)

  val r4 = parseURL("qwe").map(_.getProtocol)
  println(r4) //Failure(java.net.MalformedURLException: no protocol: qwe)

  import java.io.InputStream

  def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] =
    parseURL(url).map { u =>
      Try(u.openConnection()).map(conn => Try(conn.getInputStream))
    }

  def inputStreamForURL2(url: String): Try[InputStream] =
    parseURL(url).flatMap { u =>
      Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
    }

  //过滤器和 foreach
  def parseHttpURL(url: String) = parseURL(url).filter(_.getProtocol == "http")

  val r5 = parseHttpURL("http://apache.openmirror.de")
  val r6 = parseHttpURL("ftp://mirror.netcologne.de/apache.org")
  println(r5)
  println(r6)

  parseHttpURL("http://danielwestheide.com").foreach(println) //当 Try 是 Failure 时， foreach 不会执行，返回 Unit 类型。

  //for 语句中的 Try
  import scala.io.Source

  def getURLContent(url: String) =
    for {
      url <- parseURL(url)
      //      source = Source.fromURL(url)
      connection <- Try(url.openConnection())
      is <- Try(connection.getInputStream)
      source = Source.fromInputStream(is)
    } yield source.getLines()

  val r7: Try[Iterator[String]] = getURLContent("http://danielwestheide.com")
  r7.foreach(println)

  //模式匹配
  import scala.util.{Success, Failure}

  getURLContent("https://www.baidu.com/") match {
    case Success(lines) => lines.foreach(println)
    case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
  }

  //从故障中恢复
  import java.net.MalformedURLException
  import java.io.FileNotFoundException

  // 如果 recover 是在 Success 实例上调用的，那么就直接返回这个实例，否则就调用偏函数。 如果偏函数为给定的 Failure 定义了处理动作， recover 会返回 Success ，里面包含偏函数运行得出的结果。
  val content = getURLContent("qwe") recover {
    case e: FileNotFoundException => Iterator("Requested page does not exist")
    case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
    case _ => Iterator("An unexpected error has occurred. We are so sorry!")
  }

  content.get.foreach(println)

}
