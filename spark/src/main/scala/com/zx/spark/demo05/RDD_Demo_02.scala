package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_02 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    val originRDD: RDD[(String, Int)] = sc.parallelize(List(
      ("zhaoxu", 1),
      ("zhaoxu", 2),
      ("zhaoxu", 3),
      ("zhaoxu", 4),
      ("lisi", 1),
      ("lisi", 2),
      ("wangwu", 1),
      ("wangwu", 2),
      ("wangwu", 3)
    ))


    sc.parallelize(List(1, 2, 3, 4, 5, 6, 7)).groupBy(x => if (x > 3) {
      "more"
    } else {
      "less"
    }).foreach(println)

    //多进入一出
    val group: RDD[(String, Iterable[Int])] = originRDD.groupByKey()
    group.foreach(println)


    println("----行列转转-------")
    val res01: RDD[(String, Int)] = group.flatMap(e => {
      e._2.map(x => (e._1, x))
    }.iterator)
    res01.foreach(println)
    println("------------------")
    group.flatMapValues(e => e.iterator).foreach(println) //这里的e是K-V 中的V

    println("------------------")
    group.mapValues(e => e.toList.sorted.take(1)).foreach(println) //这里的e是K-V中的V

    println("--------sum,count,min,max,avg------------")

    println("----------------sum----------------------")
    val sum: RDD[(String, Int)] = originRDD.reduceByKey((x, y) => x + y)
    sum.foreach(println)

    println("--------------max------------------------")
    val max: RDD[(String, Int)] = originRDD.reduceByKey((x, y) => if (x > y) x else y)
    max.foreach(println)

    println("--------------min-----------------------")
    val min: RDD[(String, Int)] = originRDD.reduceByKey((x, y) => if (x > y) y else x)
    min.foreach(println)

    println("--------------count-----------------------")
    val count: RDD[(String, Int)] = originRDD.mapValues(e => 1).reduceByKey((x, y) => x + y)
    count.foreach(println)

    println("--------------avg-----------------------")
    val avg: RDD[(String, Int)] = sum.join(count).mapValues(e => e._1 / e._2)
    avg.foreach(println)

    println("--------avg-----combine-------")

    originRDD.combineByKey(
      //      createCombiner: V => C,
      //第一条记录的 value  怎么放入 hashmap
      (value: Int) => (value, 1),
      //      mergeValue: (C, V) => C,
      //如果有第二条记录，第二条以及以后的他们的value怎么放到hashmap里：
      (oldValue: (Int, Int), newValue: Int) => (oldValue._1 + newValue, oldValue._2 + 1),
      //      mergeCombiners: (C, C) => C,
      //合并溢写结果的函数：
      (value1: (Int, Int), value2: (Int, Int)) => (value1._1 + value2._1, value2._2 + value2._2)
    ).mapValues(e => (e._1 * e._2)).foreach(println)

    while (true) {

    }
    //关闭context连接
    sc.stop()
  }
}
