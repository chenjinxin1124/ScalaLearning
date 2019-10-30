package beginnersGuideToScala.chp4PatternMatchingAnonymousFunctions

/**
  * @Author: chenjinxin
  * @Date: Created in 下午8:19 19-10-28
  * @Description:
  */
object Demo extends App {
  //模式匹配与匿名函数
  val songTitle = List("The White Hare", "Childe the Hunter", "Take no Rogues")
  songTitle.map(_.toLowerCase)

  val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
    ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)

  println(wordsWithoutOutliers(wordFrequencies))

  def wordWithoutOutliers2(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }

  val predicate: (String, Int) => Boolean = {
    case (_, f) => f > 3 && f < 25
  }
  val transformFn: (String, Int) => String = {
    case (w, _) => w
  }

  println(wordWithoutOutliers2(wordFrequencies))

  //偏函数
  val pf: PartialFunction[(String, Int), String] = {
    case (word, freq) if freq > 3 && freq < 25 => word
  }

  //  println(wordFrequencies.map(pf)) MatchError
  println(wordFrequencies.collect(pf))

  def wordsWithoutOutliers3(wordFrequencies: Seq[(String,Int)]):Seq[String]=
    wordFrequencies.collect{case (word,freq)if freq>3&&freq<25=>word}

  println(wordsWithoutOutliers3(wordFrequencies))

}
