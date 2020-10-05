package com.zx.sql.demo3

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQLMovie {
  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()
    //创建表
    //    val createSQL: String = "create table movie_info\n(\n" +
    //      "    movie    string,\n " +
    //      "   category array<string>\n" +
    //      ")\n  " +
    //      "  row format delimited fields terminated by \"\\t\"\n   " +
    //      "     collection items terminated by \",\""
    //    spark.sql(createSQL)
    //    //加载数据
    //    val loadDataSQL: String = "load data local inpath \"data/movie.txt\" into table movie_info"
    //    spark.sql(loadDataSQL)
    val resultSQL: String = "select mi.movie, category_new\n" +
      "from movie_info mi lateral view explode(mi.category) categoryTable as category_new"
    spark.sql(resultSQL).show()

    /*
    +-------------+------------+
|        movie|category_new|
+-------------+------------+
| 《疑犯追踪》|        悬疑|
| 《疑犯追踪》|        动作|
| 《疑犯追踪》|        科幻|
| 《疑犯追踪》|        剧情|
|《Lie to me》|        悬疑|
|《Lie to me》|        警匪|
|《Lie to me》|        动作|
|《Lie to me》|        心理|
|《Lie to me》|        剧情|
|    《战狼2》|        战争|
|    《战狼2》|        动作|
|    《战狼2》|        灾难|
+-------------+------------+
     */
  }
}
