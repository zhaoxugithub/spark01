package zx.demo01

/**
 * scala的注释和java 一样
 *
 * （1）声明变量时，类型可以省略，编译器自动推导，即类型推导
 * （2）类型确定后，就不能修改，说明Scala是强数据类型语言。
 * （3）变量声明时，必须要有初始值
 * （4）在声明/定义一个变量时，可以使用var或者val来修饰，var修饰的变量可改变，val修饰的变量不可改。
 *
 */
object HelloWord {

  def main(args: Array[String]): Unit = {
    System.out.println("hello scala")
    println("hello scala")

    var age = 10
    age = 30
    //（2）类型确定后，就不能修改，说明Scala是强数据类型语言。
    //        age = "tom" // 错误

    //（3）变量声明时，必须要有初始值
    //        var name //错误


    //（4）在声明/定义一个变量时，可以使用var或者val来修饰，var修饰的变量可改变，val修饰的变量不可改。
    var num1 = 10 // 可变

    val num2 = 20 // 不可变
    num1 = 30 // 正确
    //num2 = 100  //错误，因为num2是val修饰的
  }
}
