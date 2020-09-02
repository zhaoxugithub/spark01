package zx.demo06

import java.util

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Scala_Collection {


  def main(args: Array[String]): Unit = {

    val listBuffer = new ListBuffer[Int]()
    listBuffer.+=(1)

    val list = List(1, 2, 3, 4, 5)
    val list1: List[Int] = list.map(x => x + 10)
    println(list)
    println(list1)
    println(list1.mkString(","))


    println("--------------------------Array-------------------------------------")


    val linkedList = new util.LinkedList[String]()
    linkedList.add("hello")
    println(linkedList)

    /**
     * Array 是不可变的数组
     */
    //初始化五个元素的数组
    val array01: Array[Int] = Array[Int](1, 2, 3, 4, 5)

    //定义容量为5的数组
    val array02: Array[Int] = new Array[Int](5)
    array02.update(0, 20)
    println(array02.toList) //可以打印出来List(20, 0, 0, 0, 0)

    //定义三个元素的数组
    val array03: Array[Any] = Array[Any](1, 2, "aaa")

    val arrayBuffer = new ArrayBuffer[Int](4)
    arrayBuffer.+=(1)
    println(arrayBuffer)

    println("--------------------------List-------------------------------------")

    //val ints: List[Int] = new List[Int](3)  报错
    //不可变
    val list2: List[Int] = List(1, 2, 3, 4, 5)

    val listBuffer1: ListBuffer[Int] = ListBuffer(1, 2, 3)

    val listBuffer2: ListBuffer[Int] = new ListBuffer[Int]()
    listBuffer2.+=(1)
    listBuffer2.+=(2)
    println(listBuffer2)


    println("--------------------------Set-------------------------------------")

    //默认不可变
    val set01: Set[Int] = Set(1, 2, 3, 4)

    println(set01)

    //可变
    val mutableSet: scala.collection.mutable.Set[Int] = scala.collection.mutable.Set(4, 5, 4, 7)
    mutableSet.+=(0)
    println(mutableSet)

    //    val ints: scala.collection.mutable.Set[Int] = new scala.collection.mutable.Set[Int]()  默认几口不可以实例化，会报错


    println("--------------------------tuple-------------------------------------")
    val t2: (Int, String) = (11, "11_value")
    println(t2._1 + "--" + t2._2)

    val t3: (Int, Int, String) = (11, 22, "222")
    println(t3)
    val t4: (Int, Int, Int, Int) = (1, 2, 3, 4)

    val t5: ((Int, Int) => Int, Int, Int, Int, Int) = ((x: Int, y: Int) => {
      x + y
    }, 2, 3, 4, 5)

    val productIterator: Iterator[Any] = t5.productIterator
    while (productIterator.hasNext) {
      val value: Any = productIterator.next()
      println(value)
    }

    println("--------------------------Map-------------------------------------")
    //不可变
    val mapDD: Map[String, Int] = Map(("a", 1), ("b", 2), "c" -> 3, ("d", 4))
    println(mapDD)

    val keys: Iterable[String] = mapDD.keys
    keys.foreach(println)

    println(mapDD.get("a").get)
    //    println(mapDD.get("e").get)  这句话报异常：Exception in thread "main" java.util.NoSuchElementException: None.get

    println(mapDD.get("a").getOrElse("a is null"))
    println(mapDD.get("e").getOrElse("e is null"))

    for (ele <- keys) {
      println(s"${ele}----${mapDD.get(ele).get}")
    }

    val stringToInt: mutable.Map[String, Int] = scala.collection.mutable.Map(("a", 1), ("b", 2))
    stringToInt.put("c", 3)
    println(stringToInt)
  }

  println("---------------------------拓展---------------------------")


  val listStr = List(
    "hello world",
    "hello msb",
    "good idea"
  )
  //        val listStr = Array(
  //      "hello world",
  //      "hello msb",
  //      "good idea"
  //    )
  //        val listStr = Set(
  //      "hello world",
  //      "hello msb",
  //      "good idea"
  //    )


  val flatMap = listStr.flatMap((x: String) => x.split(" ")) //split 返回的是一个数组
  flatMap.foreach(println)
  val mapList = flatMap.map((_, 1))
  mapList.foreach(println)

  //以上代码有什么问题吗？  内存扩大了N倍，每一步计算内存都留有对象数据；有没有什么现成的技术解决数据计算中间状态占用内存这一问题~？
  //iterator！！！！！

  println("--------------艺术-再-升华------------")


  //基于迭代器的原码分析

  val iter: Iterator[String] = listStr.iterator //什么是迭代器，为什么会有迭代器模式？  迭代器里不存数据！

  val iterFlatMap = iter.flatMap((x: String) => x.split(" "))
  //    iterFlatMap.foreach(println)

  private val str: String = iterFlatMap.next()
  println(str)//hello

  val iterMapList = iterFlatMap.map((_, 1))

  while (iterMapList.hasNext) {
    val tuple: (String, Int) = iterMapList.next()
    println(tuple)
  }


  //    iterMapList.foreach(println)

  //1.listStr真正的数据集，有数据的
  //2.iter.flatMap  没有发生计算，返回了一个新的迭代器


}
