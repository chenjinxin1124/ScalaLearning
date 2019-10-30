package beginnersGuideToScala.chp6ErrorHandlingWithTry

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:20 19-10-30
  * @Description:
  */
object Demo extends App {

  case class Customer(age: Int)
  class Cigarettes
  case class UnderAgeException(message: String) extends Exception(message)
  def buyCigarettes(customer: Customer): Cigarettes =
    if (customer.age < 16)
      throw UnderAgeException(s"Customer must be older than 16 but was ${customer.age}")
    else new Cigarettes

  val youngCustomer = Customer(15)
  val throw1 = try {
    buyCigarettes(youngCustomer)
    "Yo, here are your cancer sticks! Happy smokin'!"
  } catch {
    case UnderAgeException(msg) => msg
  }
  println(throw1)

}
