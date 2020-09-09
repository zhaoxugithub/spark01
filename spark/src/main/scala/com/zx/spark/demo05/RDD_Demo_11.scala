package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器实现平均数
 */
object RDD_Demo_11 {


  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("RDD_Demo_11").setMaster("local[*]")
    val sc = new SparkContext(conf)


    val dataRDD: RDD[(String, Int)] = sc.makeRDD(List(("zhangsan", 11), ("lisi", 33), ("wangwu", 22), ("zhaoliu", 22), ("zhouwu", 66)))

    val accumulator = new AvgAccumulator()
    sc.register(accumulator)

    dataRDD.foreach {
      case (name, age) => {
        accumulator.add(age)
      }
    }

    println(accumulator.value)
    sc.stop()


  }

}

class AvgAccumulator extends AccumulatorV2[Int, Double] {

  private var sum: Int = 0
  private var count: Int = 0


  override def isZero: Boolean = sum == 0 && count == 0

  override def copy(): AccumulatorV2[Int, Double] = {
    val accumulator = new AvgAccumulator()
    accumulator.sum = sum
    accumulator.count = count
    accumulator
  }

  override def reset(): Unit = {
    sum = 0
    count = 0
  }

  override def add(v: Int): Unit = {
    sum += v
    count += 1
  }

  override def merge(other: AccumulatorV2[Int, Double]): Unit = {
    other match {
      case mc: AvgAccumulator => {
        this.sum += mc.sum
        this.count += mc.count
      }
    }
  }

  override def value: Double = sum / count
}