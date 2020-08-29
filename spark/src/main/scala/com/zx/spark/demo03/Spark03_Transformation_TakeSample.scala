package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Transformation_TakeSample {
  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //处理需求
    val list: List[String] = List("张三1", "李四2", "王五3", "张三4", "李四5", "王五6", "张三7", "李四8", "王五9", "张三10", "李四11", "王五12")

    val nameRDD: RDD[String] = sc.makeRDD(list)

    nameRDD.takeSample(false, 1).foreach(println)

    //关闭context连接
    sc.stop()


  }
}
