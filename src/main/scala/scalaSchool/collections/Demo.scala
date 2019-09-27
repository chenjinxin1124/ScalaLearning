package scalaSchool.collections

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:05 19-9-26
  * @Description:
  */
object Demo extends App {

  println("-----------------------------基本数据结构 -------------------------------------")
  //  数组是有序的，可以包含重复项，并且可变。
  val numbers = Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
  numbers(3) = 10
  //  numbers: Array[Int] = Array(1, 2, 3, 10, 5, 1, 2, 3, 4, 5)
  //    列表是有序的，可以包含重复项，不可变。
  val numbers2 = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
  //    numbers: List[Int] = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

  //  numbers2(3) = 10
  //    <console>:9: error: value update is not a member of List[Int]
  //      numbers(3) = 10
  //    集合无序且不可包含重复项。
  val numbers3 = Set(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
  //    numbers: scala.collection.immutable.Set[Int] = Set(5, 1, 2, 3, 4)
  /*元组 Tuple

元组在不使用类的情况下，将元素组合起来形成简单的逻辑集合。

scala> val hostPort = ("localhost", 80)
hostPort: (String, Int) = (localhost, 80)

与样本类不同，元组不能通过名称获取字段，而是使用位置下标来读取对象；而且这个下标基于1，而不是基于0。

scala> hostPort._1
res0: String = localhost

scala> hostPort._2
res1: Int = 80

元组可以很好得与模式匹配相结合。

hostPort match {
case ("localhost", port) => ...
case (host, port) => ...
}

在创建两个元素的元组时，可以使用特殊语法：->

scala> 1 -> 2
res0: (Int, Int) = (1,2)*/
  /*映射 Map

它可以持有基本数据类型。

Map(1 -> 2)
Map("foo" -> "bar")

这看起来像是特殊的语法，不过不要忘了上文讨论的->可以用来创建二元组。

Map()方法也使用了从第一节课学到的变参列表：Map(1 -> "one", 2 -> "two")将变为 Map((1, "one"), (2, "two"))，其中第一个元素是映射的键，第二个元素是映射的值。

映射的值可以是映射甚至是函数。

Map(1 -> Map("foo" -> "bar"))

Map("timesTwo" -> { timesTwo(_) })*/

  println("-----------------------------函数组合子------------------------------------")
  val numbers4 = List(1, 2, 3, 4)
  numbers4.map(_ * 2).foreach(println(_))
  println(numbers4.filter(_ % 2 == 0))

  def isEvent(i: Int) = i % 2 == 0

  println(numbers4.filter(isEvent))
  println(List(1, 2, 3).zip(List("a", "b", "c")))

  //partition将使用给定的谓词函数分割列表。
  val numbers5 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  println(numbers5.partition(_ % 2 == 0)) //(List(2, 4, 6, 8, 10),List(1, 3, 5, 7, 9))

  //find返回集合中第一个匹配谓词函数的元素。
  println(numbers5.find(_ > 5))

  //  drop & dropWhile
  //  drop 将删除前i个元素

  //  scala> numbers.drop(5)
  //  res0: List[Int] = List(6, 7, 8, 9, 10)
  //
  //  dropWhile 将删除匹配谓词函数的第一个元素。例如，如果我们在numbers列表上使用dropWhile函数来去除奇数, 1将被丢弃（但3不会被丢弃，因为他被2“保护”了）。
  //
  //  scala> numbers.dropWhile(_ % 2 != 0)
  //  res0: List[Int] = List(2, 3, 4, 5, 6, 7, 8, 9, 10)

  //  0为初始值（记住numbers是List[Int]类型），m作为一个累加器。
  //  可视化观察运行过程：
  val rel = numbers5.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
  println(rel)
  val rel2 = numbers5.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
  println(rel2)

  println(List(List(1, 2), List(3, 4)).flatten)

  def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
    numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
      fn(x) :: xs
    }
  }

  def timesTwo(x: Int) = x * 2

  println(ourMap(numbers5, timesTwo(_)))

  val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
  println(extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200))
  println(extensions.filter({ case (name, extension) => extension < 200 }))

}
