package main.scala.zx.demo07.chapter07

import java.util

object ImplicitDemo01 {

  def main(args: Array[String]): Unit = {

    val list = new util.LinkedList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)

    //如果我用的是java里面的类，如果使用scala里面的方法
    //比如list.foreach(println)
    //    foreach(list, println)

    //方法一：自定义一个函数
    // f:后面加的是函数签名：(T)=>Unit
    def foreach[T](list: util.LinkedList[T], f: (T) => Unit): Unit = {
      val value: util.Iterator[T] = list.iterator()
      while (value.hasNext) {
        f(value.next())
      }
    }

    //    implicit def sss[T](list: util.LinkedList[T]): XXX[T] = {
    //      new XXX[T](list)
    //    }
    //方法二调用
    //    val value = new XXX(list)
    //    value.foreach(println)
    //
    //    //使用隐式转换
    //    implicit def sss[T](list: util.LinkedList[T]): XXX[T] = {
    //      new XXX[T](list)
    //    }


    //    list.foreach(println)

    //方法三：直接定义一个隐式类
    implicit  class KKK[T](list: util.LinkedList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val value: util.Iterator[T] = list.iterator()
        while (value.hasNext) {
          f(value.next())
        }
      }
    }

    list.foreach(println)
  }

  //第二种方式：通过自定义一个类
  //class XXX[T](list: util.LinkedList[T]) {
  //  def foreach(f: (T) => Unit): Unit = {
  //    val value: util.Iterator[T] = list.iterator()
  //    while (value.hasNext) {
  //      f(value.next())
  //    }
  //  }

}
