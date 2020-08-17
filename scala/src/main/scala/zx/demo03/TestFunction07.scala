package zx.demo03

/**
 * 闭包：函数式编程的标配
 * 1）说明
 * 闭包：如果一个函数，访问到了它的外部（局部）变量的值，那么这个函数和他所处的环境，称为闭包
 * 函数柯里化：把一个参数列表的多个参数，变成多个参数列表。
 */
object TestFunction07 {

  def main(args: Array[String]): Unit = {
    var a: Int = 10

    def f1() = {
      def f2(b: Int) = {
        a + b
      }

      f2 _
    }

    // 在调用时，f1函数执行完毕后，局部变量a应该随着栈空间释放掉
    val f = f1()

    // 但是在此处，变量a其实并没有释放，而是包含在了f2函数的内部，形成了闭合的效果
    println(f(3))


    println(f1()(3))

    // 函数柯里化，其实就是将复杂的参数逻辑变得简单化,函数柯里化一定存在闭包
    def f3()(b: Int) = {
      a + b
    }
    println(f3()(3))
  }
}
