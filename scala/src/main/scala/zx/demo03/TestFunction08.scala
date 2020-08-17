package zx.demo03

/**
 * 值调用：把计算后的值传递过去
 */
object TestFunction08 {


  def main(args: Array[String]): Unit = {
    def f = () => {
      println("f...")
      10
    }

    foo(f())

  }

  def foo(a: Int): Unit = {
    println(a)
    println(a)
  }
}
