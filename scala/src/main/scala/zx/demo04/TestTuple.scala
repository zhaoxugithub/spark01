package zx.demo04

object TestTuple {
  /**
   * 40
   * bobo
   * true
   * 40
   * 40
   * bobo
   * true
   * a=1
   * b=2
   * c=3
   */
  def main(args: Array[String]): Unit = {

    //（1）声明元组的方式：(元素1，元素2，元素3)
    val tuple: (Int, String, Boolean) = (40, "bobo", true)

    //（2）访问元组
    //（2.1）通过元素的顺序进行访问，调用方式：_顺序号
    println(tuple._1)
    println(tuple._2)
    println(tuple._3)

    //（2.2）通过索引访问数据
    println(tuple.productElement(0))

    //（2.3）通过迭代器访问数据
    for (elem <- tuple.productIterator) {
      println(elem)
    }

    //（3）Map中的键值对其实就是元组,只不过元组的元素个数为2，称之为对偶
    val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
    val map1 = Map(("a", 1), ("b", 2), ("c", 3))

    map.foreach(tuple => {
      println(tuple._1 + "=" + tuple._2)
    })
  }

}
