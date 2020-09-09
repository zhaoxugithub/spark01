package com.zx.sql.demo1

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, IntegerType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 * 自定义UDAF(弱类型，主要应用在SQL 风格的DF查询)
 */
object SQL_Demo_03 {
  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val dataFrame: DataFrame = spark.read.json("spark/src/main/data/jsondata/person.json")

    spark.udf.register("myAvg", new MyAvg)

    dataFrame.createOrReplaceTempView("person")

    spark.sql("select myAvg(age) from person").show()

    spark.stop()
  }
}

class MyAvg extends UserDefinedAggregateFunction {
  // 聚合函数输入参数的数据类型
  override def inputSchema: StructType = StructType(Array(StructField("age", LongType)))

  // 聚合函数缓冲区中值的数据类型(age,count)
  override def bufferSchema: StructType = StructType(Array(StructField("age", LongType), StructField("count", LongType)))

  // 函数返回值的数据类型
  override def dataType: DataType = DoubleType

  // 稳定性：对于相同的输入是否一直返回相同的输出。
  override def deterministic: Boolean = true

  // 函数缓冲区初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1).toDouble
  }
}
