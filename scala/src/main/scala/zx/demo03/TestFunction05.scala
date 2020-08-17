package zx.demo03

object TestFunction05 {
  def main(args: Array[String]): Unit = {

    def calculator(a: Int, b: Int, op: (Int, Int) => Int): Int = {
      op(a, b)
    }

    def op(x: Int, y: Int): Int = {
      x + y
    }

    //传递一个函数
    calculator(2, 3, op)

    //如果只有一行，则大括号也可以省略
    println(calculator(2, 3, (x: Int, y: Int) => {
      x + y
    }))
    //如果只有一行，则大括号也可以省略
    println(calculator(2, 3, (x: Int, y: Int) => x + y))

    //参数的类型可以省略，会根据形参进行自动的推导;
    println(calculator(2, 3, (x, y) => x + y))

    // （4）如果参数只出现一次，则参数省略且后面参数可以用_代替
    println(calculator(2, 3, _ + _))
  }
}
