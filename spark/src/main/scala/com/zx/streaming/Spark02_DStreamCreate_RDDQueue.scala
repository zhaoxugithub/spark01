package com.zx.streaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object Spark02_DStreamCreate_RDDQueue {
  def main(args: Array[String]): Unit = {
    // 创建Spark配置信息对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDDStream")
    // 创建SparkStreamingContext
    val ssc = new StreamingContext(conf, Seconds(3))
    // 创建RDD队列
    val rddQueue = new mutable.Queue[RDD[Int]]()
    // 创建QueueInputDStream
    val inputStream = ssc.queueStream(rddQueue, oneAtATime = false)
    // 处理队列中的RDD数据
    val mappedStream = inputStream.map((_, 1))
    val reducedStream = mappedStream.reduceByKey(_ + _)
    // 打印结果
    reducedStream.print()
    // 启动任务
    ssc.start()
    // 循环创建并向RDD队列中放入RDD
    for (i <- 1 to 5) {
      rddQueue += ssc.sparkContext.makeRDD(1 to 300, 10)
      Thread.sleep(2000)
    }
    ssc.awaitTermination()
  }
}
