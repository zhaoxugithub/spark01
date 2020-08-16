package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkConf, SparkContext}

object Spark01_CreateRDD_File_Test {

  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    val fileRDD: RDD[String] = sc.textFile("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/input/1.txt",3)

    val partitions: Array[Partition] = fileRDD.partitions
    println(partitions.length)
    fileRDD.saveAsTextFile("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/output")

    //关闭context连接
    sc.stop()


  }
}
