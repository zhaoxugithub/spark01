package zx.demo03

/**
 * 匿名函数
 * 传递匿名函数至简原则：
 * （1）参数的类型可以省略，会根据形参进行自动的推导
 * （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
 * （3）匿名函数如果只有一行，则大括号也可以省略
 * （4）如果参数只出现一次，则参数省略且后面参数可以用_代替
 */
object TestFunction04 {
  def main(args: Array[String]): Unit = {

    // （1）定义一个函数：参数包含数据和逻辑函数
    def operation(arr: Array[Int], op: Int => Int) = {
      for (elem <- arr) yield op(elem)
    }

    // （2）定义逻辑函数
    def op(ele: Int): Int = {
      ele + 1
    }

    // （3）标准函数调用
    val arr = operation(Array(1, 2, 3, 4), op)
    println(arr.mkString(","))

    // （4）采用匿名函数
    val arr1 = operation(Array(1, 2, 3, 4), (ele: Int) => {
      ele + 1
    })
    println(arr1.mkString(","))

    // （4.1）参数的类型可以省略，会根据形参进行自动的推导;
    val arr2 = operation(Array(1, 2, 3, 4), (ele) => {
      ele + 1
    })
    println(arr2.mkString(","))

    // （4.2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
    val arr3 = operation(Array(1, 2, 3, 4), ele => {
      ele + 1
    })
    println(arr3.mkString(","))

    // (4.3) 匿名函数如果只有一行，则大括号也可以省略
    val arr4 = operation(Array(1, 2, 3, 4), ele => ele + 1)
    println(arr4.mkString(","))

    //（4.4）如果参数只出现一次，则参数省略且后面参数可以用_代替
    val arr5 = operation(Array(1, 2, 3, 4), _ + 1)
    println(arr5.mkString(","))
  }


}
