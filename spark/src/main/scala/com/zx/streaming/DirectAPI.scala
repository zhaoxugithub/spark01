package com.zx.streaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object DirectAPI {

  def main(args: Array[String]): Unit = {

    //创建SparkConf
    val sparkConf: SparkConf = new SparkConf().setAppName("receiverWordCount").setMaster("local[*]")
    //创建StreamContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    //定义kafka参数
    val kafkaPara: Map[String, Object] = Map[String, Object](ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG ->
      "192.168.1.106:9092,192.168.1.107:9092,192.168.1.108:9092", ConsumerConfig.GROUP_ID_CONFIG -> "Test", "key.deserializer" ->
      "org.apache.kafka.common.serialization.StringDeserializer", "value.deserializer" ->
      "org.apache.kafka.common.serialization.StringDeserializer"
    )

    //读取kafka数据创建DStream
    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set("Test"), kafkaPara))

    //将每一条数据取出来，并且wordCount
    kafkaDStream.map(record => record.value()).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()
    ssc.start()
    ssc.awaitTermination()
  }
}
