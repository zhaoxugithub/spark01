package zx.demo03

/**
 * 函数可以作为值进行传递
 */
object TestFunction01 {

  def main(args: Array[String]): Unit = {

    //（1）调用foo函数，把返回值给变量f
    val f1 = foo()
    println(f1)
    //2）在被调用函数foo后面加上_，相当于把函数foo当成一个整体，传递给变量f1
    val f2 = foo _
    println(f2)
    f2()
    foo();

  }

  def foo(): Int = {
    println("foo...")
    1
  }
}
