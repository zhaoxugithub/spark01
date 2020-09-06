package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_01 {
  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    //去重方法一
    //    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).map((_, 1)).reduceByKey(_ + _).map(_._1).foreach(println)
    //    //去重方法二
    //    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).distinct().foreach(println)
    //关闭context连接
    val value: RDD[(String, Int)] = sc.parallelize(List(("a", 1), ("a", 2), ("b", 1), ("b", 3), ("c", 4)), 2)
    value.mapPartitionsWithIndex((index, iter) => {
      iter.map(index + "----" + _)
    }).foreach(println)

    println("--------------------")
    val value1: RDD[(String, Int)] = value.aggregateByKey(0)(((x, y) => if (x > y) x else y), (_ + _))

    value1.mapPartitionsWithIndex((index, iter) => {
      iter.map(index + "----" + _)
    }).foreach(println)
    //    val value1: RDD[(String, Int)] = value.aggregateByKey(0)(_ + _, _ + _)

    println("---求平均方法一--------")
    val value2: RDD[(String, (Int, Int))] = value.combineByKey((x: Int) => (x, 1), ((x: (Int, Int), y: Int) => (x._1 + y, x._2 + 1)), (x: (Int, Int), y: (Int, Int)) => (x._1 + y._1, x._2 + y._2))
    value2.map((x) => (x._1, (x._2._1 / x._2._2))).foreach(println)
    println("---求平均方法二--------")
    value.groupByKey().map((x) => (x._1, x._2.sum / x._2.size)).foreach(println)
    println("---求平均方法三--------")
    value.map(x => (x._1, (x._2, 1))).reduceByKey((x, y) => ((x._1 + y._1), (x._2 + y._2))).map(x => (x._1, x._2._1 / x._2._2)).foreach(println)



    //groupByKey 和 mapValues 连用效果比较好

  }
}
