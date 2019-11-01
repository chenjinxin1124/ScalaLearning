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

  val content: Either[String, Iterator[String]] =
    getContent(new URL("http://www.baidu.com")).right.map(_.getLines())
  val moreContent: Either[String, Iterator[String]] =
    getContent(new URL("http://google.com")).right.map(_.getLines)
  println(content) //Right(<iterator>)
  println(moreContent) //Left(Requested URL is blocked for the good of people!)

  val content2: Either[Iterator[String], Source] =
    getContent(new URL("http://www.baidu.com")).left.map(Iterator(_))
  val moreContent2: Either[Iterator[String], Source] =
    getContent(new URL("http://google.com")).left.map(Iterator(_))
  println(content2) //Right(<iterator>)
  println(moreContent2) //Left(<iterator>)

  //Flat Mapping
  val url1 = "https://www.runoob.com/scala/scala-collections.html"
  val url2 = "https://www.runoob.com/scala/scala-iterators.html"
  val part5: URL = new URL(url1)
  val part6: URL = new URL(url2)
  val content3: Either[String, Either[String, Int]] = getContent(part5).right.map(a =>
    getContent(part6).right.map(b =>
      (a.getLines().size + b.getLines().size) / 2))
  println(content3)

  //for 语句
  def averageLineCount(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
    } yield (source1.getLines().size + source2.getLines().size) / 2

  def averageLineCountWontCompile(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
      lines1 = source1.getLines().size
      lines2 = source2.getLines().size
    } yield (lines1 + lines2) / 2

  def averageLineCount2(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
      lines1 <- Right(source1.getLines().size).right
      lines2 <- Right(source2.getLines().size).right
    } yield (lines1 + lines2) / 2

  println(averageLineCount(part5, part6))
  println(averageLineCountWontCompile(part5, part6))
  println(averageLineCount2(part5, part6))

  //Fold 函数  fold 是一个可以用来替代模式匹配的好方法
  val content4: Iterator[String] =
    getContent(new URL("https://www.baidu.com/")).fold(Iterator(_), _.getLines())
  val moreContent3: Iterator[String] =
    getContent(new URL("http://google.com")).fold(Iterator(_), _.getLines())
  content4.foreach(println)
  moreContent3.foreach(println)

  //处理集合
  type Citizen = String

  case class BlackListedResource(url: URL, visitors: Set[Citizen])

  val blacklist = List(
    BlackListedResource(new URL("https://google.com"), Set("John Doe", "Johanna Doe")),
    BlackListedResource(new URL("http://yahoo.com"), Set.empty),
    BlackListedResource(new URL("https://maps.google.com"), Set("John Doe")),
    BlackListedResource(new URL("http://plus.google.com"), Set.empty)
  )
  val checkedBlacklist: Seq[Either[URL, Set[Citizen]]] =
    blacklist.map(resource =>
      if (resource.visitors.isEmpty) Left(resource.url)
      else Right(resource.visitors))
  val suspiciousResources = checkedBlacklist.flatMap(_.left.toOption)
  val problemCitizens = checkedBlacklist.flatMap(_.right.toOption).flatten.toSet
  println(suspiciousResources)
  println(problemCitizens)

}
