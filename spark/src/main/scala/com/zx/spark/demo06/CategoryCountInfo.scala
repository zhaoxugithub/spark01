package com.zx.spark.demo06

/**
 * 输出结果表
 *
 * @param categoryId
 * @param clickCount
 * @param orderCount
 * @param payCount
 */
case class CategoryCountInfo(categoryId: String, //品类id
                             clickCount: Long, //点击次数
                             orderCount: Long, //订单次数
                             payCount: Long) //支付次数
