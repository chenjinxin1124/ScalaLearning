package FromJavaToScala

/**
  * @Author: gin
  * @Date: Created in 下午5:06 19-7-12
  * @Description:
  */
class DefaultValues {
  //设置默认参数
  def mail(destination: String = "head office", mailClass: String = "first"): Unit =
    println(s"sending to $destination by $mailClass class")

  mail("Houston office", "Priority")
  mail("Boston office")
  mail()
  //使用命名参数
  mail(mailClass = "Priority", destination = "Bahamas office")
  mail(mailClass = "Priority")
}
