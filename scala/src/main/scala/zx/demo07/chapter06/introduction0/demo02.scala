package main.scala.zx.demo07.chapter06.introduction0

import java.util.Date

object demo02 {


  def main(args: Array[String]): Unit = {
    //    fun01("dsfafdas")
  }


  //函数嵌套
  def fun01(str: String): Unit = {
    def fun(): Unit = {
      println(str)
    }

    fun()
  }

  var res: (Int, Int) => Int = (a: Int, b: Int) => a + b;
  private val i1: Int = res(1, 3)

  //偏应用函数
  def fun02(date: Date, tp: String, msg: String): Unit = {
    println(s"$date\t$tp\t$msg")
  }

  fun02(new Date(), "info", "msg")

  private val stringToUnit: String => Unit = fun02(date = new Date(), "info", _: String)
  stringToUnit("message")


  //可变参数
  def fun03(a: Int*): Unit = {
    val rs = for (elem <- a) yield elem
    println(rs.mkString(","))
    //    def foreach[U](f: A => U): Unit
    //    a.foreach((x: Int) => println(x))
    //等价于
    //    a.foreach(println(_))
  }

  fun03(1, 2, 3, 3, 4, 5, 6)


  //高阶函数
  //函数作为参数，函数作为函数值
  def computer(a: Int, b: Int, f: (Int, Int) => Int): Unit = {
    val i = f(a, b)
    println(i)
  }

  computer(9, 3, (x: Int, y: Int) => x + y)
  computer(9, 3, (x: Int, y: Int) => x - y)
  computer(9, 3, (x: Int, y: Int) => x * y)
  computer(9, 3, (x: Int, y: Int) => x / y)
  computer(9, 3, _ * _)

  //函数作为返回值：
  def factory(i: String): (Int, Int) => Int = {
    def plus(x: Int, y: Int): Int = {
      x + y
    }

    if (i.equals("+")) {
      plus _
    } else if (i.equals("*")) {
      //可以使用匿名函数
      (x: Int, y: Int) => x * y
    } else if (i.equals("/")) {
      //函数签名中不能使用下划线_
      (x: Int, y: Int) => x / y
    } else {
      (x: Int, y: Int) => x - y
    }
  }

  //柯里化
  println("==========柯里化============")

  def fun05(a: Int)(b: Int)(c: String): Unit = {
    println(s"$a\t$b\t$c")
  }

  fun05(4)(6)("dddd")


  //如果有一个场景是明确：第一个参数明确是数值类型，第二个参数是字符串类型，并且有明确的界限可以区分，可以尝试用柯里化
  def fun06(a: Int*)(str: String*): Unit = {
    a.foreach(x => print(x + " "))
    str.foreach(x => print(x + " "))
    a.foreach(println(_))
    a.foreach(println)
  }

  def fun07(ab: Any*): Unit = {
    ab.foreach(x => print(x + " "))
  }

  fun06(1, 2, 3, 4)("a", "b", "c")
  fun07(1, 2, 3, 4, "a", "b", "c")
}