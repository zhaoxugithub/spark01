package com.zx.spark.demo01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
/**
 * 通过读取内存集合的中数据创建RDD
 */
object Spark01_CreateRDD_men {

  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //创建一个集合
    val list: List[Int] = List(1, 2, 3, 4, 5)
    //根据集合创建RDD
    val rdd: RDD[Int] = sc.parallelize(list)
    val listRDD: RDD[Int] = sc.makeRDD(list)
    rdd.collect().foreach(println)
    //关闭context连接
    sc.stop()
  }
}
