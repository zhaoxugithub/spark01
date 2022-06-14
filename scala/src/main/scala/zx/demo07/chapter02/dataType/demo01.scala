package main.scala.zx.demo07.chapter02.dataType

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:24
 * FileName: demo01
 * Description: chapter02.dataType
 *
 * 在Scala中数据类型都是对象，
 * 也就是说scala没有java中的原生类型(int, long, flout , double 等)
 */
object demo01 {

  def main(args: Array[String]): Unit = {
    //在scala中，一切皆为对象 ，比如(Int,Float,Char....)
    var num1: Int = 10
    println(num1.toString) //因为只有对象才能调方法

    var sex = '男'
    println(sex.toString) //因为只有对象才能调方法
  }

}
