package cats.parallelT

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:42 19-9-11
  * @Description:
  */
object parallelTest extends App {

  import cats.implicits._
  import cats.data._

  case class Name(value: String)

  case class Age(value: Int)

  case class Person(name: Name, age: Age)

  def parse(s: String): Either[NonEmptyList[String], Int] = {
    if (s.matches("-?[0-9]+")) Right(s.toInt)
    else Left(NonEmptyList.one(s"$s is not a value integer."))
  }

  def validateAge(a: Int): Either[NonEmptyList[String], Age] = {
    if (a > 18) Right(Age(a))
    else Left(NonEmptyList.one(s"$a is not old enough"))
  }

  def validateName(n: String): Either[NonEmptyList[String], Name] = {
    if (n.length >= 8) Right(Name(n))
    else Left(NonEmptyList.one(s"$n Does not have enough characters"))
  }

  def parsePerson2(ageString: String, nameString: String) =
    for {
      age <- parse(ageString)
      person <- (validateName(nameString).toValidated, validateAge(age).toValidated)
        .mapN(Person)
        .toEither
    } yield person

  def parsePerson(ageString: String, nameString: String) =
    for {
      age <- parse(ageString)
      person <- (validateName(nameString), validateAge(age)).parMapN(Person)
    } yield person

  val r1 = List(Either.right(42), Either.left(NonEmptyList.one("Error 1")), Either.left(NonEmptyList.one("Error 2"))).parSequence
  val r2 = (List(1, 2, 3), List(4, 5, 6)).mapN(_ + _)
  val r3 = (List(1, 2, 3), List(4, 5, 6)).parMapN(_ + _)
  val r4 = (List(1, 2, 3), List(4, 5, 6), List(4, 5, 6)).parMapN(_ + _ + _)
  val r5 = (List(1, 2, 3), List(4, 5, 6), List(4, 5, 6), List(4, 5, 6)).parMapN(_ + _ + _ + _)
  println(r1)
  println(r2)
  println(r3)
  println(r4)

}
