package zx.demo01

import scala.io.StdIn

/**
 * 需要接收用户输入的数据，就可以使用键盘输入语句来获取
 */
object TestInput {


  def main(args: Array[String]): Unit = {

    // 1 输入姓名
    println("input name:")
    var name = StdIn.readLine()

    // 2 输入年龄
    println("input age:")
    var age = StdIn.readShort()

    // 3 输入薪水
    println("input sal:")
    var sal = StdIn.readDouble()

    // 4 打印
    println("name=" + name)
    println("age=" + age)
    println("sal=" + sal)
  }
}
