package main.scala.zx.demo07.chapter02.dataType

/**
 * Copyright (C), 2017-2022, 赵旭
 * Author: 11931
 * Date: 2022/2/21 15:38
 * FileName: demo03
 * Description: chapter02.dataType
 * 部分值类型的数据类型的范围
 *
 * byte:   1字节,8位,范围: (-2的7次方) ~ (+2的7次方-1)
 * short:  2字节,16位,范围:依次类推
 * int:    4字节,32位,范围:依次类推
 * long:   8字节,64位,范围:依次类推
 * float:  4字节,32位,范围:依次类推
 * double: 8字节,64位,范围:依次类推
 *
 * char:   2字节,16位,无符号Unicode字符
 * boolean: true/false
 * Unit:   ()
 * Null:   null
 *
 * 一个字节=8位
 */
object demo03 {

  def main(args: Array[String]): Unit = {
    //使用现成的方法获取
    val byteMin: Byte = Byte.MinValue
    val byteMax: Byte = Byte.MaxValue
    printf("min=%d,max=%d\n",byteMin,byteMax)

    val intMin: Int = Int.MinValue
    val intMax: Int = Int.MaxValue
    printf("min=%d,max=%d\n",intMin,intMax)
  }

}
