package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object RDD_Demo_10 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    val value: RDD[String] = sc.parallelize(List("Hello","Hello","Hello", "History", "Hot", "Hey", "hospital", "ass"))
    val accumulator: MyAccumulator = new MyAccumulator()
    //注册累加器
    sc.register(accumulator, "wordCount")

    value.foreach(x => {
      accumulator.add(x)
    })

    println(accumulator.value)
    //关闭context连接
    sc.stop()
  }
}

//自定义累加器
class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]] {

  var map = mutable.Map[String, Long]()

  //判断是否是初始状态，如果集合的数据为空，即为初始化状态
  override def isZero: Boolean = map.isEmpty

  //复制累加器
  override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = new MyAccumulator()

  //重置累加器
  override def reset(): Unit = map.clear()

  //增加数据
  override def add(v: String): Unit = {
    if (v.startsWith("H")) {
      map(v) = map.getOrElse(v, 0L) + 1L
    }
  }

  //合并累加器
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
    var map1 = map
    var map2 = other.value

    map = map1.foldLeft(map2)(
      //nm表示map2,kv表示map1中的每一个元素
      (nm, kv) => {
        val key: String = kv._1
        val value: Long = kv._2
        nm(key) = nm.getOrElse(key, 0L) + value
        nm
      }
    )

  }

  //累加器的值，其实就是累加器的返回结果
  override def value: mutable.Map[String, Long] = map
}
