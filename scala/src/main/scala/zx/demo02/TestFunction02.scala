package zx.demo02

/**
 * （1）为完成某一功能的程序语句的集合，称为函数。
 * （2）类中的函数称之方法。
 *
 * （1）Scala语言可以在任何的语法结构中声明任何的语法
 * （2）函数没有重载和重写的概念；方法可以进行重载和重写
 * （3）Scala中函数可以嵌套定义
 */
object TestFunction02 {

  //main
  def main(): Unit = {

  }

  def main(args: Array[String]): Unit = {
    // （1）Scala语言可以在任何的语法结构中声明任何的语法
    import java.util.Date
    new Date()

    // (2)函数没有重载和重写的概念，程序报错
    def test(): Unit ={
      println("无参，无返回值")
    }
    test()
//
//    def test(name:String):Unit={
//      println()
//    }

    //（3）Scala中函数可以嵌套定义
    def test2(): Unit ={

      def test3(name:String):Unit={
        println("函数可以嵌套定义")
      }
    }
  }
}
