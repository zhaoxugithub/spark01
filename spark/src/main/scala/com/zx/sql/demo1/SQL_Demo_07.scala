package com.zx.sql.demo1

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}

object SQL_Demo_07 {

  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")

    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    val rdd: RDD[User2] = spark.sparkContext.makeRDD(List(User2("lisi", "mail",20), User2("zs", "femail",30)))
    val ds: Dataset[User2] = rdd.toDS
    //方式1：通用的方式  format指定写出类型
    ds.write
      .format("jdbc")
      .option("url", "jdbc:mysql://zhaoxu.aliyun.com:3306/sqoop")
      .option("user", "root")
      .option("password", "root")
      .option("dbtable", "user")
      .mode(SaveMode.Append)
      .save()

    //方式2：通过jdbc方法
    val props: Properties = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "root")
    ds.write.mode(SaveMode.Append).jdbc("jdbc:mysql://zhaoxu.aliyun.com:3306/sqoop", "user", props)

    //释放资源
    spark.stop()


  }

}


case class User2(name: String, sex: String, age: Long)
