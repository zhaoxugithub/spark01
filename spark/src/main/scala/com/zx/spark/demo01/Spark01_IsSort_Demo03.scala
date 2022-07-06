package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_IsSort_Demo03 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val context = new SparkContext(conf)
    test04(context)
    while (true) {}
    context.stop()
  }

  //创建两个RDD，使用map算子，看下执行顺序怎么样
  //结论是无序的，如果改成一个分区就会有序，map算子是一个一个执行的
  def test(context: SparkContext): Unit = {
    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))
    println(value.partitions.length)
    val value1: RDD[Int] = value.map(x => {
      println("------" + x)
      x
    })
    value1.map(x => {
      println("=========" + x)
      x
    }).collect().foreach(println)
  }

  def test2(context: SparkContext): Unit = {
    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 1)
    val value1: RDD[Int] = value.map(x => {
      println("------" + x)
      x
    })
    value1.map(x => {
      println("=========" + x)
      x
    }).collect()
  }


  def test3(context: SparkContext): Unit = {
    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))

    value.mapPartitions(iter => {
      println("=======")
      iter.map(_ * 2)
    })
  }.collect().foreach(println)


  def test04(context: SparkContext): Unit = {

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 2)
    val value1: RDD[(Int, Int)] = value.mapPartitionsWithIndex((index, iterator) => {
      iterator.map(item => (index, item))
    })
    value1.collect().foreach(println)
  }


}
