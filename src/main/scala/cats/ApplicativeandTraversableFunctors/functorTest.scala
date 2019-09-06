package cats.ApplicativeandTraversableFunctors

import akka.http.scaladsl.model.Uri.Empty

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:32 19-9-3
  * @Description:
  */
object functorTest extends App {

  import cats.Functor
  import cats.implicits._

  val listOption = List(Some(1), None, Some(2))
  val r1 = Functor[List].compose[Option].map(listOption)(_ + 1)
  println(r1) //List(Some(2), None, Some(3))

  def needsFunctor[F[_] : Functor, A](fa: F[A]): F[Unit] = Functor[F].map(fa)(_ => ())

  //  def foo: List[Option[Unit]] = {
  //    val listOptionFunctor = Functor[List].compose[Option]
  //    type ListOption[A] = List[Option[A]]
  //    needsFunctor[ListOption, Int](listOption)(listOptionFunctor)
  //  }

  import cats.data.Nested
  import cats.implicits._

  val nested: Nested[List, Option, Int] = Nested(listOption)
  val r2 = nested.map(_ + 1)
  println(r2)
}
