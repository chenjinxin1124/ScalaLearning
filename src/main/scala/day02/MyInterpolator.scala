package day02

/**
  * @Author: gin
  * @Date: Created in 下午5:13 19-7-13
  * @Description:
  */
object MyInterpolator {

  implicit class Interpolator(val context: StringContext) extends AnyVal {
    def mask(args: Any*): StringBuilder = {
      val processed = context.parts.zip(args).map { item =>
        val (text, expression) = item
        if (text.endsWith("^"))
          s"${text.split('^')(0)}$expression"
        else
          s"$text...${expression.toString takeRight 4}"
      }.mkString

      new StringBuilder(processed).append(context.parts.last)
    }
  }

}
