package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * UV是指通过互联网访问、浏览这个网页的自然人。访问您网站的一台电脑客户端为一个访客。00:00-24:00内相同的客户端只被计算一次。
 * PV是指访问量
 **/
//面向数据集操作：
//*，带函数的非聚合：  map，flatmap
//1，单元素：union，cartesion  没有函数计算
//2，kv元素：cogroup，join   没有函数计算
//3，排序
//4，聚合计算  ： reduceByKey  有函数   combinerByKey
object RDD_Demo_06 {
  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    //PV,UV
    //需求：根据数据计算各网站的PV,UV，同时，只显示top5
    //解题：要按PV值，或者UV值排序，取前5名

    //43.169.217.152	河北	2018-11-12	1542011088714	3292380437528494072	www.dangdang.com	Login
    val file: RDD[String] = sc.textFile("spark/src/main/data/pvuvdata/pvuvdata", 1)

    println("-----------PV------------")
    val pair: RDD[(String, Int)] = file.map(line => (line.split("\t")(5), 1))

    val redRDD: RDD[(String, Int)] = pair.reduceByKey(_ + _)

    val swapRDD: RDD[(Int, String)] = redRDD.map(_.swap)

    val sortRDD: RDD[(Int, String)] = swapRDD.sortByKey(false)

    val resultRDD: RDD[(String, Int)] = sortRDD.map(_.swap)

    resultRDD.foreach(println)


    println("--------------UV---------------")

    //首先对文件去重
    val pairRDD: RDD[(String, String)] = file.map(line => {

      val arr: Array[String] = line.split("\t")
      (arr(5), arr(0))
    })

    val disRDD: RDD[(String, String)] = pairRDD.distinct()

    val mapRDD: RDD[(String, Int)] = disRDD.map(pair => (pair._1, 1))

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    val resultRDD1: RDD[(String, Int)] = reduceRDD.sortBy(_._2, false)

    resultRDD1.foreach(println)

    while (true) {}
    //关闭context连接
    sc.stop()


  }
}
