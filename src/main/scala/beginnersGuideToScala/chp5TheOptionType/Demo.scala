package beginnersGuideToScala.chp5TheOptionType

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:02 19-10-29
  * @Description:
  */

object Demo extends App {

  //使用 Option
  case class User(
                   id: Int,
                   firstName: String,
                   lastName: String,
                   age: Int,
                   gender: Option[String]
                 )

  object UserRepository {
    private val users = Map(1 -> User(1, "John", "Doe", 32, Some("male")),
      3 -> User(1, "John", "Doe", 32, Some("famale")),
      2 -> User(2, "Johanna", "Doe", 30, None))

    def findById(id: Int): Option[User] = users.get(id)

    def findAll = users.values
  }

  val user1 = UserRepository.findById(1)
  if (user1.isDefined) {
    println(user1.get.firstName)
  }

  //作为集合的 Option
  UserRepository.findById(2).foreach(user => println(user.firstName))
  val age = UserRepository.findById(1).map(_.age)
  println(age)

  //Option 与 flatMap
  val gender: Option[Option[String]] = UserRepository.findById(1).map(_.gender)
  val gender1 = UserRepository.findById(1).flatMap(_.gender)
  val gender2 = UserRepository.findById(2).flatMap(_.gender)
  val gender3 = UserRepository.findById(3).flatMap(_.gender)
  println(gender) // Some(Some(male))
  println(gender1) // Some("male")
  println(gender2) // None
  println(gender3) // None

  val names: List[List[String]] =
    List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Westheide"))
  println(names.map(_.map(_.toUpperCase)))
  println(names.flatMap(_.map(_.toUpperCase)))

  println(UserRepository.findById(1).filter(_.age > 30))
  println(UserRepository.findById(2).filter(_.age > 30))
  println(UserRepository.findById(3).filter(_.age > 30))

  //for 语句
  val gender4 = for {
    user <- UserRepository.findById(1)
    gender <- user.gender
  } yield gender
  println(gender4)

  val firstNames: Iterable[String] = for {
    user <- UserRepository.findAll
    firstName <- Some(user.firstName)
  } yield firstName
  println(firstNames)

  val genders: Iterable[String] = for {
    user <- UserRepository.findAll
    gender <- user.gender
  } yield gender
  println(genders)

  //在生成器左侧使用
  //在生成器左侧使用 Some 模式就可以在结果集中排除掉值为 None 的元素。
  val genders2: Iterable[String] = for {
    User(_, _, _, _, Some(gender)) <- UserRepository.findAll
  } yield gender
  println(genders2)

  //链接 Option
  //如果第一个 Option 是 None ， orElse 方法会返回传名参数的值，否则，就直接返回这个 Option。
  case class Resource(content: String)
  val resourceFromConfigDir: Option[Resource] = Some(Resource("classpath"))
  val resourceFromClasspath: Option[Resource] = Some(Resource("I was found on the classpath"))
  val resource = resourceFromConfigDir orElse resourceFromClasspath
  println(resource)

}
