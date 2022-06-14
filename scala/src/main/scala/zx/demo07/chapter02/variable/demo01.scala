package main.scala.zx.demo07.chapter02.variable

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:09
 * FileName: demo01
 * Description: chapter02
 * val修饰的变量在编译后，等同于加上final，
 * 通过反编译看下底层代码。
 */
object demo01 {
  def main(args: Array[String]): Unit = {
    var name: String = "smith"
    var age: Int = 10
    var gender: Char = '男'
    var isPass: Boolean = true
    var sal: Float = 8907.4f

    var weight = 130.0 //就使用到了类型推断
    weight.isInstanceOf[Double] // true

    //类型确定后，就不能修改，说明Scala 是强数据类型语言
    //    age = "jack" //报错

    var lover = "小红"
    lover = "小黑" // 编译通过, var 是可以变的。

    val girlFriend = "小白"
    //girlFriend = "小黄" //编译报错,val 是不可变的变量
  }

}
