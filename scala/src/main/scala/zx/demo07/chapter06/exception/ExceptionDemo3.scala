package main.scala.zx.demo07.chapter06.exception

object ExceptionDemo3 {

  def main(args: Array[String]): Unit = {
    f11()
  }

  @throws(classOf[NumberFormatException])
  def f11(): Unit = {
    "to".toInt
  }

}
