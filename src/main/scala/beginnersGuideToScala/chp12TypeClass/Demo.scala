package beginnersGuideToScala.chp12TypeClass

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:16 19-10-31
  * @Description:
  */
object Demo extends App {

  //创建类型类
  object Math {

    //定义一个特质
    import annotation.implicitNotFound

    //自定义错误消息
    @implicitNotFound("No member of type class NumberLike in scope for ${T}")
    trait NumberLike[T] {
      def plus(x: T, y: T): T

      def divide(x: T, y: Int): T

      def minus(x: T, y: T): T
    }

    //提供默认成员
    object NumberLike {

      implicit object NumberLikeDouble extends NumberLike[Double] {
        def plus(x: Double, y: Double): Double = x + y

        def divide(x: Double, y: Int): Double = x / y

        def minus(x: Double, y: Double): Double = x - y
      }

      implicit object NumberLikeInt extends NumberLike[Int] {
        def plus(x: Int, y: Int): Int = x + y

        def divide(x: Int, y: Int): Int = x / y

        def minus(x: Int, y: Int): Int = x - y
      }

    }

  }

  //运用类型类
  object Statistics {

    import Math.NumberLike

    def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
      ev.divide(xs.reduce(ev.plus(_, _)), xs.size)
  }

  val numbers = Vector[Double](13, 23.0, 42, 45, 61, 73, 96, 100, 199, 420, 900, 3839)
  println(Statistics.mean(numbers))

  //  val strings = Vector[String]("123","234")
  //  println(Statistics.mean(strings))

  //上下文绑定
  object Statistics2 {

    import Math.NumberLike

    def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
      ev.divide(xs.reduce(ev.plus(_, _)), xs.size)

    def median[T: NumberLike](xs: Vector[T]): T = xs(xs.size / 2)

    def quartiles[T: NumberLike](xs: Vector[T]): (T, T, T) =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))

    def iqr[T: NumberLike](xs: Vector[T]): T = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) =>
        implicitly[NumberLike[T]].minus(upperQuartile, lowerQuartile)
    }
  }

  //自定义的类型类成员
  

}
