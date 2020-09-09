package com.zx.sql.demo1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * df 与RDD装换
 */
object SQL_Demo_01 {


  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //RDD=>DataFrame=>DataSet转换需要引入隐式转换规则，否则无法转换
    //spark不是包名，是上下文环境对象名
    import sparkSession.implicits._

    //csv   format   jdbc   json   load   option   options   orc   parquet   schema   table   text   textFile
    val df: DataFrame = sparkSession.read.json("spark/src/main/data/jsondata/person.json")
    //查看所有的表信息
    df.show()

    println("----------------SQL 风格语法-------------------------")

    //SQL 风格语法
    df.createOrReplaceTempView("person")

    sparkSession.sql("select * from person where name ='zx'").show()
    sparkSession.sql("select avg(age) from person").show()

    println("--------------DSL 风格语法-----------------------------")
    //DSL 风格语法
    df.select("*").show()

    println("---------------DataFrame 与RDD转换---------------------------------")
    //dataRDD 转成dataFrame
    val value: RDD[(Int, String, Int)] = sparkSession.sparkContext.makeRDD(List((1, "qiaofeng", 30), (2, "xuzhu", 28), (3, "duanyu", 20)))
    val dataFrame: DataFrame = value.toDF("id", "name", "age")
    dataFrame.show()

    //dataFrame转成dataSet
    val ds: Dataset[Row] = dataFrame.as("User")
    ds.show()


    //dataSet 转dataFrame
    val dataFrame1: DataFrame = ds.toDF()

    //dataFrame转成RDD
    val rdd: RDD[Row] = dataFrame1.rdd
    rdd.foreach(a => println(a.getString(1)))


    //RDD ->DataSet
    val ds11: Dataset[User] = value.map {
      case (id, name, age) => User(id, name, age)
    }.toDS()

    //释放资源
    sparkSession.stop()

  }
}


case class User(id: Int, name: String, age: Int)