package com.zx.sql.demo1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 加载数据和保存数据
 *
 *
 * scala> spark.read.format("…")[.option("…")].load("…")
 * 用法详解：
 * format("…")：指定加载的数据类型，包括"csv"、"jdbc"、"json"、"orc"、"parquet"和"textFile"
 * load("…")：在"csv"、"jdbc"、"json"、"orc"、"parquet"和"textFile"格式下需要传入加载数据的路径
 * option("…")：在"jdbc"格式下需要传入JDBC相应参数，url、user、password和dbtable
 */
object SQL_Demo_05 {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //    val dataFrame: DataFrame = spark.read.json("spark/src/main/data/jsondata/person.json")
    spark.read.format("json").load("spark/src/main/data/jsondata/person.json").show()

    spark.sql("select * from json.`spark/src/main/data/jsondata/person.json`").show()


    /**
     * scala> df.write.
     * csv  jdbc   json  orc   parquet textFile… …
     * spark.read.format("…")[.option("…")].load("…")
     */
    val df: DataFrame = spark.read.format("json").load("spark/src/main/data/jsondata/person.json")
    //df.write.json("spark/src/main/data/jsondata/output")


    /**
     * scala> df.write.format("…")[.option("…")].save("…")
     * 用法详解：
     * format("…")：指定保存的数据类型，包括"csv"、"jdbc"、"json"、"orc"、"parquet"和"textFile"。
     * save ("…")：在"csv"、"orc"、"parquet"和"textFile"格式下需要传入保存数据的路径。
     * option("…")：在"jdbc"格式下需要传入JDBC相应参数，url、user、password和dbtable
     */

    spark.read.format("jdbc")
      .option("url", "jdbc:mysql://1.15.149.196:33306/mall")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "rootroot")
      .option("dbtable", "mall_category")
      .load().show

    df.write.mode("append").json("spark/src/main/data/jsondata/output")


  }
}
