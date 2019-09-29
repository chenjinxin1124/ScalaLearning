package gettingStarted.Intermixing

/**
  * @Author: gin
  * @Date: Created in 上午10:05 19-7-15
  * @Description:
  */
class Person(val firstName: String, val lastName: String) {
  override def toString: String = firstName + " " + lastName
}
