package main.scala.zx.demo07.chapter02.dataType

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:42
 * FileName: demo05
 * Description: chapter02.dataType
 */
object demo05 {
  def main(args: Array[String]): Unit = {
    var num1: Int = 100
    var num2: Long = 100L

    //1
    val num3 = num1 + num2//num3为Long类型

    var num : Int = 2.7.toInt //强制类型转换
    println(num)
  }
}
