package zx.demo01

import scala.util.control.Breaks
import scala.util.control.Breaks._


/**
 * Scala内置控制结构特地去掉了break和continue，是为了更好的适应函数式编程，推荐使用函数式的风格解决break和continue的功能，而不是一个关键字。Scala中使用breakable控制结构来实现break和continue功能。
 */
object TestBreak {


  def main(args: Array[String]): Unit = {

    //1：采用异常的方式退出循环

    try {
      for (elem <- 1 to 10) {
        println(elem)
        if (elem == 5) throw new RuntimeException
      }
    } catch {
      case e =>
    }
    println("正常结束循环")

    //2.采用Scala自带的函数，退出循环
    Breaks.breakable(
      for (elem <- 1 to 10) {
        println(elem)
        if (elem == 5) Breaks.break()
      }
    )
    println("正常结束循环")

    //3.对break进行省略

    breakable {
      for (elem <- 1 to 10) {
        println(elem)
        if (elem == 5) break
      }
    }
    println("正常结束循环")
  }

}
