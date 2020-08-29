package zx.demo06


trait God {
  def say(): Unit = {
    println("God say...")
  }
}

trait Dog {
  def ku(): Unit = {
    println("dag----ku")
  }

  def run(): Unit
}

trait Mg extends God { //Mg 可以extends God with Dog
  def play(): Unit = {
    println("Mg----play")
  }
}

class Person(name: String) extends Dog with God {

  override def say(): Unit = {
    println(s"${name}Person----say")
  }

  override def run(): Unit = {
    println(s"${name}person-----run 实现")
  }
}

object Scala_trait {


  def main(args: Array[String]): Unit = {

    val person: Person = new Person("zhaoxu")
    person.ku()
    person.run()
    person.say()


    println("--------------------")

    val dog: Dog = new Person("dog")
    dog.ku()
    dog.run()

  }

}
