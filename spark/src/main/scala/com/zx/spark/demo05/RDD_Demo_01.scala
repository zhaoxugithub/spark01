package com.zx.spark.demo05

import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_01 {
  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //去重方法一
    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).map((_, 1)).reduceByKey(_ + _).map(_._1).foreach(println)
    //去重方法二
    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).distinct().foreach(println)
    //关闭context连接
    sc.stop()


  }
}
