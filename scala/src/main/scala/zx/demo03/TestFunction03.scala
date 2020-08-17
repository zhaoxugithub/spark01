package zx.demo03

/**
 * 函数可以作为函数返回值返回
 */
object TestFunction03 {

  def main(args: Array[String]): Unit = {
    def f1() = {
      def f2() = {

      }

       f2 _
    }

    val f = f1()
    // 因为f1函数的返回值依然为函数，所以可以变量f可以作为函数继续调用
    f()
    // 上面的代码可以简化为
    f1()()
  }

}
