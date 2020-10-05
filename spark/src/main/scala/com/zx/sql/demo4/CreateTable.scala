package com.zx.sql.demo4

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object CreateTable {
  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()


    val createVideoTable: String = "create table gulivideo_ori\n" +
      "(\n  " +
      "  videoId   string,\n  " +
      "  uploader  string,\n    age   " +
      "    int,\n   " +
      " category  array<string>,\n   " +
      " length    int,\n  " +
      "  views     int,\n  " +
      "  rate      float,\n  " +
      "  ratings   int,\n   " +
      " comments  int,\n  " +
      "  relatedId array<string>\n" +
      ")\n  " +
      "  row format delimited\n   " +
      "     fields terminated by \"\\t\"\n  " +
      "      collection items terminated by \"&\"\n " +
      "  stored as textfile"

    spark.sql(createVideoTable)

    val createUserTable :String ="create table gulivideo_user_ori\n" +
      "(\n   " +
      " uploader string,\n  " +
      "  videos   int,\n   " +
      " friends  int\n" +
      ")\n   " +
      " row format delimited\n     " +
      "   fields terminated by \"\\t\"\n  " +
      "  stored as textfile"
    spark.sql(createUserTable)

    spark.close()

  }
}
