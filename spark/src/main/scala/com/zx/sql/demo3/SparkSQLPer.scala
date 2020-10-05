package com.zx.sql.demo3

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * 二、把星座和血型一样的人归类到一起。结果如下：
 * 射手座,A            大海|凤姐
 * 白羊座,A            孙悟空|猪八戒
 * 白羊座,B            宋宋|苍老师
 * *
 *
 * 行转列
 * CONCAT(string A/col, string B/col…)
 * CONCAT_WS(separator, str1, str2,...)
 * COLLECT_SET(col)
 *
 */
object SparkSQLPer {

  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()
    //创建表
    //    val createSQL: String = "create table person_info\n" +
    //      "(\n   " +
    //      " name          string,\n " +
    //      "   constellation string,\n  " +
    //      "  blood_type    string\n" +
    //      ")row format delimited fields terminated by \"\\t\""
    //    spark.sql(createSQL)
    //加载数据
    //    val loadDataSQL: String = "load data local  inpath \"data/constellation.txt\" into table person_info"
    //    spark.sql(loadDataSQL)

    //执行需求
    val resultSQL :String ="select t1.key, concat_ws(\"|\", collect_set(t1.name))\n" +
      " from (\n   " +
      " select pi.name, concat(pi.constellation, \",\", pi.blood_type) as key\n" +
      "         from person_info pi) t1\n" +
      "group by t1.key"
    spark.sql(resultSQL).show()
    /*
      +--------+-------------------------------+
      |     key|concat_ws(|, collect_set(name))|
      +--------+-------------------------------+
      |白羊座,A|                  猪八戒|孙悟空|
      |射手座,A|                      大海|凤姐|
      |白羊座,B|                           宋宋|
      +--------+-------------------------------+
     */

    spark.close();
  }

}
