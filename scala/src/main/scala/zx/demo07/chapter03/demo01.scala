package main.scala.zx.demo07.chapter03

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:46
 * FileName: demo01
 * Description: chapter03
 */
object demo01 {

  def main(args: Array[String]): Unit = {

    //可以使用代码块赋值
    val b ={
      9
    }

    println(b)


    //scala中不支持三元

    val r1 : Int = 10 / 3  // 3 [不会进行四舍五入]
    println("r1=" + r1)

    val r2 : Double = 10 / 3 // a.先得到 3;  b. 3再转成3.0
    println("r2=" + r2)

    val r3 : Double = 10.0 / 3  //[3.3333333..]
    println("r3=" + r3 )
    println("r3=" + r3.formatted("%.3f") ) // 格式化输出， 保留小数点3位，并且进行四舍五入

  }

}
