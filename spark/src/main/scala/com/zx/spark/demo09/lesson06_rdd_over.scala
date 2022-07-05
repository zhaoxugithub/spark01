package com.zx.spark.demo09

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object lesson06_rdd_over {

  def main(args: Array[String]): Unit = {

    //综合应用算子
    //topN   分组取TopN  （二次排序）
    //2019-6-1	39
    //同月份中 温度最高的2天
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("topN")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val data: RDD[String] = sc.parallelize(List(
      "hello world",
      "hello spark",
      "hello world",
      "hello hadoop",
      "hello world",
      "hello msb",
      "hello world"
    ))
    val words: RDD[String] = data.flatMap(_.split(" "))
    val kv: RDD[(String, Int)] = words.map((_, 1))
    val res: RDD[(String, Int)] = kv.reduceByKey(_ + _)
    //      val res01: RDD[(String, Int)] = res.map(x=>(x._1,x._2*10))
    val res01: RDD[(String, Int)] = res.mapValues(x => x * 10)
    val res02: RDD[(String, Iterable[Int])] = res01.groupByKey()
    res02.foreach(println)

    val dataRDD: RDD[String] = sc.textFile("spark/src/main/data/pvuvdata/tqdata", 4)
    val sourceRDD: RDD[(String, String, String, String)] = dataRDD.map(e => e.split("\t")).map(arr => {
      val strings: Array[String] = arr(0).split("-")
      //年月日 温度
      (strings(0), strings(1), strings(2), arr(1))
    })

    val mapRDD: RDD[((String, String), (String, String))] = sourceRDD.map(e => ((e._1, e._2), (e._3, e._4)))


  }

}
