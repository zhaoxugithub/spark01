package com.zx.spark.demo01

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


object Spark01_Partitions_Demo02 {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("partitions").setMaster("local[*]")
    val sparkContext = new SparkContext(conf)
    val value1: RDD[String] = sparkContext.textFile("data/num.txt",2)
    value1.saveAsTextFile("data/partition")
    sparkContext.stop();
  }



}
