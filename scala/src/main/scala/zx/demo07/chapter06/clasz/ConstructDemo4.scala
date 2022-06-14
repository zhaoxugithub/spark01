package main.scala.zx.demo07.chapter06.clasz

import scala.beans.BeanProperty

object ConstructDemo4 {

  def main(args: Array[String]): Unit = {
    val car = new Car
    car.name = "奔驰" //底层是 car.name.$eq()
    println(car.name) //底层使用的是car.name()

    car.setName("路虎")
    println(car.getName)
  }
}

class Car {
  @BeanProperty var name: String = _
}
