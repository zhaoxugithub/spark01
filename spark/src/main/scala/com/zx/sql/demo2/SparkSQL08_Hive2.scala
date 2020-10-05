package com.zx.sql.demo2

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQL08_Hive2 {


  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()




  }
}
