package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Spark01_WordCount {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\soft\\hadoop")
    //1.准备Spark环境的位置
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    //2.建立和spark的连接
    val sc = new SparkContext(sparkConf)
    //val result = sc.textFile("D:\\尚硅谷大数据\\spark笔记\\3.代码\\spark-190513\\input").flatMap(str => str.split(" ")).map(word => (word, 1)).reduceByKey((x, y) => x + y).collect()
    //result.foreach(println)¬¬
    /*
    def textFile(path: String,minPartitions: Int = defaultMinPartitions): RDD[String] = withScope
    数据源是文件的情况下，真正的分区数取决于 Math.max(path中文件的block块数,minPartitions)
    textFile返回的实际上是一个HadoopRDD
     */
    val result2 = sc.textFile("D:\\document\\idea\\spark_01\\spark\\src\\main\\data\\wordcount\\input\\word.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    result2.foreach(println)
//    println("-----------------------------------")
//    val value: RDD[(Int, Int)] = result2.map((x) => (x._2, 1)).reduceByKey(_ + _)
//    value.foreach(println)
    Thread.sleep(Long.MaxValue)
    //释放连接
    sc.stop()
  }
}
