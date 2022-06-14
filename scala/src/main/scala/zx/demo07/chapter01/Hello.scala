package main.scala.zx.demo07.chapter01

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 14:38
 * FileName: Hello
 * Description: chapter01
 */
object Hello {
  def main(args: Array[String]): Unit = {
    println("hello world")

    val name: String = "hello world"
    val age: Int = 30
    val height: Double = 3.444

    //第一种打印方式
    println("name=" + name + ",age=" + age + ",height=" + height)

    //第二种打印方式
    println(s"name=$name,age=$age,height=$height")

    //第三种打印方式
    printf("name=%s,age=%d,height=%f\n", name, age, height)

    //第三种打印方式
    printf("name=%s,age=%d,height=%.2f\n", name, age, height)
  }
}
