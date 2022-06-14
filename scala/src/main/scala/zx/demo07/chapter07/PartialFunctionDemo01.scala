package main.scala.zx.demo07.chapter07

object PartialFunctionDemo01 {


  def main(args: Array[String]): Unit = {

    //偏函数，输入Any,输出String
    def xxx: PartialFunction[Any, String] = {
      case "hello" => "val is hello"
      case x: Int => s"$x ..is int"
      case _ => "none"
    }

    xxx(44)
    xxx("hello")
  }

}
