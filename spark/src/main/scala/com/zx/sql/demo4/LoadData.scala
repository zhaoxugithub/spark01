package com.zx.sql.demo4

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object LoadData {
  def main(args: Array[String]): Unit = {


    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()

    val loadVideoData :String ="load data local inpath \"spark/src/main/data/video/videos\" into table gulivideo_ori"
    spark.sql(loadVideoData)


    val loadUserData :String ="load data local inpath \"spark/src/main/data/video/user.txt\" into table gulivideo_user_ori"
    spark.sql(loadUserData)
  }
}
