package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * distinct([numTasks]))
 * 作用:
 * 对 RDD 中元素执行去重操作. 参数表示任务的数量.默认值和分区数保持一致.
 */
object Spark03_Transformation_RDD07 {
  def main(args: Array[String]): Unit = {

    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1,1,2,2,3,3), 2)

    // （1，null）,(1, null),(2, null),(2, null),(3, null),(3,null)
    // 1, (null, null,null,null,null) => (1, null)
    println(numRDD.distinct(10).collect().mkString(","))

    // TODO 9. 释放连接
    sc.stop()


  }
}
