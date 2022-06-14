package main.scala.zx.demo07.chapter06.collection

import scala.collection.mutable.ListBuffer

/**
 *
 * => 一般表示的是 函数后面的处理逻辑
 * :  表示的返回类型，数据类型
 *
 *
 *
 */
object Collection01 {

  def main(args: Array[String]): Unit = {

  }


  //可变的list
  def fun01(): Unit = {

    val list = new ListBuffer[Int]
    list.addOne(2)
    list.+=(3)

    //todo: 学习  ++ += ++: :++

    list.foreach(println)
  }

  //不可变的map
  def fun02(): Unit = {
    val value: Map[String, Int] = Map(("a", 33), "b" -> 44, ("c", 55), ("d", 66))
    val keys: Iterable[String] = value.keys


    println(value.get("a")) //Some(33)

    //因为map中get方法可能会返回空指针，所以需要对返回的数据做判断
    println(value.getOrElse("a", "hello World"))
    println(value.getOrElse("w", "hello world"))


    //U 表示的无返回值,非U表示有返回值
    //def foreach[U](f: A => U): Unit = {
    keys.foreach(x => {
      println(s"key:$x value:${value(x)}")
    })


    //数据转换
    val value1 = List(1, 2, 3, 4, 6)
    value1.foreach(println)


    // B 表示f函数体内的返回值，List<B>表示的是map方法的返回值
    //map[B](f: A => B): List[B] = {
    val value2: List[Int] = value1.map(x => x + 10)

    val value3 = List(
      "Hello world",
      "Hello msb",
      "good idea"
    )
    //(f: A => IterableOnce[B]): IterableOnce[B] = it match {
    // IterableOnce 返回的是可以迭代的集合
    val value4: List[String] = value3.flatMap(x => x.split(" "))
    value4.foreach(println)

    val value5: List[(String, Int)] = value4.map(x => (x, 1))
    value5.foreach(println)

    //上面的方法进行数据转换会消耗内存，因为每进行一次变换都要创建一个内存存放变换过后的数据

    //所以使用迭代器模式，迭代器类似一个个指针，只是对单个元素进行变化
    val iterator: Iterator[String] = value3.iterator
    val value6: Iterator[String] = iterator.flatMap(x => x.split(" "))
    val value7: Iterator[(String, Int)] = value6.map(x => (x, 1))
    //上述的都没有具体执行，直到执行foreach 才会执行转换方法
    value7.foreach(println)

  }
  fun02()

}
