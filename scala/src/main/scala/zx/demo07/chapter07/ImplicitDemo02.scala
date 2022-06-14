package main.scala.zx.demo07.chapter07

import java.util

object ImplicitDemo02 {

  def main(args: Array[String]): Unit = {
    test03()
  }

  def test01(): Unit = {
    val list = new util.LinkedList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)

    implicit class KKK[T](list: util.LinkedList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val value: util.Iterator[T] = list.iterator()
        while (value.hasNext) {
          f(value.next())
        }
      }
    }

    list.foreach(println)
  }


  def test02(): Unit = {
    val list = new util.ArrayList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)

    implicit class KKK[T](list: util.ArrayList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val value: util.Iterator[T] = list.iterator()
        while (value.hasNext) {
          f(value.next())
        }
      }
    }

    list.foreach(println)
  }

  def test03(): Unit = {
    val list = new util.LinkedList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)
    list.add(4)
    implicit class KKK[T](list: util.Collection[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val value: util.Iterator[T] = list.iterator()
        while (value.hasNext) {
          f(value.next())
        }
      }
    }
    list.foreach(println)
  }


}
