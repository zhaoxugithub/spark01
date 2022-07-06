package com.zx.spark.demo09

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable


object lesson06_rdd_over {

  def main(args: Array[String]): Unit = {

    //综合应用算子
    //topN   分组排序取TopN  （二次排序）
    //2019-6-1	39
    //同月份中 温度最高的2天
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("topN")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    //    val data: RDD[String] = sc.parallelize(List(
    //      "hello world",
    //      "hello spark",
    //      "hello world",
    //      "hello hadoop",
    //      "hello world",
    //      "hello msb",
    //      "hello world"
    //    ))
    //    val words: RDD[String] = data.flatMap(_.split(" "))
    //    val kv: RDD[(String, Int)] = words.map((_, 1))
    //    val res: RDD[(String, Int)] = kv.reduceByKey(_ + _)
    //    //      val res01: RDD[(String, Int)] = res.map(x=>(x._1,x._2*10))
    //    val res01: RDD[(String, Int)] = res.mapValues(x => x * 10)
    //    val res02: RDD[(String, Iterable[Int])] = res01.groupByKey()
    //    res02.foreach(println)

    val dataRDD: RDD[String] = sc.textFile("spark/src/main/data/pvuvdata/tqdata", 4)
    val sourceRDD: RDD[(Int, Int, Int, Int)] = dataRDD.map(e => e.split("\t")).map(arr => {
      val strings: Array[String] = arr(0).split("-")
      //年月日 温度
      (strings(0).toInt, strings(1).toInt, strings(2).toInt, arr(1).toInt)
    })

    println("---------方法一-------------")
    //第一种方式
    //缺点：1.groupByKey() 占用内存，当数据量非常大的时候，会非常影响性能，占用内存资源
    //缺点：2.new 出了一个map，可能会产生OOM
    val mapRDD01: RDD[((Int, Int), (Int, Int))] = sourceRDD.map(e => ((e._1, e._2), (e._3, e._4)))
    val groupedRDD01: RDD[((Int, Int), Iterable[(Int, Int)])] = mapRDD01.groupByKey()
    val valueRDD01: RDD[((Int, Int), List[(Int, Int)])] = groupedRDD01.mapValues(arr => {
      //去重操作
      val map = new mutable.HashMap[Int, Int]()
      arr.foreach(x => {
        val temp: Int = map.get(x._1).getOrElse(0)
        if (temp < x._2) map.put(x._1, x._2)
      })
      //自定义排序规则
      map.toList.sorted(new Ordering[(Int, Int)] {
        override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
      })
    })
    valueRDD01.foreach(println)
    println("---------方法二------------")
    //第二种方式
    //缺点：运用了groupBykey，坑会OOM
    //把同一天的数据去重.留下较大的
    val reduceRDD02: RDD[((Int, Int, Int), Int)] = sourceRDD.map(t => ((t._1, t._2, t._3), (t._4))).reduceByKey((oldV, newV) => Math.max(oldV, newV))
    val groupRDD02: RDD[((Int, Int), Iterable[(Int, Int)])] = reduceRDD02.map(t => ((t._1._1, t._1._2), (t._1._3, t._2))).groupByKey()
    val valueRDD02: RDD[((Int, Int), List[(Int, Int)])] = groupRDD02.mapValues(v => v.toList.sorted(new Ordering[(Int, Int)] {
      override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
    }).take(2))
    valueRDD02.foreach(println)

    println("---------方法三------------------")

    //将数据降序排列
    //  用了groupByKey  容易OOM  取巧：用了spark 的RDD 的reduceByKey 去重，用了sortByKey 排序
    //  注意：多级shuffle关注  后续书法的key一定得是前置rdd  key的子集
    val sortRDD03: RDD[(Int, Int, Int, Int)] = sourceRDD.sortBy(t4 => (t4._1, t4._2, t4._4), false)
    val reduceRDD03: RDD[((Int, Int, Int), Int)] = sortRDD03.map(t4 => ((t4._1, t4._2, t4._3), (t4._4))).reduceByKey((oldV, newV) => (Math.max(oldV, newV)))
    val groupRDD03: RDD[((Int, Int), Iterable[(Int, Int)])] = reduceRDD03.map(t4 => ((t4._1._1, t4._1._2), (t4._1._3, t4._2))).groupByKey()
    groupRDD03.foreach(println)


    println("--------方法四----------")
    val sortRDD04: RDD[(Int, Int, Int, Int)] = sourceRDD.sortBy(t4 => (t4._1, t4._2, t4._4), false)




    //最终方案：
    //第五代
    //分布式计算的核心思想：调优天下无敌：combineByKey
    //分布式是并行的，离线批量计算有个特征就是后续步骤(stage)依赖其一步骤(stage)
    //如果前一步骤(stage)能够加上正确的combineByKey
    //我们自定的combineByKey的函数，是尽量压缩内存中的数据

    val kv: RDD[((Int, Int), (Int, Int))] = sourceRDD.map(t4 => ((t4._1, t4._2), (t4._3, t4._4)))

    val res: RDD[((Int, Int), Array[(Int, Int)])] = kv.combineByKey(
      //第一条记录怎么放：
      (v1: (Int, Int)) => {
        Array(v1, (0, 0), (0, 0))
      },
      //第二条，以及后续的怎么放：
      (oldv: Array[(Int, Int)], newv: (Int, Int)) => {
        //去重，排序
        var flg = 0 //  0,1,2 新进来的元素特征：  日 a)相同  1）温度大 2）温度小   日 b)不同

        for (i <- 0 until oldv.length) {
          if (oldv(i)._1 == newv._1) {
            if (oldv(i)._2 < newv._2) {
              flg = 1
              oldv(i) = newv
            } else {
              flg = 2
            }
          }
        }
        if (flg == 0) {
          oldv(oldv.length - 1) = newv
        }

        //        oldv.sorted
        scala.util.Sorting.quickSort(oldv)
        oldv

      },
      (v1: Array[(Int, Int)], v2: Array[(Int, Int)]) => {
        //关注去重
        val union: Array[(Int, Int)] = v1.union(v2)
        union.sorted
      }

    )
    res.map(x => (x._1, x._2.toList)).foreach(println)

    while (true) {}


  }

}
