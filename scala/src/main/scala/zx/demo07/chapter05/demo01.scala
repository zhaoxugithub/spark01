package main.scala.zx.demo07.chapter05

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 18:34
 * FileName: demo01
 * Description: chapter05
 */
object demo01 {

  def main(args: Array[String]): Unit = {
    val i = sum(1, 2)
    println(i)

    val unit = sumWithoutReturn(2, 3)
    println(unit)

  }

  def sum(n1: Int, n2: Int): Int = {
    n1 + n2
  }

  def sumWithoutReturn(n1: Int, n2: Int): Unit = {
    n1 + n2
  }

}
