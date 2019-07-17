package FromJavaToScala

/**
  * @Author: gin
  * @Date: Created in 下午4:06 19-7-12
  * @Description:
  */
class ArgType {
  def function(input: Int*): Unit = println(input.getClass)

  //  function(1,2,3)
}
