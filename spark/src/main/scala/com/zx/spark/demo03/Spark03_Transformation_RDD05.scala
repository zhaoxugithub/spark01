package com.zx.spark.demo03

import org.apache.spark.{SparkConf, SparkContext}

/**
 * filter(func)
 * 作用: 过滤. 返回一个新的 RDD 是由func的返回值为true的那些元素组成
 */
object Spark03_Transformation_RDD05 {

  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    val names = sc.parallelize(Array("xiaoli", "laoli", "laowang", "xiaocang", "xiaojing", "xiaokong"))

    names.filter(_.contains("xiao")).collect().foreach(println)

    //关闭context连接
    sc.stop()


  }

}
