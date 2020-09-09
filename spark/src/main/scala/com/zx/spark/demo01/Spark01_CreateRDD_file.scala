package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_CreateRDD_file {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    //读取本地文件创建RDD
    val fileRDD: RDD[String] = sc.textFile("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/input/word.txt", 3)

    val strings: Array[String] = fileRDD.take(0)
    println(strings.toString)

    /*
    (file:/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/input/word.txt,zhaoxu zhaoxu
        li hahah
        zhangsan lisi
        wuwang spark
        hadoop spark
        java scala
        c shell
        java python
        scala python)
     */
    //    val wholeRDD: RDD[(String, String)] = sc.wholeTextFiles("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/input/word.txt")
    //    println(wholeRDD.take(0).toString)

    fileRDD.collect().foreach(println)
    //    wholeRDD.collect().foreach(println)

    //读取hdfsRDD
    //    val hdfsRDD: RDD[String] = sc.textFile("hdfs://CentOS201:9000/input")
    //    hdfsRDD.collect().foreach(println)

    //关闭context连接
    sc.stop()
  }
}
