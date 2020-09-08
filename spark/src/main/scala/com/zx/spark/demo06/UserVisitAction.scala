package com.zx.spark.demo06

/**
 * 用户访问行为表
 *
 * @param date
 * @param user_id
 * @param session_id
 * @param page_id
 * @param action_time
 * @param search_keyword
 * @param click_category_id
 * @param click_product_id
 * @param order_category_ids
 * @param order_product_ids
 * @param pay_category_ids
 * @param pay_product_ids
 * @param city_id
 */
case class UserVisitAction(date: String, //用户点击行为的日期
                           user_id: Long, //用户的ID
                           session_id: String, //Session的ID
                           page_id: Long, //某个页面的ID
                           action_time: String, //动作的时间点
                           search_keyword: String, //用户搜索的关键词
                           click_category_id: Long, //某一个商品品类的ID
                           click_product_id: Long, //某一个商品的ID
                           order_category_ids: String, //一次订单中所有品类的ID集合
                           order_product_ids: String, //一次订单中所有商品的ID集合
                           pay_category_ids: String, //一次支付中所有品类的ID集合
                           pay_product_ids: String, //一次支付中所有商品的ID集合
                           city_id: Long) //城市 id
