package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_08 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    sc.setLogLevel("ERROR")
    val data: RDD[String] = sc.textFile("spark/src/main/data/agent/input/agent.log")
    //1516609143867 6 7 64 16
    val value: RDD[(String, Int)] = data.map(x => {
      val arr: Array[String] = x.split(" ")
      ((arr(1) + "-" + arr(4)), 1)
    })
    val reduceRDD: RDD[(String, Int)] = value.reduceByKey(_ + _)

    val mapRDD: RDD[(String, (String, Int))] = reduceRDD.map(x => {
      val arr: Array[String] = x._1.split("-")
      (arr(0), (arr(1), x._2))
    })

    mapRDD.groupByKey().mapValues(x => x.toList.sortWith((x, y) => {
      x._2 > y._2
    }).take(3)).foreach(println)



    //关闭context连接
    sc.stop()


  }
}
