package com.zx.sql.demo3

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
//-- from-where-groupby-having-select-orderby-limit
//窗口函数都是最后一步执行，而且仅位于Order by字句之前
/**
 * /**
 * 四、开窗函数
 * 1．相关函数说明
 * OVER()：指定分析函数工作的数据窗口大小，这个数据窗口大小可能会随着行的变而变化。
 * CURRENT ROW：当前行
 * n PRECEDING：往前n行数据
 * n FOLLOWING：往后n行数据
 * UNBOUNDED：起点，UNBOUNDED PRECEDING 表示从前面的起点， UNBOUNDED FOLLOWING表示到后面的终点
 * LAG(col,n,default_val)：往前第n行数据
 * LEAD(col,n, default_val)：往后第n行数据
 * NTILE(n)：把有序分区中的行分发到指定数据的组中，各个组有编号，编号从1开始，对于每一行，NTILE返回此行所属的组的编号。注意：n必须为int类型。
 **/
 */
object SparkSQLBussiness {
  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()

    //    //创建表
    //    val createSQL: String = "create table business\n" +
    //      "(\n   " +
    //      " name      string,\n  " +
    //      "  orderdate string,\n    cost      int\n" +
    //      ") ROW FORMAT DELIMITED FIELDS TERMINATED BY ','"
    //    spark.sql(createSQL)
    //    //将数据导入表中
    //    val loadDataSQL: String = "load data local inpath \"data/shop.txt\" into table business"
    //    spark.sql(loadDataSQL)

    spark.sql("select * from business where substring(orderdate, 1, 7) = '2017-04'").show()
    /*
        +----+----------+----+
        |name| orderdate|cost|
        +----+----------+----+
        |jack|2017-01-01|  10|
        |tony|2017-01-02|  15|
        |jack|2017-02-03|  23|
        |tony|2017-01-04|  29|
        |jack|2017-01-05|  46|
        |jack|2017-04-06|  42|
        |tony|2017-01-07|  50|
        |jack|2017-01-08|  55|
        |mart|2017-04-08|  62|
        |mart|2017-04-09|  68|
        |neil|2017-05-10|  12|
        |mart|2017-04-11|  75|
        |neil|2017-06-12|  80|
        |mart|2017-04-13|  94|
        +----+----------+----+
     */
    //（1）查询在2017年4月份购买过的顾客及总人数  count(*) 的结果是在group by 之后再去统计的
    val resultSQL: String = "select name, count(*)\n" +
      "from business\n" +
      "where substring(orderdate, 1, 7) = '2017-04'\n" +
      "group by name"
    spark.sql(resultSQL).show()
    /*
    +----+--------+
    |name|count(1)|
    +----+--------+
    |mart|       4|
    |jack|       1|
    +----+--------+
     */

    val resultSQL2: String = "select name, count(*) over ()\n" +
      "from business\n" +
      "where substring(orderdate, 1, 7) = '2017-04'\n" +
      "group by name"
    spark.sql(resultSQL2).show()
    /*
    +----+------------------------------------------------------------------------+
    |name|count(1) OVER (ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)|
    +----+------------------------------------------------------------------------+
    |mart|                                                                       2|
    |jack|                                                                       2|
    +----+------------------------------------------------------------------------+
     */

    //（2）查询顾客的购买明细及月购买总额 先开窗设置数据大小，然后计算sum(cost)
    val resultSQL3: String = "select name,orderdate,cost,sum(cost) over(partition by name) from business"
    spark.sql(resultSQL3).show()
    /*
        +----+----------+----+-----------------------------------------------------------------------------------------------------------+
        |name| orderdate|cost|sum(CAST(cost AS BIGINT)) OVER (PARTITION BY name ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)|
        +----+----------+----+-----------------------------------------------------------------------------------------------------------+
        |mart|2017-04-08|  62|                                                                                                        299|
        |mart|2017-04-09|  68|                                                                                                        299|
        |mart|2017-04-11|  75|                                                                                                        299|
        |mart|2017-04-13|  94|                                                                                                        299|
        |jack|2017-01-01|  10|                                                                                                        176|
        |jack|2017-02-03|  23|                                                                                                        176|
        |jack|2017-01-05|  46|                                                                                                        176|
        |jack|2017-04-06|  42|                                                                                                        176|
        |jack|2017-01-08|  55|                                                                                                        176|
        |tony|2017-01-02|  15|                                                                                                         94|
        |tony|2017-01-04|  29|                                                                                                         94|
        |tony|2017-01-07|  50|                                                                                                         94|
        |neil|2017-05-10|  12|                                                                                                         92|
        |neil|2017-06-12|  80|                                                                                                         92|
        +----+----------+----+-----------------------------------------------------------------------------------------------------------+
     */
    //（3）上述的场景, 将每个顾客的cost按照日期进行累加
    val resultSQL4: String = "select name,orderdate \n " +
      "      cost,\n   " +
      "    sum(cost) over (partition by name order by orderdate rows between UNBOUNDED PRECEDING and current row )\n" +
      "from business"
    spark.sql(resultSQL4).show()
    //（4）查看顾客上次的购买时间
    val resultSQL5: String = "select name,\n   " +
      "    orderdate,\n  " +
      "     cost,\n     " +
      "  lag(orderdate, 1, \"1900-01-01\") over (partition by name order by orderdate) as time1\n   " +
      "from business"
    spark.sql(resultSQL5).show()
    //（5）查询前20%时间的订单信息
    val resultSQL6: String = "select *\n" +
      "from (\n" +
      "         select name, orderdate, cost, ntile(5) over (order by orderdate) sorted\n      " +
      "   from business\n   " +
      "  ) t\n" +
      "where sorted = 1"
    spark.sql(resultSQL6).show()
    spark.close()
  }
}
