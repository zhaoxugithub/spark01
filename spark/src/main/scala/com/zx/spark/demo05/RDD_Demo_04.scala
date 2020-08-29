package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_04 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val originRDD: RDD[Int] = sc.parallelize(1 to 100)

    //抽取数据样本，抽取10%,不放回
    println("------------------------------")
    originRDD.sample(false, 0.1).foreach(println)
    println("------------------------------")
    originRDD.sample(true, 0.1).foreach(println)
    println("------------------------------")
    originRDD.sample(true, 0.1).foreach(println)

    println("---------------------分割线----------------------------")
    val data: RDD[Int] = sc.parallelize(1 to 10, 3)
    data.foreach(println)

    println(s"data:${data.getNumPartitions}")

    val data1: RDD[(Int, Int)] = data.mapPartitionsWithIndex(
      (pindex, iter) => {
        iter.map(e => (pindex, e))
      })
    data1.foreach(println)

    println("=====================================")
    //这种分区方式肯定会经历shuff
    //val repartition: RDD[(Int, Int)] = data1.repartition(6)
    /**
     * coalesce 中的两个参数，第一个参数表示重新分几个区，第二个参数表示是否shuff分区
     */
    //    val repartition: RDD[(Int, Int)] = data1.coalesce(4, true)

    /**
     * 如果coalesce 的第二个参数设置为true，不管分区数比之前的大还是小都会经历shuff
     */
    //    val repartition: RDD[(Int, Int)] = data1.coalesce(2, true)

    /**
     * 这个不经历shuff，不拆分分区,会整体合并分区
     */
    //    val repartition: RDD[(Int, Int)] = data1.coalesce(2, false)

    /**
     * 这种情况分区的结果和之前没有分区一样
     */
    val repartition: RDD[(Int, Int)] = data1.coalesce(4, false)

    val data2: RDD[(Int, (Int, Int))] = repartition.mapPartitionsWithIndex(
      (pindex, iter) => {
        iter.map(e => (pindex, e))
      })
    data2.foreach(println)
    println("---------------------------------------")
    //关闭context连接
    sc.stop()
  }
}
