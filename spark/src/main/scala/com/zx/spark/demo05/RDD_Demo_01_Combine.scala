package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_01_Combine {
  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    val list1: RDD[Int] = sc.parallelize(List(1, 3, 5, 7, 9, 11), 1)
    val list2: RDD[Int] = sc.parallelize(List(2, 4, 6, 8, 10), 1)
    println(list1.partitions.length)
    println(list2.partitions.length)
    println("--------------------------")


    val value: RDD[Int] = list1.union(list2)


    //两个RDD分区相加
    println(value.partitions.length)


    value.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => (index, x))
    }).foreach(println)

    sc.stop()
  }
}


