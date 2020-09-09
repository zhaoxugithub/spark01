package com.zx.streaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Stream_Demo_01 {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //Duration采集的时间间隔
    val context = new StreamingContext(conf, Seconds(3))


    val receiverDS: ReceiverInputDStream[String] = context.socketTextStream("localhost", 9999)


    val flatMapStream: DStream[String] = receiverDS.flatMap(_.split(" "))

    val mapStream: DStream[(String, Int)] = flatMapStream.map((_, 1))

    val reduceStream: DStream[(String, Int)] = mapStream.reduceByKey(_ + _)

    reduceStream.print()

    context.start()

    context.awaitTermination()

  }


}
