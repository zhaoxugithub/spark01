package com.zx.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.{ConnectException, Socket}
import java.nio.charset.StandardCharsets

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark03_CustomerReceiver {

  def main(args: Array[String]): Unit = {
    //1.初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamWordCount")
    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    //3.创建自定义receiver的Streaming
    val lineStream = ssc.receiverStream(new MyReceiver("hadoop202", 9999))
    //4.将每一行数据做切分，形成一个个单词
    val wordStream = lineStream.flatMap(_.split("\t"))
    //5.将单词映射成元组（word,1）
    val wordAndOneStream = wordStream.map((_, 1))
    //6.将相同的单词次数做统计
    val wordAndCountStream = wordAndOneStream.reduceByKey(_ + _)
    //7.打印
    wordAndCountStream.print()
    //8.启动SparkStreamingContext
    ssc.start()
    ssc.awaitTermination()
  }
}


/**
 * Author: Felix
 * Date: 2020/2/20
 * Desc: 自定义数据源
 */
class MyReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {
  //创建一个Socket
  private var socket: Socket = _

  //最初启动的时候，调用该方法，作用为：读数据并将数据发送给Spark
  override def onStart(): Unit = {
    new Thread("Socket Receiver") {
      setDaemon(true)
      override def run() { receive() }
    }.start()
  }

  //读数据并将数据发送给Spark
  def receive(): Unit = {
    try {
      socket = new Socket(host, port)
      //创建一个BufferedReader用于读取端口传来的数据
      val reader = new BufferedReader(
        new InputStreamReader(
          socket.getInputStream, StandardCharsets.UTF_8))
      //定义一个变量，用来接收端口传过来的数据
      var input: String = null

      //读取数据 循环发送数据给Spark 一般要想结束发送特定的数据 如："==END=="
      while ((input = reader.readLine())!=null) {
        store(input)
      }
    } catch {
      case e: ConnectException =>
        restart(s"Error connecting to $host:$port", e)
        return
    }
  }

  override def onStop(): Unit = {
    if(socket != null ){
      socket.close()
      socket = null
    }
  }
}