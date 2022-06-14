package main.scala.zx.demo07.chapter02.dataType

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:40
 * FileName: demo04
 * Description: chapter02.dataType
 *
 * 1)字符常量是用单引号 ‘ ’ 括起来的单个字符。
 * 例如：
 * var c1 = 'a‘
 * var c2 = '中‘
 * var c3 =  '9'
 *
 * 2) Scala 也允许使用转义字符‘\’来将其后的字符转变为特殊字符型常量。例如：var c3 = ‘\n’
 *    '\n'表示换行符
 *
 * 3)可以给Char赋一个整数(只判断是否范围越界)，输出时，会按照对应的unicode 字符输出
 *     ['\u0061' --> 97 --> a]
 *
 * 4) Char类型是可以进行运算的，相当于一个整数，因为它都对应有Unicode码.
 */
object demo04 {
  def main(args: Array[String]): Unit = {
    //1
    var c1 = 'a'
    var c2 = '中'
    var c3 =  '9'

    //2, 表示换行
    val cn = '\n'
    val ct = '\t'
    println("****"+ct + "****")
    print(cn)
    println("****"+ct + "****")

    //3.
    val num:Char = 97 //a
    println(num)

    val charMax = Char.MaxValue
    println(charMax.toInt)
  }
}
