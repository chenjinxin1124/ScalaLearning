package Interview.scalaCode.zzke

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:34 19-9-29
  * @Description:
  */
object A1 extends App {

  Source.fromFile("/home/gin/cjx/docs/3.csv").getLines().map(line => (line.split(",")(2), 1)).toList.groupBy(_._1).map(t => (t._2.size, t._1)).toList.sortBy(_._1).reverse.foreach(println(_))

}
