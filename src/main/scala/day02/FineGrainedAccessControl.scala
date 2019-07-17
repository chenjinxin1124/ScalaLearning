package FromJavaToScala

/**
  * @Author: gin
  * @Date: Created in 上午9:19 19-7-13
  * @Description:
  */
package society {
  package professional {

    class Executive {
      private[professional] val workDetails = ()
      val friends = ()
      //      private[social] val friends = ()
      private[this] val secrets = () //只能在该实例中访问

      def help(another: Executive): Unit = {
        println(another.workDetails)
        println(secrets)
        //  编译错误
        //  println(another.secrets)
      }

      val e = new Executive
      //  编译错误
      //  println(e.secrets)
    }

    class Assistant {
      def assist(anExec: Executive): Unit = {
        println(anExec.workDetails)
        println(anExec.friends)
      }
    }

  }

  package social {

    class Acquaintance {
      def socialize(person: professional.Executive): Unit = {
        println(person.friends)
        //  编译错误
        //  println(person.workDetails)
      }
    }

  }

}