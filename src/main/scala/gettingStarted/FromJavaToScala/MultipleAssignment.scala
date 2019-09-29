package gettingStarted.FromJavaToScala

/**
  * @Author: gin
  * @Date: Created in 下午3:13 19-7-12
  * @Description:
  */
class MultipleAssignment {
  /**
    * 多参数赋值
    */
  val (firstName, lastNmae, emailAddress) = getPersonInfo(1)

  println(s"First Name: $firstName")
  println(s"Last Name: $lastNmae")
  println(s"Email Address: $emailAddress")

  val p = getPersonInfo(1)
  /**
    * 元组中的下标从 0 开始
    */
  println(s"First Name: " + p._1)
  println(s"Last Name: " + p._2)
  println(s"Email Address: ${p._3}")

  def getPersonInfo(primaryKey: Int) = {
    ("a", "b", "c@d.com")
  }
}
