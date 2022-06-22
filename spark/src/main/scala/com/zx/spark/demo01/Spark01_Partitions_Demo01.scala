package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object Spark01_Partitions_Demo01 {

  def main(args: Array[String]): Unit = {


    val conf: SparkConf = new SparkConf().setAppName("partitions").setMaster("local[*]")
    val sparkContext = new SparkContext(conf)
    val ints = new ListBuffer[Int]
    for (i <- 1 to 20) {
      ints.+=(i)
    }

    val value: RDD[Int] = sparkContext.makeRDD(ints, 6)
    value.saveAsTextFile("data/partition")
    sparkContext.stop();
  }

}
