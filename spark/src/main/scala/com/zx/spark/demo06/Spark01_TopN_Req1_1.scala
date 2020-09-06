package com.zx.spark.demo06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
 * 2019-07-17_95_26070e87-1ad7-49a3-8fb3-cc741facaddf_37_2019-07-17 00:00:02_手机_-1_-1_null_null_null_null_3
 * 用户id -------- 95
 * session id --------- 26070e87-1ad7-49a3-8fb3-cc741facaddf
 * 页面id ------ 37
 * 搜索关键字 ------- 手机
 * 点击品类id 和 产品id -------- -1_-1
 * 下单品类id 和产品id ---------- null_null
 * 支付品类ids 和 产品ids ---------- null_null
 * 城市id  ------------ 3
 *
 * （1）数据采用_分割字段
 * （2）每一行表示用户的一个行为，所以每一行只能是四种行为中的一种。
 * （3）如果搜索关键字是null，表示这次不是搜索
 * （4）如果点击的品类id和产品id是-1表示这次不是点击
 * （5）下单行为来说一次可以下单多个产品，所以品类id和产品id都是多个，id之间使用逗号，分割。如果本次不是下单行为，则他们相关数据用null来表示
 * （6）支付行为和下单行为类似
 */


/**
 * 需求1：Top10热门品类
 *
 * 需求说明：品类是指产品的分类，大型电商网站品类分多级，咱们的项目中品类只有一级，不同的公司可能对热门的定义不一样。我们按照每个品类的点击、下单、支付的量来统计热门品类。
 * 鞋			点击数 下单数  支付数
 * 衣服		点击数 下单数  支付数
 * 生活用品	点击数 下单数  支付数
 * 例如，综合排名=点击数*20%+下单数*30%+支付数*50%
 * 本项目需求优化为：先按照点击数排名，靠前的就排名高；如果点击数相同，再比较下单数；下单数再相同，就比较支付数。
 */

object Spark01_TopN_Req1_1 {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    //创建SparkContext
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val fileRDD: RDD[String] = sc.textFile("spark/src/main/data/shop/input/user_visit_action.txt")

    val mapRDD: RDD[UserVisitAction] = fileRDD.map(line => {
      val fields: Array[String] = line.split("_")
      UserVisitAction(
        fields(0),
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
        fields(12).toLong
      )
    })

    val flatMapRDD: RDD[CategoryCountInfo] = mapRDD.flatMap(userVisitAction => {
      if (userVisitAction.click_category_id != -1) {
        List(CategoryCountInfo(userVisitAction.click_category_id + "", 1, 0, 0))
      } else if (userVisitAction.order_category_ids != "null") {

        val listBuffer = new ListBuffer[CategoryCountInfo]
        val orderCategoryId: Array[String] = userVisitAction.order_category_ids.split(",")
        for (orderId <- orderCategoryId) {
          listBuffer.append(CategoryCountInfo(orderId, 0, 1, 0))
        }
        listBuffer

      } else if (userVisitAction.pay_category_ids != "null") {

        val listBuffer = new ListBuffer[CategoryCountInfo]
        val payCategoryId: Array[String] = userVisitAction.pay_category_ids.split(",")

        for (payId <- payCategoryId) {
          listBuffer.append(CategoryCountInfo(payId, 0, 0, 1))
        }
        listBuffer
      } else {
        Nil
      }
    })

    val groupRDD: RDD[(String, Iterable[CategoryCountInfo])] = flatMapRDD.groupBy(categoryCountInfo => categoryCountInfo.categoryId)

    val mapValuesRDD: RDD[(String, CategoryCountInfo)] = groupRDD.mapValues(iter => {
      iter.reduce((info1, info2) => {
        CategoryCountInfo(info1.categoryId, info1.clickCount + info2.clickCount, info1.orderCount + info2.orderCount, info1.payCount + info2.payCount)
      })
    })

    val mapRDD2: RDD[CategoryCountInfo] = mapValuesRDD.map(_._2)

    val res: Array[CategoryCountInfo] = mapRDD2.sortBy(info => (info.clickCount, info.orderCount, info.payCount), false).take(10)

    res.foreach(println)
    //关闭context连接
    sc.stop()
  }
}


/**
 * 第一种方法
CategoryCountInfo(15,6120,1672,1259)
CategoryCountInfo(2,6119,1767,1196)
CategoryCountInfo(20,6098,1776,1244)
CategoryCountInfo(12,6095,1740,1218)
CategoryCountInfo(11,6093,1781,1202)
CategoryCountInfo(17,6079,1752,1231)
CategoryCountInfo(7,6074,1796,1252)
CategoryCountInfo(9,6045,1736,1230)
CategoryCountInfo(19,6044,1722,1158)
CategoryCountInfo(13,6036,1781,1161)

 第二种方法：
CategoryCountInfo(15,6120,1672,1259)
CategoryCountInfo(2,6119,1767,1196)
CategoryCountInfo(20,6098,1776,1244)
CategoryCountInfo(12,6095,1740,1218)
CategoryCountInfo(11,6093,1781,1202)
CategoryCountInfo(17,6079,1752,1231)
CategoryCountInfo(7,6074,1796,1252)
CategoryCountInfo(9,6045,1736,1230)
CategoryCountInfo(19,6044,1722,1158)
CategoryCountInfo(13,6036,1781,1161)
 */