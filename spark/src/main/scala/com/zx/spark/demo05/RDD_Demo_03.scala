package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object RDD_Demo_03 {


  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("partitions").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //处理需求

    val data: RDD[Int] = sc.parallelize(1 to 10, 2)


    //外关联 sql查询
    val value: RDD[String] = data.map(
      (x) => {
        println("-------mysql--connected-------")
        println(s"------$x---selected ")
        println("-------mysql---closed---------")
        x + "selected"
      }
    )
    value.foreach(println)


    println("-------------------------------------")
      data.mapPartitionsWithIndex(
      (pindex, iter) => {
        var lb = new ListBuffer[String]
        println(s"-------$pindex--connected-------")
        while (iter.hasNext) {
          val i: Int = iter.next()
          lb.+=(i + "selected")
        }
        println("-------mysql---closed---------")
        lb.iterator
      }
    ).foreach(println)

    println("----------------------------------")
    data.mapPartitionsWithIndex(

      f = (pindex, iter) => {
        println(s"-------$pindex--connected-------")
        new Iterator[String] {

          override def hasNext: Boolean = if (iter.hasNext) {
            true
          } else {
            println("-------mysql---closed---------")
            false
          }

          override def next(): String = {
            val i: Int = iter.next()
            println(s"------$i---selected ")
            i + "selected"
          }
        }
      }
    ).foreach(println)


    //关闭context连接
    sc.stop()


  }

}
