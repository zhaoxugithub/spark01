package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_01 {
  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    //去重方法一
    //    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).map((_, 1)).reduceByKey(_ + _).map(_._1).foreach(println)
    //    //去重方法二
    //    sc.parallelize(List(1, 2, 3, 4, 5, 4, 3, 2, 1)).distinct().foreach(println)
    //关闭context连接
    val value: RDD[(String, Int)] = sc.parallelize(List(("a", 1), ("b", 2), ("c", 1), ("d", 3), ("a", 4)), 2)
    value.mapPartitionsWithIndex((index, iter) => {
      iter.map(index + "----" + _)
    }).foreach(println)
    println("--------------------")
    //先分区内部进行比较大小，留下相同的key,value最大的那个元素

    println("---------相同key,留下最大的value元素-----------")
    //相同key,留下最大的value元素
    //方法一：reduceByKey
    println("--------方法一----------")
    //初始值不进行运算，分区内和分区间运算相同
    value.reduceByKey((x, y) => {
      if (x > y) x else y
    }).foreach(println)
    println("--------方法二----------")
    //第一个元素不参加运算
    value.combineByKey(v1 => v1, (v1: Int, v2: Int) => if (v1 > v2) v1 else v2, (v1: Int, v2: Int) => if (v1 > v2) v1 else v2).foreach(println)
    println("--------方法三----------")
    //初始值参与计算,分区内，分区间计算相同
    value.foldByKey(0)((v1, v2) => if (v1 > v2) v1 else v2).foreach(println)

    println("--------方法四----------")
    //初始值参与计算，分区内，分区间计算不同
    value.aggregateByKey(0)((v1, v2) => if (v1 > v2) v1 else v2, (v1, v2) => if (v1 > v2) v1 else v2).foreach(println)

    println("---------相同key求和-----------")
    println("--------方法一----------")
    value.reduceByKey(_ + _).foreach(println)
    println("--------方法二----------")
    value.combineByKey(v1 => v1, (v1: Int, v2: Int) => v1 + v2, (v1: Int, v2: Int) => v1 + v2).foreach(println)
    println("--------方法三----------")
    value.foldByKey(0)((v1: Int, v2: Int) => v1 + v2).foreach(println)
    println("--------方法四----------")
    value.aggregateByKey(0)((v1: Int, v2: Int) => v1 + v2, (v1: Int, v2: Int) => v1 + v2).foreach(println)

    //第二步，将分区间，相同key的value想加在一起

    // def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner)(seqOp: (U, V) => U,
    //      combOp: (U, U) => U): RDD[(K, U)] = self.withScope
    //    val value1: RDD[(String, Int)] = value.aggregateByKey(0)(((x, y) => if (x > y) x else y), (_ + _))
    //
    //    value1.mapPartitionsWithIndex((index, iter) => {
    //      iter.map(index + "----" + _)
    //    }).foreach(println)
    //    val value1: RDD[(String, Int)] = value.aggregateByKey(0)(_ + _, _ + _)

    println("---------相同key求均值-----------")
    //方法一：
    println("--------方法一----------")
    val value1: RDD[(String, (Int, Int))] = value.map(t1 => (t1._1, 1)).reduceByKey(_ + _).join(value.reduceByKey(_ + _))
    value1.map(t => (t._1, t._2._2 / t._2._1)).foreach(println)
    println("--------方法二----------")
    value.map(t1 => (t1._1, (t1._2, 1))).reduceByKey((oldV, newV) => (oldV._1 + newV._1, oldV._2 + newV._2)).map(t => (t._1, t._2._1 / t._2._2)).foreach(println)
    println("--------方法三----------")
    value.mapValues(x => (x, 1)).reduceByKey((oldV, newV) => (oldV._1 + newV._1, oldV._2 + newV._2)).map(t => (t._1, t._2._1 / t._2._2)).foreach(println)
    println("--------方法四----------")
    value.groupByKey().map(x => (x._1, x._2.sum / x._2.size)).foreach(println)
    println("--------方法五----------")
    //第一个参数是转换结构
    value.combineByKey(v => (v, 1), (c: (Int, Int), v: Int) => (c._1 + v, c._2 + 1), (c1: (Int, Int), c2: (Int, Int)) => (c1._1 + c2._1, c1._2 + c2._2)).map(x => (x._1, x._2._1 / x._2._2)).foreach(println)



    //  def combineByKey[C](
    //      createCombiner: V => C,
    //      mergeValue: (C, V) => C,
    //      mergeCombiners: (C, C) => C,
    //      partitioner: Partitioner,
    //      mapSideCombine: Boolean = true,
    //      serializer: Serializer = null): RDD[(K, C)] = self.withScope

    //(https://www.jianshu.com/p/bbc532bccd23
    //    val value2: RDD[(String, (Int, Int))] = value.combineByKey((x: Int) => (x, 1), ((x: (Int, Int), y: Int) => (x._1 + y, x._2 + 1)), (x: (Int, Int), y: (Int, Int)) => (x._1 + y._1, x._2 + y._2))
    //    value2.map((x) => (x._1, (x._2._1 / x._2._2))).foreach(println)
    //    println("---求平均方法二--------")
    //    value.groupByKey().map((x) => (x._1, x._2.sum / x._2.size)).foreach(println)
    //    println("---求平均方法三--------")
    //    value.map(x => (x._1, (x._2, 1))).reduceByKey((x, y) => ((x._1 + y._1), (x._2 + y._2))).map(x => (x._1, x._2._1 / x._2._2)).foreach(println)
    //groupByKey 和 mapValues 连用效果比较好

    Thread.sleep(Long.MaxValue)
  }
}


