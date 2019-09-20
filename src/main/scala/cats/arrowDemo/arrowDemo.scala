package cats.arrowDemo

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:51 19-9-18
  * @Description:
  */
object arrowDemo extends App {

  import cats.arrow.Arrow
  import cats.implicits._

  def combine[F[_, _] : Arrow, A, B, C](fab: F[A, B], fac: F[A, C]): F[A, (B, C)] =
    Arrow[F].lift((a: A) => (a, a)) >>> (fab *** fac)

  val mean: List[Int] => Double =
    combine((_: List[Int]).sum, (_: List[Int]).size) >>> { case (x, y) => x.toDouble / y }

  val variance: List[Int] => Double =
    combine(((_: List[Int]).map(x => x * x)) >>> mean, mean) >>> { case (x, y) => x - y * y }

  val meanAndVar: List[Int] => (Double, Double) = combine(mean, variance)

  val r1 = meanAndVar(List(1, 2, 3, 4))
  println(r1)

  val mean2: List[Int] => Double = xs => xs.sum.toDouble / xs.size

  val variance2: List[Int] => Double = xs => mean2(xs.map(x => x * x)) - scala.math.pow(mean2(xs), 2.0)

  val meanAndVar2: List[Int] => (Double, Double) = combine(mean2, variance2)

  val r2 = meanAndVar2(List(1, 2, 3, 4))
  println(r2)

  import cats.data.Kleisli

  val headK = Kleisli((_: List[Int]).headOption)
  val lastK = Kleisli((_: List[Int]).lastOption)

  //  val headPlusLast = combine(headK, lastK) >>> Arrow[Kleisli[Option, Int, Int]](Int).lift(((_: Int) + (_: Int)).tupled)
  //  val r3 = headPlusLast.run(List(2, 3, 5, 8))
  //  val r4 = headPlusLast.run(Nil)

  case class FancyFunction[A, B](run: A => (FancyFunction[A, B], B))

  def runList[A, B](ff: FancyFunction[A, B], as: List[A]): List[B] = as match {
    case h :: t =>
      val (ff2, b) = ff.run(h)
      b :: runList(ff2, t)
    case _ => List()
  }

  implicit val arrowInstance: Arrow[FancyFunction] = new Arrow[FancyFunction] {

    override def lift[A, B](f: A => B): FancyFunction[A, B] = FancyFunction(lift(f) -> f(_))

    override def first[A, B, C](fa: FancyFunction[A, B]): FancyFunction[(A, C), (B, C)] = FancyFunction { case (a, c) =>
      val (fa2, b) = fa.run(a)
      (first(fa2), (b, c))
    }

    override def id[A]: FancyFunction[A, A] = FancyFunction(id -> _)

    override def compose[A, B, C](f: FancyFunction[B, C], g: FancyFunction[A, B]): FancyFunction[A, C] = FancyFunction { a =>
      val (gg, b) = g.run(a)
      val (ff, c) = f.run(b)
      (compose(ff, gg), c)
    }
  }

  def accum[A, B](b: B)(f: (A, B) => B): FancyFunction[A, B] = FancyFunction { a =>
    val b2 = f(a, b)
    (accum(b2)(f), b2)
  }

  val r5 = runList(accum[Int, Int](0)(_ + _), List(6, 5, 4, 3, 2, 1))
  println(r5) //List(6, 11, 15, 18, 20, 21)

  import cats.kernel.Monoid

  def sum[A: Monoid]: FancyFunction[A, A] = accum(Monoid[A].empty)(_ |+| _)

  def count[A]: FancyFunction[A, Int] = Arrow[FancyFunction].lift((_: A) => 1) >>> sum

  def avg: FancyFunction[Int, Double] =
    combine(sum[Int], count[Int]) >>> Arrow[FancyFunction].lift { case (x, y) => x.toDouble / y }

  val r6 = runList(avg, List(1, 10, 100, 1000))
  println(r6)
}
