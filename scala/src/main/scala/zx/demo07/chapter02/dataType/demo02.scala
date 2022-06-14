package main.scala.zx.demo07.chapter02.dataType

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:34
 * FileName: demo02
 * Description: chapter02.dataType
 *
 * 2)Scala的整型常量, 在不手动指定类型的情况下, 默认为 Int 型，
 *   如果你给了一个特别大的数, int不足以容纳, 就会报错, 此时你需要把它变成long
 *   声明Long型常量, 须后加‘l’’或‘L’ [反编译看]
 *
 * 3)Scala的浮点类型可以表示一个小数, 比如 123.4f，7.8，0.12 等等
 *   Scala的浮点型常量默认为Double型，
 *   声明Float型常量，须后加‘f’或‘F’。
 *   ***通常情况下，应该使用Double型，因为它比Float型更精确(小数点后大致7位)
 *
 * 4)浮点型常量有两种表示形式
 * 十进制数形式：如：5.12       512.0f        .512   (必须有小数点）
 * 科学计数法形式:
 *   如：5.12e2 => 5.12 乘以 10的2次方
 *       5.12e-2 => 表示5.12 除以 10的2次方
 *
 * 5)通常情况下，应该使用Double型，因为它比Float型更精确(float精确小数点后大致7位)
 */
object demo02 {
  def main(args: Array[String]): Unit = {

    //2
    var a = 1232   //默认为 Int 型，

    //var b = 2147483648  //2147483647是int中的最大值, 这里比int最大值多1, 编译报错
    var b = 2147483648L  //声明Long型常量, 编译通过,

    //3
    //十进制计数法
    var f1 = 1.1   //默认为 double 型，
    var f2 = 1.2f  //声明Float型常量，须后加‘f’或‘F’。
    var f3 = .3f    //就是 0.3,可以打印确认,

    //科学计数法
    var f4 = 5.12e2 // 5.12 乘以 10的2次方,结果512.0,可以打印确认
    var f5 = 512e-2 // 512 除以 10的2次方,结果5.12,可以打印确认


    //5
    //(float精确小数点后大致7位)
    var test1 = 1.2345678912
    var test2 = 1.2345678912f

    println(test1)
    println(test2)

  }

}
