package com.zx.sql.demo3

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 一、
 * 求出不同部门男女各多少人。结果如下：
 * A     2       1
 * B     1       2
 */
object SparkSQLEmp {


  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()

    //创建表
    //    val createEmpSql :String ="create table emp_sex\n(\n    name    string,\n    dept_id string,\n    sex     string\n)\n    row format delimited fields terminated by \"\\t\""
    //    val frame: DataFrame = spark.sql(createEmpSql)
    //将数据导入表中
    //    spark.sql("load  data  local inpath 'data/emp_sex.txt' into table emp_sex")

//    spark.sql("select * from emp_sex").show()

    val resultSQL: String = "select es.dept_id,\n       sum(case when es.sex = '男' then 1 else 0 end) as male,\n       sum(case when es.sex = '女' then 1 else 0 end) as female\nfrom emp_sex es\ngroup by es.dept_id"

    spark.sql(resultSQL).show();
    //关闭context连接
    spark.stop()
  }
}
