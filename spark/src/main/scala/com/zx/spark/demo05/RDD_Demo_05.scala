package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * //面向数据集开发  面向数据集的API  1，基础API   2，复合API
 * //RDD  （HadoopRDD,MappartitionsRDD,ShuffledRDD...）
 * //map,flatMap,filter
 * //distinct...
 * //reduceByKey:  复合  ->  combineByKey（）
 *
 *
 * //  面向数据集：  交并差  关联 笛卡尔积
 *
 * //面向数据集： 元素 -->  单元素，K,V元素  --> 机构化、非结构化
 */
object RDD_Demo_05 {


  def main(args: Array[String]): Unit = {
    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)

    sc.setLogLevel("ERROR")

    //spark很人性，面向数据集提供了不同的方法的封装，且，方法已经经过经验，常识，推算出自己的实现方式
    //人不需要干预（会有一个算子）
    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5), 2)
    val rdd2: RDD[Int] = sc.parallelize(List(3, 4, 5, 6, 7), 2)
    //union 合并不需要shuffle
    val merge: RDD[Int] = rdd1.union(rdd2)

    println(merge.getNumPartitions)

    merge.mapPartitionsWithIndex((pindex, iter) => {
      iter.map(x => (pindex, x))
    }).foreach(println)

    println("----------------笛卡尔积、差集、交集----------------")

    println("----------------笛卡尔积----------------")
    /**
     * //  如果数据，不需要区分每一条记录归属于那个分区。。。间接的，这样的数据不需要partitioner。。不需要shuffle
     * //    //因为shuffle的语义：洗牌  ---》面向每一条记录计算他的分区号
     * //    //如果有行为，不需要区分记录，本地IO拉去数据，那么这种直接IO一定比先Parti。。计算，shuffle落文件，最后在IO拉去速度快！！！
     */
    //笛卡尔积 不需要shuffle,有待理解
    val cartRDD: RDD[(Int, Int)] = rdd1.cartesian(rdd2)
    cartRDD.foreach(println)
    println("----------------差集----------------")
    //差集，有方向需要shuffle
    val sub: RDD[Int] = rdd1.subtract(rdd2)
    sub.foreach(println)
    println("----------------交集----------------")
    //交集.需要shuffle
    val interRDD: RDD[Int] = rdd1.intersection(rdd2)
    interRDD.foreach(println)

    println("-------------分割线1----------")

    val kv1: RDD[(String, Int)] = sc.parallelize(List(
      ("zhangsan", 11),
      ("zhangsan", 12),
      ("lisi", 13),
      ("wangwu", 14)
    ))
    val kv2: RDD[(String, Int)] = sc.parallelize(List(
      ("zhangsan", 21),
      ("zhangsan", 22),
      ("lisi", 23),
      ("zhaoliu", 28)
    ))

    val data0: RDD[(String, (Iterable[Int], Iterable[Int]))] = kv1.cogroup(kv2)
    data0.foreach(println)

    val joinRDD: RDD[(String, (Int, Int))] = kv1.join(kv2)
    joinRDD.foreach(println)

    println("----------------------------------")
    val leftJoinRDD: RDD[(String, (Int, Option[Int]))] = kv1.leftOuterJoin(kv2)
    leftJoinRDD.foreach(println)

    println("------------------------------")
    val rightJoinRDD: RDD[(String, (Option[Int], Int))] = kv1.rightOuterJoin(kv2)
    rightJoinRDD.foreach(println)


    println("------------fullJoin------------------")
    val fullJoinRDD: RDD[(String, (Option[Int], Option[Int]))] = kv1.fullOuterJoin(kv2)
    fullJoinRDD.foreach(println)
    //关闭context连接
    sc.stop()


  }
}
