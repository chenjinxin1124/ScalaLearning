package beginnersGuideToScala.chp3PatternEverywhere

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:21 19-10-28
  * @Description:
  */
object Demo extends App {

  case class Player(name: String, score: Int)

  def message(player: Player) = player match {
    case Player(_, score) if score > 100000 =>
      "Get a job,dude!"
    case Player(name, _) =>
      s"Hey, $name, nice to see you again!"
  }

  def printMessage(player: Player) = println(message(player))

  val p1 = Player("Tom", 200000)
  val p2 = Player("Tom", 20000)
  printMessage(p1)
  printMessage(p2)

  //值定义中的模式
  def currentPlayer(): Player = Player("Daniel", 3500)

  val Player(name, _) = currentPlayer()
  println(name)

  def gameResult(): (String, Int) = ("Daniel", 3500)

  val result = gameResult()
  println(result._1 + ": " + result._2)

  //for 语句中的模式
  def gameResults(): Seq[(String, Int)] = ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

  def hallOfFame = for {
    (name, score) <- gameResults()
    if (score > 5000)
  } yield name
  println(hallOfFame)

  val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil
  val notNullList = for{
    list @ head :: _ <- lists
  }yield list
  println(notNullList)

}
