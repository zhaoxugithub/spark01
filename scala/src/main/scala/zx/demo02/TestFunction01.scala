package zx.demo02

object TestFunction01 {

  def main(args: Array[String]): Unit = {

    // （1）函数定义
    def f(arg: String): Unit = {
      println(arg)
    }

    // （2）函数调用
    // 函数名（参数）
    f("hello world")



    def sum(x: Int, y: Int): Int = {
      x + y
    }

  }



}
