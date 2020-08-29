package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 5. map()和mapPartitions()的区别
 * 1.map()：每次处理一条数据。
 * 2.mapPartitions()：每次处理一个分区的数据，这个分区的数据处理完后，原 RDD 中该分区的数据才能释放，可能导致 OOM。
 * 3.开发指导：当内存空间较大的时候建议使用mapPartitions()，以提高处理效率。
 */
object Spark03_Transformation_RDD01 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    /**
     * map(func)
     * 作用: 返回一个新的 RDD, 该 RDD 是由原 RDD 的每个元素经过函数转换后的值而组成. 就是对 RDD 中的数据做转换.
     */
    // TODO 3. 构建RDD
    val numRDD0: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // 将RDD通过算子进行转换，变成新的RDD
    // 转换算子 - map
    val mapRDD: RDD[Int] = numRDD0.map(num => {
      num * 2
    })
    mapRDD.collect().foreach(println)

    val value: RDD[String] = sc.makeRDD(List("coffee panda", "happy panda", "happiest panda party"), 2)
    value.map(str => str.split(" ")).collect().foreach(x=>x.mkString(" "))


    /**
     * mapPartitions(func)
     * 作用: 类似于map(func), 但是是独立在每个分区上运行.所以:Iterator<T> => Iterator<U>
     * 假设有N个元素，有M个分区，那么map的函数的将被调用N次,而mapPartitions被调用M次,一个函数一次处理所有分区。
     */
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    // 将RDD通过算子进行转换，变成新的RDD
    // 转换算子 - mapPartitions
    // mapPartitions以分区单位进行逻辑运算，但是运算过程中，数据不会被释放掉，所以容易产生内存溢出
    // 在这个场合下，一般就采用map算子，保证程序执行通过。
    val mapPartitionsRDD: RDD[Int] = numRDD.mapPartitions(list => {
      println(list)
      list.foreach(println)
      list
    })
    mapPartitionsRDD.collect().foreach(println)

    /**
     * mapPartitionsWithIndex(func)
     * 作用: 和mapPartitions(func)类似. 但是会给func多提供一个Int值来表示分区的索引. 所以func的类型是:(Int, Iterator<T>) => Iterator<U>
     */
    val numRDD2: RDD[Int] = sc.makeRDD(List(1, 3, 2, 4), 2)
    // 循环打印数据，并同时输出数据的分区号
    // 转换算子 - mapPartitionsWithIndex
    val mapPartitionsWithIndexRDD: RDD[(Int, Int)] = numRDD2.mapPartitionsWithIndex(

      (index, datas) => {
        datas.map(
          data => (index, data)
        )
      }
    )

    //(0,(0,1)) (0,(0,3))  (1(1,2)) (1(1,4))

    val collect: Array[(Int, Int)] = mapPartitionsWithIndexRDD.collect
    collect.foreach(println)


    val tuples: Array[(Int, Int)] = collect.filter(t => t._1 == 1)
    println(tuples.map(_._2).sum)

    //关闭context连接
    sc.stop()
  }
}
