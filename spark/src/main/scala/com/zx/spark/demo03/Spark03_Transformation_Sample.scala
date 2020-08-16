package com.zx.spark.demo03

import org.apache.spark.{SparkConf, SparkContext}

class Spark03_Transformation_Sample {

  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    //处理需求


    //关闭context连接
    sc.stop()


  }


}