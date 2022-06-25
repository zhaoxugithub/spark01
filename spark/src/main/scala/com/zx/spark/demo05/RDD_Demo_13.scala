package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//行动算子,触发job执行，
object RDD_Demo_13 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),4)
    //reduce算子,返回的是数字不是RDD
    println("##############reduce###################")
    val i: Int = rdd.reduce(_ + _)
    println(i)
    //collect 算子：方法会将不同分区的按照分区顺序采集到Driver端的内存中，形成数组
    println("##############collect###################")
    val ints: Array[Int] = rdd.collect()
    println(ints.mkString(","))
    //count:返回RDD 中元素的个数
    println("##############count###################")
    val countResult: Long = rdd.count()
    println(countResult)
    //first:返回RDD 中的第一个元素
    println("##############first###################")
    val firstResult: Int = rdd.first()
    println(firstResult)
    //take:返回一个由RDD 的前 n 个元素组成的数组
    println("##############take###################")
    val takeResult: Array[Int] = rdd.take(2)
    println(takeResult.mkString(","))
    //takeOrdered:返回该 RDD 排序后的前 n 个元素组成的数组
    println("##############takeOrdered###################")
    val result: Array[Int] = rdd.takeOrdered(2)
    println(result.mkString(","))
    //aggregate:分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合
    // 将该 RDD 所有元素相加得到结果
    println("##############aggregate###################")
    val result1: Int = rdd.aggregate(0)(_ + _, _ + _)

    val result2: Int = rdd.aggregate(10)(_ + _, _ + _)
    //10==60
    println(result1+"=="+result2)

    println("##############fold###################")
    //fold:折叠操作，aggregate 的简化版操作
    val result3: Int = rdd.fold(0)(_ + _)
    println(result3)

    println("##############countByKey###################")
    //countByKey:统计每种 key 的个数
    val rdd1: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (1, "a"), (1, "a"), (2, "b"), (3, "c"), (3, "c")))
    // 统计每种 key 的个数
    val result6: collection.Map[Int, Long] = rdd1.countByKey()
    println(result6)
    println("##############foreach###################")
    //foreach:分布式遍历RDD 中的每一个元素，调用指定函数
    // 收集后打印
    rdd.map(num=>num).collect().foreach(println)
    println("****************")
    // 分布式打印
    rdd.foreach(println)
    //将数据保存到不同格式的文件中
    // 保存成 Text 文件
    rdd.saveAsTextFile("output")
    // 序列化成对象保存到文件
    rdd.saveAsObjectFile("output1")
    // 保存成 Sequencefile 文件
    rdd.map((_,1)).saveAsSequenceFile("output2")
    sc.stop()
  }
}
