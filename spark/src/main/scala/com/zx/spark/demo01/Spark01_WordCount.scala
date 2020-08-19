package com.zx.spark.demo01

import org.apache.spark.{SparkConf, SparkContext}


object Spark01_WordCount {

  def main(args: Array[String]): Unit = {

    //1.准备Spark环境的位置
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")

    //2.建立和spark的连接
    val sc = new SparkContext(sparkConf)


    //val result = sc.textFile("D:\\尚硅谷大数据\\spark笔记\\3.代码\\spark-190513\\input").flatMap(str => str.split(" ")).map(word => (word, 1)).reduceByKey((x, y) => x + y).collect()
    //result.foreach(println)

    val result2 = sc.textFile("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/input/word.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect()
    result2.foreach(println)

    //释放连接
    sc.stop()
  }
}
