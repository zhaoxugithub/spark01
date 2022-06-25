package com.zx.spark.demo05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object RDD_Demo_12 {

  def main(args: Array[String]): Unit = {


    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    // reduceByKey使用
    val dataRDD1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("a", 4)))
    //相同 key 的第一个数据不进行任何计算，分区内和分区间计算规则相同，底层实现是combineByKeyWithClassTag
    println("##########reduceByKey###########")
    dataRDD1.reduceByKey(_ + _).foreach(println)

    /*
    def combineByKeyWithClassTag[C](
      createCombiner: V => C,  初始值操作
      mergeValue: (C, V) => C, 分区内计算规则
      mergeCombiners: (C, C) => C,  分区间操作
      partitioner: Partitioner,
      mapSideCombine: Boolean = true,
      serializer: Serializer = null)(implicit ct: ClassTag[C]): RDD[(K, C)] = self.withScope {
     */
    //初始值不进行计算
    println("##########combineByKeyWithClassTag###########")
    dataRDD1.combineByKeyWithClassTag(num => num, (x: Int, y: Int) => {
      x + y
    }, (x: Int, y: Int) => {
      x + y
    }).foreach(println)

    println("##########combineByKey###########")
    //combineByKey
    /**
     * def combineByKey[C](
     * createCombiner: V => C,
     * mergeValue: (C, V) => C,
     * mergeCombiners: (C, C) => C,
     * partitioner: Partitioner,
     * mapSideCombine: Boolean = true,
     * serializer: Serializer = null): RDD[(K, C)] = self.withScope {
     */
    dataRDD1.combineByKey(num => num, (x: Int, y: Int) => {
      x + y
    }, (x: Int, y: Int) => {
      x + y
    }).foreach(println)

    println("##########groupByKey###########")
    //groupByKey
    //(c,CompactBuffer(3))
    //(b,CompactBuffer(2))
    //(a,CompactBuffer(1, 4))
    dataRDD1.groupByKey().foreach(println)
    dataRDD1.groupByKey().map((x) => (x._1, x._2.sum)) foreach (println)

    println("##########aggregateByKey###########")
    //aggregateByKey
    /**
     * def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner)(seqOp: (U, V) => U,
     * combOp: (U, U) => U): RDD[(K, U)] = self.withScope {
     */
    //相同key求和
    dataRDD1.aggregateByKey((0))((x: Int, y: Int) => {
      x + y
    }, (x: Int, y: Int) => {
      x + y
    }).foreach(println)
    //相同key求平均
    /*
        初始值：(0,0),第一个0表示的是value的初始值，第二个表示的是个数
        第一个函数：(x,y)=>(x._1+y,x._2+1),x表示的是一个元组(原始数值,个数)，这个入参的初始值(0,0)，y表示的另外的一个数值
        第一个函数：(t1,t2)=>(t1._1+t2._1,t1._2+t2._2),t1表示的是一个元组(同一个分区相同key数值总和，同一个分区相同key总个数),
                    t2表示的是另外一个元组(另外一个分区相同key数值总和，另外一个分区相同key总个数)
     */
    dataRDD1.aggregateByKey((0, 0))((x, y) => (x._1 + y, x._2 + 1), (t1, t2) => (t1._1 + t2._1, t1._2 + t2._2)).map(x => (x._1, x._2._1 / x._2._2)).foreach(println)

    println("##########foldByKey###########")
    //foldByKey
    //相同的key求和
    /*
      等同于
       dataRDD1.aggregateByKey((0))((x: Int, y: Int) => {
          x + y
        }, (x: Int, y: Int) => {
          x + y
        }).foreach(println)
     */
    dataRDD1.foldByKey(0)((x, y) => x + y).foreach(println)


    println("##########cogroup###########")
    val dataRDD3 = sc.makeRDD(List(("a", 1), ("a", 2), ("c", 3)))
    val dataRDD4 = sc.makeRDD(List(("a", 1), ("c", 2), ("c", 3)))
    //(c,(CompactBuffer(3),CompactBuffer(2, 3)))
    //(a,(CompactBuffer(1, 2),CompactBuffer(1)))
    val value: RDD[(String, (Iterable[Int], Iterable[Int]))] = dataRDD3.cogroup(dataRDD4)
    value.map(x=>(x._1,x._2._2.sum+x._2._1.sum)).foreach(println)


  }
}
