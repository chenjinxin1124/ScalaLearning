package beginnersGuideToScala.extractors

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:01 19-9-30
  * @Description:
  */
object Demo extends App {

  /*case class User(firstName: String, lastName: String, score: Int)
  def advance(xs: List[User]) = xs match {
    case User(_, _, score1) :: User(_, _, score2) :: _ => score1 - score2
    case _ => 0
  }*/

  trait User {
    def name: String
  }

  class FreeUser(val name: String) extends User

  class PremiumUser(val name: String) extends User

  object FreeUser {
    def unapply(user: FreeUser): Option[String] = Some(user.name)
  }

  object PremiumUser {
    def unapply(user: PremiumUser): Option[String] = Some(user.name)
  }

  println(FreeUser.unapply(new FreeUser("Daniel")))

  val user: User = new PremiumUser("zhangsan")
  val rel = user match {
    case FreeUser(name) => "FreeUser:" + name
    case PremiumUser(name) => "premiunUser:" + name
  }
  println(rel)

}
