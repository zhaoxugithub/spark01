package main.scala.zx.demo07.chapter06.introduction0

object demo01 {


  def main(args: Array[String]): Unit = {
    val value = new DefaultValue
    println(value.name)
    println(value.age)
    println(value.salary)
    println(value.isDead)


    val detail = new ClassDetail
    println(detail.name)//String类型的null
    println(detail.age.getClass)
    println(detail.address)//Null类型的null
  }

}

class DefaultValue {
  var name: String = _
  var age: Int = _
  var salary: Double = _
  var isDead: Boolean = _

}

class ClassDetail{
  var age = 25//3.
  var name: String = null//4.
  var address = null//4.
}

