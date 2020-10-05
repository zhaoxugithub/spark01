package com.zx.sql.demo1

import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row, SparkSession, TypedColumn}
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 4)自定义聚合函数实现-强类型（应用于DataSet的DSL更方便）
 */
object SQL_Demo_04 {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val dataFrame: DataFrame = spark.read.json("spark/src/main/data/jsondata/person.json")

    val ds: Dataset[Row] = dataFrame.as("User1")

    val avg = new MyAvg2

    val column: TypedColumn[User1, Double] = avg.toColumn

    ds.select(column).show()

    spark.stop()
  }
}

case class User1(var age: Long)

case class AgeBuffer(var sum: Long, var count: Long)

class MyAvg2 extends Aggregator[User1, AgeBuffer, Double] {
  override def zero: AgeBuffer = {
    AgeBuffer(0L, 0L)
  }

  override def reduce(b: AgeBuffer, a: User1): AgeBuffer = {
    b.sum = b.sum + a.age
    b.count = b.count + 1
    b
  }

  override def merge(b1: AgeBuffer, b2: AgeBuffer): AgeBuffer = {
    b1.count = b1.count + b2.count
    b1.sum = b1.sum + b2.sum
    b1
  }

  //DataSet默认额编解码器，用于序列化，固定写法
  //自定义类型就是produce   自带类型根据类型选择
  override def finish(reduction: AgeBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  override def bufferEncoder: Encoder[AgeBuffer] = {
    Encoders.product
  }

  override def outputEncoder: Encoder[Double] = {
    Encoders.scalaDouble
  }

}

