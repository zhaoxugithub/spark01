package com.zx.sql.demo1

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SQL_Demo_06 {

  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")

    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    import spark.implicits._

    //方式1：通用的load方法读取
    spark.read.format("jdbc")
      .option("url", "jdbc:mysql://zhaoxu.aliyun.com:3306/sqoop")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "root")
      .option("dbtable", "user")
      .load().show


    //方式2:通用的load方法读取 参数另一种形式
    spark.read.format("jdbc")
      .options(Map("url"->"jdbc:mysql://zhaoxu.aliyun.com:3306/sqoop?user=root&password=root",
        "dbtable"->"user","driver"->"com.mysql.jdbc.Driver")).load().show

    //方式3:使用jdbc方法读取
    val props: Properties = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "root")
    val df: DataFrame = spark.read.jdbc("jdbc:mysql://zhaoxu.aliyun.com:3306/sqoop", "user", props)
    df.show

    //释放资源
    spark.stop()

  }
}
