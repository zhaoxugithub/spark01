package main.scala.zx.demo07.chapter07

object MatchDemo01 {

  def main(args: Array[String]): Unit = {

    val tuple: (Double, Int, String, Char, Int) = (1.0, 88, "abc", 'a', 99)
    val iterator: Iterator[Any] = tuple.productIterator
    val value: Iterator[Unit] = iterator.map(
      (x) => {
        x match {
          case d: Double => println(s"$d....$x double")
          case c: Char => println(s"$c....$x char")
          case s: String => println(s"$s....$x string")
          case o: Int => println(s"$o....$x int")
          case w: Int if w > 50 => println("large than 50")
          case _ => println("over")
        }
      }
    )

    while (value.hasNext) {
      println(value.next())
    }


  }

}
