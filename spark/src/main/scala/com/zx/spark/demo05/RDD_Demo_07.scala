package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object RDD_Demo_07 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    /**
     * //综合应用算子
     * //topN   分组取TopN  （二次排序）
     * //2019-6-1	39
     * //同月份中 温度最高的2天
     */
    //1970-8-8	32
    val file: RDD[String] = sc.textFile("spark/src/main/data/pvuvdata/tqdata", 1)

    val value: RDD[Array[String]] = file.map(line => line.split("\t"))
    //这里的array是一个数组
    val dataRDD: RDD[(Int, Int, Int, Int)] = value.map(array => {
      val arr: Array[String] = array(0).split("-")
      (arr(0).toInt, arr(1).toInt, arr(2).toInt, array(1).toInt)
    })

    println("-------------相同月份取出最高温的方法-----------------")
    val newTuple2: RDD[((Int, Int), (Int, Int))] = dataRDD.map(ele => ((ele._1, ele._2), (ele._3, ele._4)))

    val reducerRDD: RDD[((Int, Int), (Int, Int))] = newTuple2.reduceByKey((x: (Int, Int), y: (Int, Int)) => {
      if (x._2 > y._2) x else y
    })

    reducerRDD.map(t2 => {
      (t2._1._1 + "-" + t2._1._2 + "-" + t2._2._1, t2._2._2)
    }).sortByKey(false).foreach(println)

    /**
     * 隐式转换
     */
    implicit val sdfsdf = new Ordering[(Int, Int)] {
      override def compare(x: (Int, Int), y: (Int, Int)) = y._2.compareTo(x._2)
    }

    //取出topN 一  分组->去重->排序
    println("-------------相同月份取出最高温两天的方法 一-----------------")
    //去重，排序 缺点是容易OOM 内存溢出  用了groupByKey 容易OOM   且自己的算子实现了函数：去重、排序
    val res: RDD[((Int, Int), List[(Int, Int)])] = dataRDD.map(v => ((v._1, v._2), (v._3, v._4))).groupByKey().mapValues(iter => {
      var map = new mutable.HashMap[Int, Int]()
      iter.foreach(ele => if (map.get(ele._1).getOrElse(0) < ele._2) map.put(ele._1, ele._2))
      map.toList.sorted(new Ordering[(Int, Int)] {
        override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
      })
    })
    res.foreach(println)

    //取出topN 二  去重->分组->排序
    println("-------------相同月份取出最高温两天的方法 二 -----------------")
    //取出同一天最高的温度(去重)   用了groupByKey  容易OOM  取巧：spark rdd  reduceByKey 的取 max间接达到去重  让自己的算子变动简单点
    val reduceRDD: RDD[((Int, Int, Int), Int)] = dataRDD.map(v => ((v._1, v._2, v._3), (v._4))).reduceByKey((x: Int, y: Int) => if (x > y) x else y)
    //转换元组（（x1,x2,x3）,(v1)）=>((x1,x2),(x3,v1))
    val mapRDD: RDD[((Int, Int), (Int, Int))] = reduceRDD.map(v => ((v._1._1, v._1._2), (v._1._3, v._2)))
    //转换成一个groupRDD
    val groupRDD: RDD[((Int, Int), Iterable[(Int, Int)])] = mapRDD.groupByKey()
    val mapValuesRDD: RDD[((Int, Int), List[(Int, Int)])] = groupRDD.mapValues(arr => arr.toList.sorted.take(2))
    mapValuesRDD.foreach(println)

    //另外一种输出方式
    //    mapValuesRDD.flatMapValues(e => e.iterator).map(k => ((k._1._1, k._1._2, k._2._1), (k._2._2))).foreach(println)

    //取出topN  排序->去重->分组（这个做法有点问题）
    println("-------------相同月份取出最高温两天的方法 三-----------------")
    val sortedRDD: RDD[(Int, Int, Int, Int)] = dataRDD.sortBy(t4 => (t4._1, t4._2, t4._4), false)
    //两个shffle之间的key不同，也不是前一个key的子集，会破坏前一个算子的结果
    val reduceRDD2: RDD[((Int, Int, Int), Int)] = sortedRDD.map(t4 => ((t4._1, t4._2, t4._3), (t4._4))).reduceByKey((x: Int, y: Int) => if (x > y) x else y)
    val groupRDD2: RDD[((Int, Int), Iterable[(Int, Int)])] = reduceRDD2.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2))).groupByKey()
    groupRDD2.foreach(println)

    //解决办法：
    //  用了groupByKey  容易OOM  取巧：用了spark 的RDD  sortByKey 排序  没有破坏多级shuffle的key的子集关系
    val sorted: RDD[(Int, Int, Int, Int)] = dataRDD.sortBy(t4 => (t4._1, t4._2, t4._4), false)
    val grouped: RDD[((Int, Int), Iterable[(Int, Int)])] = sorted.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
    grouped.foreach(println)

    //第五代
    //分布式计算的核心思想：调优天下无敌：combineByKey
    //分布式是并行的，离线批量计算有个特征就是后续步骤(stage)依赖其一步骤(stage)
    //如果前一步骤(stage)能够加上正确的combineByKey
    //我们自定的combineByKey的函数，是尽量压缩内存中的数据
    println("------------------终极方案-------------------")
    val combineRDD: RDD[((Int, Int), Array[(Int, Int)])] = dataRDD.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).combineByKey(

      (v: (Int, Int)) => {
        Array(v, (0, 0), (0, 0))
      },
      (oldVal: Array[(Int, Int)], newVal: (Int, Int)) => {
        var flag: Int = 0
        for (i <- 0 until oldVal.length) {
          //如果有相同的日期
          if (oldVal(i)._1 == newVal._1) {
            if (oldVal(i)._2 < newVal._2) {
              flag = 1
              oldVal(i) = newVal
            } else {
              flag = 2
            }
          }
        }
        if (flag == 0) {
          oldVal(oldVal.length - 1) = newVal
        }
        scala.util.Sorting.quickSort(oldVal)
        oldVal
      },
      (v1: Array[(Int, Int)], v2: Array[(Int, Int)]) => {
        val union: Array[(Int, Int)] = v1.union(v2)
        union.sorted
      }
    )
    combineRDD.map(x => x._2.toList).foreach(println)
    //关闭context连接
    sc.stop()
  }
}
