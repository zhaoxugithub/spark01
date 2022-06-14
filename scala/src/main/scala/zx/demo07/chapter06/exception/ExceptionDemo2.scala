package main.scala.zx.demo07.chapter06.exception

object ExceptionDemo2 {

  def main(args: Array[String]): Unit = {
    try {
      test()
    } catch {
      case ex: Exception => {
        println("捕获 抛出的异常")
      }
    } finally {
      println("继续执行。。。")
    }

  }

  def test(): Nothing = {
    throw new Exception("抛出异常")
  }
}
