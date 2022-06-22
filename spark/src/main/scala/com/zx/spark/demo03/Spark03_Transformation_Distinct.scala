package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Transformation_Distinct {
  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    //处理需求
    val orginRDD: RDD[Int] = sc.makeRDD(List(1, 2, 2, 2, 3, 3, 4, 5, 6, 7, 7, 7, 8, 9, 8, 9), 3)

    /*
        [U: ClassTag] :泛型
         f: (Int, Iterator[T]) => Iterator[U] :函数签名：表示函数逻辑，返回一个U类型的迭代器
         preservesPartitioning: Boolean = false
         RDD[U] = withScope :返回一个U类型的RDD算子

         def mapPartitionsWithIndex[U: ClassTag](
         f: (Int, Iterator[T]) => Iterator[U],
         preservesPartitioning: Boolean = false): RDD[U] = withScope
    */
    orginRDD.mapPartitionsWithIndex { (index, datas) => {
      println(index + "--->" + datas.mkString(","))
      datas
    }
    }.collect()
    println("---------------------------------")
    val distinctRDD: RDD[Int] = orginRDD.distinct(2)
    // distinct底层源码  map(x => (x, null)).reduceByKey((x, y) => x, numPartitions).map(_._1)
    distinctRDD.mapPartitionsWithIndex { (index, datas) => {
      println(index + "--->" + datas.mkString(","))
      datas
    }
    }.collect()
    //关闭context连接
    sc.stop()
  }
}
