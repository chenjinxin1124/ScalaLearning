package cats.VarianceAndFunctor

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:57 19-9-4
  * @Description:
  */
object contravariantTest extends App {

  import cats._

  import cats.implicits._

  case class Money(amount: Int)

  case class Salary(size: Money)

  implicit val showMoney: Show[Money] = Show.show(m => s"$$${m.amount}")

  implicit val showSalary: Show[Salary] = showMoney.contramap(_.size)

  val r1 = Salary(Money(1000)).show
  println(r1)

  val r2 = Ordering.Int.compare(2, 1)
  val r3 = Ordering.Int.compare(1, 2)
  println(r2, r3)

  import scala.math.Ordered._

  implicit val moneyOrdering: Ordering[Money] = Ordering.by(_.amount)

  println(Money(100) < Money(200))

  class A

  class B extends A

  val b: B = new B
  val a: A = b

  val showA: Show[A] = Show.show(a => "a!")
  val showB1: Show[B] = showA.contramap(b => b: A)
  val showB2: Show[B] = showA.contramap(identity[A])
  val showB3: Show[B] = Contravariant[Show].narrow[A, B](showA)
  println(showA, showB1, showB2, showB3)
}
