package com.zx.spark.demo06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object Spark01_TopN_Req1_2 {
  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)


    sc.setLogLevel("ERROR")
    val dataRDD: RDD[String] = sc.textFile("spark/src/main/data/shop/input/user_visit_action.txt")
    val mapRDD: RDD[UserVisitAction] = dataRDD.map(line => {
      val fields: Array[String] = line.split("_")
      UserVisitAction(fields(0),
        fields(1).toLong,
        fields(2),
        fields(3).toLong,
        fields(4),
        fields(5),
        fields(6).toLong,
        fields(7).toLong,
        fields(8),
        fields(9),
        fields(10),
        fields(11),
        fields(12).toLong)
    })

    val flatMapRDD: RDD[(String, CategoryCountInfo)] = mapRDD.flatMap(userVisitAction => {

      if (userVisitAction.click_category_id != -1) {
        List((userVisitAction.click_category_id + "", CategoryCountInfo(userVisitAction.click_category_id + "", 1, 0, 0)))
      } else if (userVisitAction.order_category_ids != "null") {
        val listBuff = new ListBuffer[(String, CategoryCountInfo)]
        val orderIds: Array[String] = userVisitAction.order_category_ids.split(",")
        for (orderId <- orderIds) {
          listBuff.append((orderId, CategoryCountInfo(orderId, 0, 1, 0)))
        }
        listBuff
      } else if (userVisitAction.pay_category_ids != "null") {
        val listBuffer = new ListBuffer[(String, CategoryCountInfo)]
        val payIds: Array[String] = userVisitAction.pay_category_ids.split(",")
        for (payId <- payIds) {
          listBuffer.append((payId, CategoryCountInfo(payId, 0, 0, 1)))
        }
        listBuffer
      } else {
        Nil
      }
    })
    val reduceRDD: RDD[(String, CategoryCountInfo)] = flatMapRDD.reduceByKey((category1, category2) => {
      CategoryCountInfo(category1.categoryId, category1.clickCount + category2.clickCount, category1.orderCount + category2.orderCount, category1.payCount + category2.payCount)
    })
    val res: Array[CategoryCountInfo] = reduceRDD.map(_._2).sortBy(categoryCountInfo => (categoryCountInfo.clickCount, categoryCountInfo.orderCount, categoryCountInfo.payCount), false).take(10)
    res.foreach(println)
    //关闭context连接
    sc.stop()
  }
}
