package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * glom()
 * 作用: 将每一个分区的元素合并成一个数组，形成新的 RDD 类型是RDD[Array[T]]
 */
object Spark03_Transformation_RDD03 {

  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    var rdd1 = sc.parallelize(Array(10, 20, 30, 40, 50, 60), 2)

    //def glom(): RDD[Array[T]] = withScope
    val array: Array[Array[Int]] = rdd1.glom().collect()
    array.foreach(x => {
      val iterator: Iterator[Int] = x.iterator
      while (iterator.hasNext) {
        println(x.hashCode()+":"+iterator.next())
      }
    })
    //关闭context连接
    sc.stop()
  }
}
