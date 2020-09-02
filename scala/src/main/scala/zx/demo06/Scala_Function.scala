package zx.demo06

import java.util
import java.util.Date

object Scala_Function {

  def main(args: Array[String]): Unit = {

    //方法  函数
    println("---------1.basic -------------")

    //返回值，参数，函数体
    def fun01(): Unit = {
      println("hello world")
    }

    fun01()
    //y 返回的是函数返回值，如果函数无返回值则y 的值是（）
    val y = fun01()
    println(y)

    /**
     * val y = fun01()
     * println(y)
     * 上面两行代码 等于
     * println("sss" + fun01)
     */
    println("sss" + fun01)


    //有return 则必须制定函数返回值,给定返回值但是没有return 会把函数体中最后一行当成函数返回
    def fun02(): Unit = {
      new util.ArrayList[String]()
    }

    val result: Unit = fun02()
    //result 的返回值是（）
    println(result)


    def fun03(): util.List[String] = {
      val li: util.List[String] = new util.ArrayList[String]()
      li.add("aaa")
      return li
    }

    val fun03Result: util.List[String] = fun03()
    println(fun03Result)

    def fun04(a: Int): Unit = {
      println(a)
    }

    fun04(20)

    println("---------------递归函数---------------")

    def fun05(num: Int): Int = {
      if (num == 1) {
        num
      } else {
        num * fun05(num - 1)
      }
    }

    val fun05Result: Int = fun05(10)
    println(fun05Result)

    println("------------------默认值函数--------------")

    def fun06(str: String = "zhaoxu", num: Int): Unit = {
      println(s"${num}\t${str}")
    }

    fun06(num = 20)
    fun06("zz", 22)

    println("-------------嵌套函数-------------")

    def fun07(a: String): Unit = {
      def fun06(): Unit = {
        println(a)
      }

      fun06()
    }

    fun07("ceshi")

    println("------------偏应用函数------------")

    def fun08(date: Date, tp: String, msg: String): Unit = {
      println(s"${date}\t${tp}\t${msg}")
    }

    var info = fun08(new Date(), "info", "ok")
    //info()报错
    var info1 = fun08(_: Date, "info", _: String) //单单写这个是不能执行方法的 ，必须执行info1(new Date(), "info1_ok")这个之后才能
    info1(new Date(), "info1_ok")
  }

  println("-------------可变参数--------------")

  def fun09(num: Int*): Unit = {

    for (a <- num) {
      println(a)
    }
    //      def foreach[U](f: A => U): Unit
    //      a.foreach(   (x:Int)=>{println(x)}   )
    //      a.foreach(   println(_)   )
    num.foreach(println)

  }

  fun09(1)
  fun09(1, 3, 2, 4, 6, 7, 8)


  println("-----------高阶函数--------------")

  //函数作为参数，函数作为返回值
  //函数作为参数
  def computer(a: Int, b: Int, f: (Int, Int) => Int): Int = {
    //这里的return 可以省略
    return f(a, b)
  }

  val res: Int = computer(3, 4, (x, y) => {
    x + y
  })
  println(res)

  //如果computer返回值是空，那么调用computer 可以写成这样 computer(3, 8, _ * _)

  //函数作为返回值
  def factory(i: String): (Int, Int) => Int = {

    def plus(x: Int, y: Int): Int = {
      x + y
    }

    def chen(x: Int, y: Int): Int = {
      x * y
    }

    if (i.equals("+")) {
      plus
    } else {
      chen
    }
  }

  private val i: Int = factory("+")(2, 3)
  println(i)

  private val i1: Int = computer(4, 4, factory("*"))
  println(i1)


  println("---------------柯里化一-------------------")

  //普通方法1 参数为两个的方法
  def method1(x: Int, y: Int): Int = {
    x + y
  }

  //调用
  val i122: Int = method1(1, 2)

  println(i122)

  //普通方法2 参数为一个的方法
  def method2(x: Int): Int => Int = {
    (y: Int) => {
      x + y
    } //返回一个Int=>Int类型函数
  }

  val intToInt2: Int => Int = method2(1)
  val i2: Int = intToInt2(2) //调用返回的函数
  println(i2)

  //柯里化的方法
  def method(x: Int)(y: Int): Int = {
    x + y
  }

  //调用方式一
  val i4: Int = method(1)(2)
  println(i4)
  //调用方式二
  val intToInt: Int => Int = method(1) //返回一个
  val i3: Int = intToInt(2)
  println(i3)


  println("---------------柯里化二-------------------")


  def fun10(a: Int)(b: Int)(c: String): Unit = {
    println(s"$a\t$b\t$c")
  }

  fun10(3)(8)("sdfsdf")

  def fun11(a: Int*)(b: String*): Unit = {
    a.foreach(println)
    b.foreach(println)
  }

  fun11(1, 2, 3)("sdfs", "sss")
}
