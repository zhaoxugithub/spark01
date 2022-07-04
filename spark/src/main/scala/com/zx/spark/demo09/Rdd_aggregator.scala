package com.zx.spark.demo09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Rdd_aggregator {


  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    val sourceRDD: RDD[(String, Int)] = sc.parallelize(List(
      ("张三", 40),
      ("张三", 50),
      ("张三", 60),
      ("李四", 100),
      ("李四", 200),
      ("李四", 60),
      ("王五", 900),
      ("王五", 400),
      ("王五", 700),
      ("赵六", 1000),
      ("赵六", 120)
    ))


    //groupByKey ： 行转列
    val groupByRdd: RDD[(String, Iterable[Int])] = sourceRDD.groupByKey()
    groupByRdd.foreach(println)

    println("----------------")
    //列转行 还原
    //方法一：
    groupByRdd.flatMap(e => e._2.map(x => (e._1, x)).iterator).foreach(println)
    //方法二：
    println("-----------------")
    groupByRdd.flatMapValues(v => v.iterator).foreach(println)

    println("-----------------")
    //排序获取前两个元素
    groupByRdd.mapValues(v => v.toList.sorted.take(2)).foreach(println)

    println("-----------------")
    groupByRdd.flatMapValues(v => v.toList.sorted.take(2)).foreach(println)


    println("-------sum,count,min,max,avg-------------------")
    //相同key,求和
    val sumRdd: RDD[(String, Int)] = sourceRDD.reduceByKey(_ + _)

    //求相同key最大值
    val maxRdd: RDD[(String, Int)] = sourceRDD.reduceByKey((v1, v2) => if (v1 > v2) v1 else v2)

    //求相同key的最小值
    val minRDD: RDD[(String, Int)] = sourceRDD.reduceByKey((v1, v2) => if (v1 > v2) v2 else v1)

    //求相同key的count
    val countRDD1: RDD[(String, Int)] = sourceRDD.mapValues(e => 1).reduceByKey(_ + _)
    val countRDD: RDD[(String, Int)] = sourceRDD.map(e => (e._1, 1)).reduceByKey(_ + _)

    //求相同的key的平均值
    val value: RDD[(String, (Int, Int))] = sumRdd.join(countRDD)
    val avgRDD: RDD[(String, Int)] = value.mapValues(x => x._1 / x._2)

    //求平均值的优化方式
    /*
    def combineByKey[C](
    createCombiner: V => C,
      mergeValue: (C, V) => C,
      mergeCombiners: (C, C) => C,
      partitioner: Partitioner,
      mapSideCombine: Boolean = true,
      serializer: Serializer = null): RDD[(K, C)] = self.withScope {
     */
    sourceRDD.combineByKey((x: Int) => (x, 1), ((x: (Int, Int), y: Int) => (x._1 + y, x._2 + 1)), (x: (Int, Int), y: (Int, Int)) => (x._1 + y._1, x._2 + y._2))
    sourceRDD.combineByKey(x => (x, 1), (v1: (Int, Int), v2) => (v1._1 + v2, v1._2 + 1), (x: (Int, Int), y: (Int, Int)) => ((x._1 + y._1, x._2 + y._2))


    )


    sumRdd.foreach(println)
    println("-----------------")
    maxRdd.foreach(println)
    println("-----------------")
    minRDD.foreach(println)
    println("-----------------")
    countRDD1.foreach(println)
    println("------------------")
    avgRDD.foreach(println)

  }
}
