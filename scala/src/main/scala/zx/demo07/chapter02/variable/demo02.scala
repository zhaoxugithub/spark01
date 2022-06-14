package main.scala.zx.demo07.chapter02.variable

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:11
 * FileName: demo02
 * Description: chapter02
 */
object demo02 {
  def main(args: Array[String]): Unit = {
    //创建一个对象
    //1. dog 是val 修饰的，即dog对象是不可变，即dog的引用不可变
    //2. 但是 dog.name是 var, 他的属性可变
    val dog = new Dog
    //    dog = null //报错
    dog.name = "大黄狗"
    dog.name = "大黄狗2"
    //    var 修饰的对象引用可以改变
    var dog2 = new Dog
    dog2 = null //不报错
  }
}

class Dog {
  var name = "tom"
}
