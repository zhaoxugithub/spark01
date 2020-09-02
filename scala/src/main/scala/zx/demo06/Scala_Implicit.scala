package zx.demo06

import java.util

/**
 * 隐式转换
 */
object Scala_Implicit {

  def main(args: Array[String]): Unit = {

    val arrayList = new util.ArrayList[Int]()
    arrayList.add(1)
    arrayList.add(2)
    arrayList.add(3)

    foreach(arrayList, println)

    def foreach[T](list: util.ArrayList[T], f: (T) => Unit): Unit = {
      val iter: util.Iterator[T] = list.iterator()
      while (iter.hasNext) {
        f(iter.next())
      }
    }

    println("-----------------------------------------------------------")

    class XC[T](list: util.ArrayList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val iter: util.Iterator[T] = list.iterator()
        while (iter.hasNext) {
          f(iter.next())
        }
      }
    }

    val value: XC[Int] = new XC[Int](arrayList)
    value.foreach(println)

    println("----------------------------------------------------")

    //隐式转换
    implicit class XC1[T](list: util.ArrayList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val iter: util.Iterator[T] = list.iterator()
        while (iter.hasNext) {
          f(iter.next())
        }
      }
    }
    arrayList.forEach(println)


    println("-------------------------------------------------")

    val list: util.List[String] with Object = new util.ArrayList[String]()
    list.add("java")
    list.add("php")
    list.add("scala")
    val linkedList: util.LinkedList[String] = new util.LinkedList[String]()
    linkedList.add("java")
    linkedList.add("php")
    linkedList.add("scala")

    def bbb[T](bb: util.LinkedList[T]) = {
      new XX[T](bb.iterator())
    }

    def aaa[T](aa: util.ArrayList[T]) = {
      new XX[T](aa.iterator())
    }

    class XX[T](list: util.Iterator[T]) {
      def foreach(f: (T) => Unit): Unit = {
        while (list.hasNext) f(list.next())
      }
    }

    list.forEach(println)
    linkedList.forEach(println)

    println("-----------------------------------")

    val aaaa: String = "where"
    implicit val x1: Int = 100

    def ooxx1(name: String)(age: Int): Unit = {
      println(name + "----" + age)
    }

    ooxx1("zhaoxu")(10) //zhaoxu----10


    def ooxx2(name: String)(implicit age: Int): Unit = {
      println(name + "----" + age)
    }

    ooxx2("zhaoxu") //zhaoxu----100

  }

}
