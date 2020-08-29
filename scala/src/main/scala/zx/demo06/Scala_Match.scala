package zx.demo06

/**
 * 模式匹配
 */
object Scala_Match {

  def main(args: Array[String]): Unit = {

    val tuple: (Double, Int, String, Boolean, Int) = (1.0, 88, "abc", false, 44)

    val iterator: Iterator[Any] = tuple.productIterator

    val iterator1: Iterator[Unit] = iterator.map((x) => {
      x match {
        case 1 => println(s"${x}...is 1")
        case 88 => println(s"${x} ... is 88")
        case false => println(s"${x}... is false")
        case w: Int if w > 50 => println(s"${x}... is >50")
        case _ => println("没有匹配")
      }
    }
    )
    while (iterator1.hasNext) println(iterator1.next())
  }
}
