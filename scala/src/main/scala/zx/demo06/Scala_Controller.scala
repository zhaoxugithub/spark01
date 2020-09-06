package zx.demo06

import scala.collection.immutable

/**
 * scala 流控制 if for while
 */
object Scala_Controller {

  def main(args: Array[String]): Unit = {

    val a = 10
    if (a <= 10) {
      println(s"$a<=0")
    } else {
      println(s"$a>0")
    }

    println("----------------------------")
    var b = 0;
    while (b < 10) {
      println(b)
      b += 1
    }

    println("----------------------------")

    var num = 0
    for (i <- 1 to 9; j <- 1 to 9; if (j <= i)) {
      if (j < i) {
        print(s"${i} * ${j} =${i * j}\t")
      }
      if (i == j) println()
    }


    println("----------------------------")
    val indexedSeq: immutable.IndexedSeq[Int] = for (i <- 1 to 9) yield {
      var num = 19
      i + num
    }

    for (i <- indexedSeq) {
      println(i)
    }

  }
}
