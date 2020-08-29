package zx.demo06

/**
 * scala case
 *
 * @param name
 * @param age
 */
class Person01(name: String, age: Int) {
}

case class Person02(name: String, age: Int) {

}


object Scala_Case_Class {

  def main(args: Array[String]): Unit = {

    val p1: Person01 = new Person01("zhangsan", 20)
    val p2: Person01 = new Person01("zhangsan", 20)
    println(p1.equals(p2))
    println(p1 == p2)

    val p3: Person02 = Person02("lisi", 20)
    val p4: Person02 = Person02("lisi", 20)
    println(p3.equals(p4))
    println(p3 == p4)


  }
}
