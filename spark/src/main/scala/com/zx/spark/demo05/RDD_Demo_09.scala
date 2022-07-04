package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Demo_09 {

  def main(args: Array[String]): Unit = {


    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5), 2)
    rdd1.mapPartitionsWithIndex((pindex, iter) => {
      iter.map(e => (pindex, e))
    }).foreach(println)

    var sum = 0
    rdd1.foreach((x) => {
      sum += x
      println(sum)
    })

    println(sum)
    /**
     * (0,1)
     * (0,2)
     * (1,3)
     * (1,4)
     * (1,5)
     * 3
     * 7
     * 12
     * 1
     * 3
     */

    //处理需求


    //累加器
    val count: LongAccumulator = sc.longAccumulator("count")

    rdd1.foreach(count.add(_))

    print(count)





    //关闭context连接
    sc.stop()


  }

}
