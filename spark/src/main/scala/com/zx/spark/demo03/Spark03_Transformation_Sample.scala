package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Transformation_Sample {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //处理需求

    // TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    //withReplacement 如果值是false抽样不放回,true表示抽样放回

    /**
     * withReplacement:
     * 如果值是false抽样不放回   fraction 的值表示每一个元素出现的概率[0,1]
     * true表示抽样放回         fraction 的值表示期望每一个元素出现的次数>0
     */
    numRDD.sample(false, 0.5, System.currentTimeMillis()).collect().foreach(println)
    println("----------------------------")
    numRDD.sample(true, 3, System.currentTimeMillis()).collect().foreach(println)
    //关闭context连接
    sc.stop()


  }


}
