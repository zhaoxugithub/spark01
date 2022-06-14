package main.scala.zx.demo07.chapter04

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:52
 * FileName: demo01
 * Description: chapter04
 */
object demo01 {

  def main(args: Array[String]): Unit = {

    var list = List(1,2,3,4)
    list = list.reverse

    println(list)
    println(list.reverse)

    for (i <- 1 to 5){
      println("to:"+i);
    }

    // 循环守卫的方式====
    for(i <- 1 to 3 if i!=2){
      println("守卫:"+i)
    }

    // 普通的方式
    for (i <- 1 to 3){
      if (i!=2){
        println("普通"+i)
      }
    }

    for (i <- 1 to 10;j = i-1 if j<2){
      println(i)
    }

    //嵌套循环
    println("嵌套循环 ======")
    for(i <- 1 to 5;j <- 1 to 5){
      println(s"i=$i,j=$j")
    }

    //复杂的业务时,推荐使用这个
    println("普通方式=================")
    for (i <- 1 to 3) {
      for (j <-1 to 3) {
        println(" i =" + i + " j = " + j)
      }
    }

  }

}
