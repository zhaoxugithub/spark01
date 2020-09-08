package com.zx.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test {

  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val dataRDD: RDD[String] = sc.parallelize(List("hello word", "hello word"))
    val mapRDD: RDD[(String, Int)] = dataRDD.flatMap(word => word.split(" ")).map(x => (x, 1))
    val value1: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()
    val value: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    value1.foreach(println)
    value.foreach(println)
    Thread.sleep(Long.MaxValue)

    //关闭context连接
    sc.stop()


  }

}
