package zx.demo06

/**
 * scala 偏函数
 */
object Scala_PartialFunction {
  def main(args: Array[String]): Unit = {

    def xxx: PartialFunction[Any, String] = {

      case "hello" => "cal is hello"
      case x: Int => s"${x} is number"
      case _ => "none"
    }

    println(xxx("hello"))
    println(xxx)
    println(xxx(11))
    println(xxx("xxx"))

  }
}
