package gettingStarted.FromJavaToScala

/**
  * 简单理解：
  * “隐式参数”相当于是“可变的默认参数”，但是“不能预先设置”
  *
  * @Author: gin
  * @Date: Created in 下午5:37 19-7-12
  * @Description:
  */
class Implicitarameters {
  def connectToNetwork(user: String)(implicit wifi: Wifi): Unit = {
    println(s"User:$user connected to WIFI $wifi")
  }

  def atOffice(): Unit = {
    println("--- at the office ---")

    implicit def officeNetwork: Wifi = new Wifi("office-network")

    val cafeteriaNetwork = new Wifi("cafe-connect")

    connectToNetwork("guest")(cafeteriaNetwork)
    connectToNetwork("Jill Coder")
    connectToNetwork("Joe Hacker")
  }

  def atJoesHome(): Unit = {
    println("--- at Joe's home ---")

    implicit def homeNetwork: Wifi = new Wifi("home-network")

    connectToNetwork("guest")(homeNetwork)
    connectToNetwork("Joe Hacker")
  }

  atOffice()
  atJoesHome()
}

class Wifi(name: String) {
  override def toString: String = name
}
