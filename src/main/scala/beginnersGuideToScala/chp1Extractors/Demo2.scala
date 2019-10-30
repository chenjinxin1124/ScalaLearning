package beginnersGuideToScala.chp1Extractors

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:27 19-9-30
  * @Description:
  */
object Demo2 extends App {

  trait User {
    def name: String

    def score: Int
  }

  class FreeUser(val name: String, val score: Int, val upgradeProbability: Double) extends User

  class PremiumUser(val name: String, val score: Int) extends User

  object FreeUser {
    def unapply(user: FreeUser): Option[(String, Int, Double)] = Some((user.name, user.score, user.upgradeProbability))
  }

  object PremiumUser {
    def unapply(user: PremiumUser): Option[(String, Int)] = Some((user.name, user.score))
  }

  object premiumCandidate {
    def unapply(user: FreeUser): Boolean = user.upgradeProbability > 0.75
  }

  val user: User = new FreeUser("Daniel", 3000, 0.7d)
  val rel = user match {
    case FreeUser(name, _, p) =>
      if (p > 0.75) s"$name, what can me do for you today?"
      else s"Hello $name"
    case PremiumUser(name, _) =>
      s"Welcome back, dear $name"
  }
  println(rel)

  val user2: User = new FreeUser("lisi", 2500, 0.8d)
  val rel2 = user2 match {
    case freeUser@premiumCandidate() => "user.upgradeProbability > 0.75"
    case _ => "user.upgradeProbability < 0.75"
  }
  println(rel2)

}
