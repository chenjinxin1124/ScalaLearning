package beginnersGuideToScala.chp13PathDependentTypes

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:04 19-11-1
  * @Description:
  */
object chp13PathDependentTypes extends App {

  object Franchise {

    case class Character(name: String)

  }

  class Franchise(name: String) {

    import Franchise.Character

    def createFanFiction(
                          lovestruck: Character,
                          objectOfDesire: Character): (Character, Character) = (lovestruck, objectOfDesire)
  }

  val starTrek = new Franchise("Star Trek")
  val starWars = new Franchise("Star Wars")

  val quark = Franchise.Character("Quark")
  val jadzia = Franchise.Character("Jadzia Dax")

  val luke = Franchise.Character("Luke Skywalker")
  val yoda = Franchise.Character("Yoda")

  val r1 = starTrek.createFanFiction(lovestruck = jadzia, objectOfDesire = luke)
  println(r1)

  //路径依赖类型
  class Franchise2(name: String) {

    case class Character2(name: String)

    def createFanFictionWith(
                              lovestruck: Character2,
                              objectOfDesire: Character2): (Character2, Character2) = (lovestruck, objectOfDesire)
  }

  val starTrek2 = new Franchise2("Star Trek")
  val starWars2 = new Franchise2("Star Wars")

  val quark2 = starTrek2.Character2("Quark")
  val jadzia2 = starTrek2.Character2("Jadzia Dax")

  val luke2 = starWars2.Character2("Luke Skywalker")
  val yoda2 = starWars2.Character2("Yoda")

  val r2 = starTrek2.createFanFictionWith(lovestruck = quark2, objectOfDesire = jadzia2)
  val r3 = starWars2.createFanFictionWith(lovestruck = luke2, objectOfDesire = yoda2)
  println(r2)
  println(r3)

  //val r4 = starTrek2.createFanFictionWith(lovestruck = jadzia2, objectOfDesire = luke2)报错，不匹配

  //抽象类型成员
  //开发一个键值存储，只支持读取和存放操作，但是类型安全的。
  object AwesomeDB {

    abstract class Key(name: String) {
      type Value
    }

  }

  import AwesomeDB.Key

  class AwesomeDB {

    import collection.mutable.Map

    val data = Map.empty[Key, Any]

    def get(key: Key): Option[key.Value] = data.get(key).asInstanceOf[Option[key.Value]]

    def set(key: Key)(value: key.Value): Unit = data.update(key, value)
  }

  trait IntValued extends Key {
    type Value = Int
  }

  trait StringValued extends Key {
    type Value = String
  }

  object Keys {
    val foo = new Key("foo") with IntValued
    val bar = new Key("bar") with StringValued
  }

  val dataStore = new AwesomeDB
  dataStore.set(Keys.foo)(23)
  val i: Option[Int] = dataStore.get(Keys.foo)
  println(i)
  //  dataStore.set(Keys.foo)("23") // does not compile
  val dataStore2 = new AwesomeDB
  dataStore2.set(Keys.bar)("23")
  val j=dataStore2.get(Keys.bar)
  println(j)

}
