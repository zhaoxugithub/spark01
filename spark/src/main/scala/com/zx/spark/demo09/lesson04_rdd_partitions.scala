package com.zx.spark.demo09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object lesson04_rdd_partitions {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("partitions")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val data: RDD[Int] = sc.parallelize(1 to 10, 2)

    //外关联 sql查询
    val res01: RDD[String] = data.map(

      (value: Int) => {
        println("------conn--mysql----")
        println(s"-----select $value-----")
        println("-----close--mysql------")
        value + "selected"
      }
    )
    res01.foreach(println)

    println("--------------------")


    val res02: RDD[String] = data.mapPartitionsWithIndex(

      (pindex, piter) => {
        val lb = new ListBuffer[String] //致命的！！！！  根据之前源码发现  spark就是一个pipeline，迭代器嵌套的模式
        //数据不会再内存积压
        println(s"--$pindex----conn--mysql----")
        while (piter.hasNext) {
          val value: Int = piter.next()
          println(s"---$pindex--select $value-----")
          lb.+=(value + "selected")
        }
        println("-----close--mysql------")
        lb.iterator
      }
    )
    res02.foreach(println)


    val res03: RDD[String] = data.mapPartitionsWithIndex((pIndex, piter) => {
      println(s"--------$pIndex---mysql connect--------")
      new Iterator[String] {
        override def hasNext: Boolean = if (piter.hasNext == false) {
          println(s"--------$pIndex---close mysql------------")
          false
        } else {
          true
        }

        override def next(): String = {
          val i: Int = piter.next()
          println(s"-----$pIndex--select $i---------")
          i + "selected"
        }
      }
    })

    res03.foreach(println)

  }
}
