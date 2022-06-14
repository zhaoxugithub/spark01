package main.scala.zx.demo07.chapter04

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 16:09
 * FileName: demo02
 * Description: chapter04
 *
 * 循环返回值
 * 将遍历过程中处理的结果, 放到一个Vector集合中(indexedSeq[T])
 */
object demo02{
  def main(args: Array[String]): Unit = {

    val res = for(i <- 1 to 10 if i %2 ==0) yield i
    println(res)

    val res2 = for (i <- 1 to 10) yield {
      if(i%2==0){
        "偶数"
      }else{
        i
      }
    }
    println(res2)
    //循环控制步长
    // 步长为3
    for (i <- 1 to 10 if i%3==1){
      print(s"$i ")
    }
    println()
    println("------")
    //[1,9]  不包括10
    for (i <- Range(1,10,2)){
      print(s"$i ")
    }
  }
}
