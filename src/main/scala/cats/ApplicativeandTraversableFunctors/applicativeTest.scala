package cats.ApplicativeandTraversableFunctors

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:07 19-9-3
  * @Description:
  */
object applicativeTest extends App {

  import cats.Functor

  trait Applicative[F[_]] extends Functor[F] {
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]

    def pure[A](a: A): F[A]
  }

  //    implicit def applicativeForEither[L]: Applicative[Either[L, *]] = new Applicative[Either[L, *]] {
  //      def product[A, B](fa: Either[L, A], fb: Either[L, B]): Either[L, (A, B)] = (fa, fb) match {
  //        case (Right(a), Right(b)) => Right((a, b))
  //        case (Left(l) , _       ) => Left(l)
  //        case (_       , Left(l) ) => Left(l)
  //      }
  //
  //      def pure[A](a: A): Either[L, A] = Right(a)
  //
  //      def map[A, B](fa: Either[L, A])(f: A => B): Either[L, B] = fa match {
  //        case Right(a) => Right(f(a))
  //        case Left(l)  => Left(l)
  //      }
  //    }
  import cats.Applicative
  //
  //    def product3[F[_]: Applicative, A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)] = {
  //      val F = Applicative[F]
  //      val fabc = F.product(F.product(fa, fb), fc)
  //      F.map(fabc) { case ((a, b), c) => (a, b, c) }
  //    }
}
