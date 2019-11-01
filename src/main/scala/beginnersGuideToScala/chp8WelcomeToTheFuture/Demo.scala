package beginnersGuideToScala.chp8WelcomeToTheFuture

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:45 19-10-31
  * @Description:
  */
object Demo extends App {

  //卡布奇诺
  /*研磨所需的咖啡豆
  加热一些水
  用研磨好的咖啡豆和热水制做一杯咖啡
  打奶泡
  结合咖啡和奶泡做成卡布奇诺*/

  import scala.util.Try

  // Some type aliases, just for getting more meaningful method signatures:
  type CoffeeBeans = String
  type GroundCoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class GrindingException(msg: String) extends Exception(msg)

  case class FrothingException(msg: String) extends Exception(msg)

  case class WaterBoilingException(msg: String) extends Exception(msg)

  case class BrewingException(msg: String) extends Exception(msg)

  //使用 Future
  import scala.concurrent.future
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.util.Random

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  //回调
  grind("arabica beans").onSuccess { case ground =>
    println("okay, got my ground coffee")
  }

  import scala.util.{Success, Failure}

  grind("baked beans").onComplete {
    case Success(ground) => println(s"got my $ground")
    case Failure(ex) => println("This grinder needs a replacement, seriously!")
  }

  //Map 操作
  val tempreatureOkay: Future[Boolean] = heatWater(Water(25)) map { water =>
    println("we're in the future!")
    (80 to 85) contains (water.temperature)
  }
  //  println(tempreatureOkay)

  //FlatMap 操作
  def temperatureOkay(water: Water): Future[Boolean] = Future {
    (80 to 85) contains (water.temperature)
  }

  val nestedFuture: Future[Future[Boolean]] = heatWater(Water(25)) map {
    water => temperatureOkay(water)
  }

  val flatFuture: Future[Boolean] = heatWater(Water(25)) flatMap {
    water => temperatureOkay(water)
  }
  println(nestedFuture)
  println(flatFuture)

  //for 语句
  val acceptable: Future[Boolean] = for {
    heatedWater <- heatWater(Water(25))
    okay <- temperatureOkay(heatedWater)
  } yield okay

  //  def prepareCappuccino(): Future[Cappuccino] = {
  //    val groundCoffee = grind("arabica beans")
  //    val heatedWater = heatWater(Water(20))
  //    val frothedMilk = frothMilk("milk")
  //    for {
  //      ground <- groundCoffee
  //      water <- heatedWater
  //      foam <- frothedMilk
  //      espresso <- brew(ground, water)
  //    } yield combine(espresso, foam)
  //  }


}
