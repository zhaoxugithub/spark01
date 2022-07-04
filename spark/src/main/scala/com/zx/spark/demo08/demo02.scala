package com.zx.spark.demo08

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object demo02 {


  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkout")
    //读取本地文件创建RDD

    val rdd: RDD[String] = sc.makeRDD(List("Hello Scala", "Hello Spark"), 1)

    val mapRdd: RDD[(String, Int)] = rdd.flatMap(x => (x.split(" "))).map(
      x => {
        println("===========")
        (x, 1)
      }
    )
    /*
    (1) ShuffledRDD[3] at reduceByKey at demo02.scala:34 []
     +-(1) MapPartitionsRDD[2] at map at demo02.scala:19 []
        |  MapPartitionsRDD[1] at flatMap at demo02.scala:19 []
        |  ParallelCollectionRDD[0] at makeRDD at demo02.scala:17 []
    ===========
    ===========
    ===========
    ===========
    (Spark,1)
    (Hello,2)
    (Scala,1)
    --------------------------
    (1) ShuffledRDD[3] at reduceByKey at demo02.scala:34 []
     +-(1) MapPartitionsRDD[2] at map at demo02.scala:19 []
        |      CachedPartitions: 1; MemorySize: 336.0 B; ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
        |  MapPartitionsRDD[1] at flatMap at demo02.scala:19 []
        |  ParallelCollectionRDD[0] at makeRDD at demo02.scala:17 []
    (Spark,CompactBuffer(1))
    (Hello,CompactBuffer(1, 1))
    (Scala,CompactBuffer(1))

     */
    //如果运用了cache,就会把第一次计算的结果数据保存到堆内存中，方便后面的rdd计算,但是不会切断血缘,会增加一个依赖CachedPartitions
    //    mapRdd.persist()
    mapRdd.cache()

    //结果数据保存到磁盘，会切断血缘,会单独启动一个job执行，所以一般会结合cache一起用

//    mapRdd.checkpoint()
    //检查点技术
    val value: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
    println(value.toDebugString)
    value.foreach(println)
    println("--------------------------")
    //只是用了mapRDD，但是还是需要重头计算
    val value1: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()
    println(value.toDebugString)
    value1.foreach(println)
  }
}
