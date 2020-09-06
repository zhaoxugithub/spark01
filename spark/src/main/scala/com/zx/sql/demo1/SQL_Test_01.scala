package com.zx.sql.demo1

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SQL_Test_01 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    /**
     * scala> spark.read.
     * csv   format   jdbc   json   load   option   options   orc   parquet   schema   table   text   textFile
     */

    //    import spark.implicits._
    val df: DataFrame = spark.read.json("spark/src/main/data/jsondata/person.json")

    df.show()


    //创建一张临时表
    df.createOrReplaceTempView("people")

    val sqlDf: DataFrame = spark.sql("select * from people where name ='zx'")
    sqlDf.show()


    //处理需求


  }
}
