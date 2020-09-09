package com.zx.sql.demo1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 自定义UDF函数
 */
object SQL_Demo_02 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val dataFrame: DataFrame = spark.read.json("spark/src/main/data/jsondata/person.json")

    spark.udf.register("addSayHi",(name:String)=>{"nihao:"+name})

    dataFrame.createOrReplaceTempView("person")


    spark.sql("select addSayHi(name),age from person").show()

    spark.stop()

  }
}
