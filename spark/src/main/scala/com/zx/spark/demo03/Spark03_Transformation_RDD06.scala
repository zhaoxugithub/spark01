package com.zx.spark.demo03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * sample(withReplacement, fraction, seed)
 * 作用:
 * 1.以指定的随机种子随机抽样出比例为fraction的数据，(抽取到的数量是: size * fraction). 需要注意的是得到的结果并不能保证准确的比例.
 * 2.withReplacement表示是抽出的数据是否放回，true为有放回的抽样，false为无放回的抽样. 放回表示数据有可能会被重复抽取到, false 则不可能重复抽取到. 如果是false, 则fraction必须是:[0,1], 是 true 则大于等于0就可以了.
 * 3.seed用于指定随机数生成器种子。 一般用默认的, 或者传入当前的时间戳
 */
object Spark03_Transformation_RDD06 {

  def main(args: Array[String]): Unit = {
    // TODO 1. 创建Spark配置对象
    val sparkConf = new SparkConf().setAppName("Spark08_RDD_Transform5").setMaster("local[*]")

    // TODO 2. 创建Spark环境连接对象
    val sc = new SparkContext(sparkConf)

    //TODO 3. 构建RDD
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    // 随机抽取数据
    val sampleRDD: RDD[Int] = numRDD.sample(true, 1, 1)
    //1,1,1,1,2,2,2,2,3,4,4,5,5,5,5,6,6,6,6,6,7,8,8,9,9,10,10,10
    println(sampleRDD.collect().mkString(","))

    // TODO 9. 释放连接
    sc.stop()
  }

}
