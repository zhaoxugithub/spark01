package com.zx.spark.demo08

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

//RDD血缘关系
object demo01 {

  def main(args: Array[String]): Unit = {
    rdd_1()
    rdd_2()
  }

  def rdd_2(): Unit = {
    /*
    org.apache.spark.OneToOneDependency@5287ba5f
    org.apache.spark.OneToOneDependency@6806468e
    org.apache.spark.OneToOneDependency@24a0c58b
    org.apache.spark.ShuffleDependency@652e345
     */
    System.setProperty("hadoop.home.dir", "D:\\soft\\hadoop")
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    val fileRDD: RDD[String] = sc.textFile("D:\\document\\idea\\spark_01\\spark\\src\\main\\data\\wordcount\\input\\word.txt", 3)
    fileRDD.dependencies.foreach(println)
    println("	")

    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    wordRDD.dependencies.foreach(println)
    println("	")

    val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
    mapRDD.dependencies.foreach(println)
    println("	")

    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    resultRDD.dependencies.foreach(println)
    resultRDD.collect()
    sc.stop()
  }


  //RDD血缘关系
  def rdd_1(): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\soft\\hadoop")
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //读取本地文件创建RDD
    val fileRDD: RDD[String] = sc.textFile("D:\\document\\idea\\spark_01\\spark\\src\\main\\data\\wordcount\\input\\word.txt", 3)

    println(fileRDD.toDebugString)
    println("	")

    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    println(wordRDD.toDebugString)
    println("	")

    val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
    println(mapRDD.toDebugString)
    println("	")

    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    println(resultRDD.toDebugString)

    resultRDD.collect()

    sc.stop()
  }
}
