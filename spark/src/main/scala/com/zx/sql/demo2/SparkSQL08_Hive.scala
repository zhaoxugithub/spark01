package com.zx.sql.demo2

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQL08_Hive {

  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()


        val createTable1: String = "CREATE TABLE `user_visit_action`(\n " +
          " `date` string,\n " +
          " `user_id` bigint,\n  " +
          "`session_id` string,\n  " +
          "`page_id` bigint,\n  " +
          "`action_time` string,\n  " +
          "`search_keyword` string,\n " +
          " `click_category_id` bigint,\n  " +
          "`click_product_id` bigint,\n " +
          " `order_category_ids` string,\n " +
          " `order_product_ids` string,\n  " +
          "`pay_category_ids` string,\n " +
          " `pay_product_ids` string,\n " +
          " `city_id` bigint)\n" +
          "row format delimited fields terminated by '\\t'"
        spark.sql(createTable1)
        spark.sql("load data local inpath 'spark-warehouse/user_visit_action.txt' into table user_visit_action")
        spark.sql("select * from  user_visit_action").show()


    //创建产品表
    //    var createTable2:String = "CREATE TABLE `product_info`(\n " +
    //      " `product_id` bigint,\n " +
    //      " `product_name` string,\n  " +
    //      "`extend_info` string)\n" +
    //      "row format delimited fields terminated by '\\t'"
    //    spark.sql(createTable2)

    //    spark.sql("load data local inpath 'spark-warehouse/product_info.txt' into table product_info")
    //
    //    spark.sql("select * from  product_info").show()


    //创建城市表
    //    var createTable3: String = "CREATE TABLE `city_info`(\n  `city_id` bigint,\n  `city_name` string,\n  `area` string)\nrow format delimited fields terminated by '\\t'"
    //
    //    spark.sql(createTable3)

    //    spark.sql("load data local inpath 'spark-warehouse/city_info.txt' into table city_info")
    spark.sql("select * from  city_info").show()

    spark.sql("show databases").show()
    spark.sql("show tables").show()
    //释放资源
    spark.stop()
  }
}
