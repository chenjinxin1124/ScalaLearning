package cats.monad

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:33 19-9-3
  * @Description:
  */
object monadTest extends App {
  val r1 = List(List(1), List(2, 3)).flatten
  println(r1)

  import cats._

  implicit def optionMonad(implicit app: Applicative[Option]) =
    new Monad[Option] {
      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
        app.map(fa)(f).flatten
      override def pure[A](a: A): Option[A] = app.pure(a)

      @annotation.tailrec
      def tailRecM[A, B](init: A)(fn: A => Option[Either[A, B]]): Option[B] =
        fn(init) match {
          case None => None
          case Some(Right(b)) => Some(b)
          case Some(Left(a)) => tailRecM(a)(fn)
        }
    }

  import scala.reflect.runtime.universe

  universe.reify(
    for {
      x <- Some(1)
      y <- Some(2)
    } yield x + y
  ).tree

  import scala.annotation.tailrec

  implicit val optionMonad = new Monad[Option] {
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
    def pure[A](a: A): Option[A] = Some(a)

    @tailrec
    def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = f(a) match {
      case None              => None
      case Some(Left(nextA)) => tailRecM(nextA)(f) // continue the recursion
      case Some(Right(b))    => Some(b)            // recursion done
    }
  }

  import cats.implicits._

  val r2 = Monad[List].ifM(List(true, false, true))(ifTrue = List(1, 2), ifFalse = List(3, 4))
  println(r2)

}
