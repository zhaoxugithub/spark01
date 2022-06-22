package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Transformation_RDD02 {


  def main(args: Array[String]): Unit = {
    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark02_RDD2").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5))

    // 将集合中的每一个数据进行结构的转换，并形成平方和立方的组合
    // 转换算子 - flatMap
    //  TraversableOnce："集合的模板特征，该特征只能被遍历一次或一次或多次。"
    // def flatMap[U: ClassTag](f: T => TraversableOnce[U]): RDD[U] = withScope
    val flatMapRDD: RDD[Int] = numRDD.flatMap(num => {
      List(num * num, num * num * num)
    })
    //result :1,1,4,8,9,27,16,64,25,125
    flatMapRDD.collect().foreach(println)
    println(flatMapRDD.collect.mkString(","))

    // TODO 9. 释放连接
    sc.stop()
  }

}
