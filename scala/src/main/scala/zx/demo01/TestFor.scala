package zx.demo01

import scala.language.postfixOps

object TestFor {

  def main(args: Array[String]): Unit = {
    /**
     * 前后闭合
     */
    for (i <- 0 to 5) {
      println("宋宋，告别海狗人参丸吧" + i)
    }

    /**
     * 前闭合后开
     */

    for (i <- 0 until 5 + 1) {
      println("宋宋，告别海狗人参丸吧" + i)
    }

    /**
     * 下面的代码和这个一样
     * for (i <- 1 to 3){
     * if (i != 2) {
     * print(i + " ")
     * }
     * }
     */
    for (i <- 1 to 3 if i != 2) {
      print(i + " ")
    }
    println()

    /**
     * 输出1到5中，不等于3的值
     */
    for (i <- 1 to 5 if i != 3) {
      println(i + "宋宋")
    }

    /**
     * 循环步长
     */
    for (i <- 1 to 10 by 2) {
      println("i=" + i)
    }

    /**
     * 嵌套循环
     */

    for (i <- 1 to 3; j <- 1 to 3) {
      println(" i =" + i + " j = " + j)
    }
    //这两段代码的功能一样
    for (i <- 1 to 3) {
      for (j <- 1 to 3) {
        println("i =" + i + " j=" + j)
      }
    }

    /**
     * 引入变量
     */
    println("---------")
    for (i <- 1 to 3; j = 4 - i) {
      println("i=" + i + " j=" + j)
    }
    //这两段代码的功能一养
    for (i <- 1 to 3) {
      var j = 4 - i
      println("i=" + i + " j=" + j)
    }

    /**
     * 循环返回值
     */
    val res = for (i <- 1 to 10) yield i
    //Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(res)

    /**
     * 需求：将原数据中所有值乘以2，并把数据返回到一个新的集合中。
     */
    var res2 = for (i <- 1 to 10) yield {
      i * 2
    }

    /**
     * 倒序打印
     */

    for (i <- 1 to 10 reverse) {
      println(i)
    }

  }
}
