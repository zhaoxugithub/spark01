package com.zx.spark.demo02

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkConf, SparkContext}

object Spark02_Partition_men {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //创建一个集合
    val list: List[Int] = List(1, 2, 3, 4)
    //根据集合创建RDD,默认是4个分区。每个分区中的数据分布
    //val listRdd: RDD[Int] = sc.makeRDD(list)
    val listRdd: RDD[Int] = sc.makeRDD(list, 2)
    //获取分区
    val partitions: Array[Partition] = listRdd.partitions
    println(partitions.size)
    //输出到
    listRdd.saveAsTextFile("/Users/serendipity/IdeaProjects/spark_01/spark/src/main/data/wordcount/output")
    //关闭context连接
    sc.stop()
  }
}
