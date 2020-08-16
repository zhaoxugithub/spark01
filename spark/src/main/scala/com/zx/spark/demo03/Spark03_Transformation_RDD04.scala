package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * groupBy(func)
 * 作用:
 * 按照func的返回值进行分组.
 * func返回值作为 key, 对应的值放入一个迭代器中. 返回的 RDD: RDD[(K, Iterable[T])
 * 每组内元素的顺序不能保证, 并且甚至每次调用得到的顺序也有可能不同.
 */
object Spark03_Transformation_RDD04 {

  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(Array(1, 3, 4, 20, 4, 5, 8))
    val value1: RDD[(String, Iterable[Int])] = value.groupBy(x => if (x % 2 == 1) "odd" else "even")
    value1.collect().foreach(println)
    //关闭context连接
    sc.stop()


  }
}
