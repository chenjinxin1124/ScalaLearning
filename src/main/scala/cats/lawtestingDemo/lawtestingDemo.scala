package cats.lawtestingDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:54 19-9-20
  * @Description:
  */
object lawtestingDemo extends App {

  import cats._

  sealed trait Tree[+A]

  case object Leaf extends Tree[Nothing]

  case class Node[A](p: A, left: Tree[A], right: Tree[A]) extends Tree[A]

//  object Tree {
//    implicit val functorTree: Functor[Tree] = new Functor[Tree] {
//      def map[A, B](tree: Tree[A])(f: A => B) = tree match {
//        case Leaf => Leaf
//        case Node(p, left, right) => Node(f(p), map(left)(f), map(right)(f))
//      }
//    }
//  }
//
//  implicit def eqTree[A: Eq]: Eq[Tree[A]] = Eq.fromUniversalEquals
//
//  import org.scalacheck.{Arbitrary, Gen}
//
//  object arbitraries {
//    implicit def arbTree[A: Arbitrary]: Arbitrary[Tree[A]] =
//      Arbitrary(Gen.oneOf(Gen.const(Leaf), (for {
//        e <- Arbitrary.arbitrary[A]
//      } yield Node(e, Leaf, Leaf)))
//      )
//  }

}
