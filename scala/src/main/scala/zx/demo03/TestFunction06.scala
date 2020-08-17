package zx.demo03

/**
 * 模拟Map映射、Filter过滤、Reduce聚合
 */
object TestFunction06 {
  def main(args: Array[String]): Unit = {

    /**
     * map 映射
     */
    def map(arr: Array[Int], op: Int => Int) = {
      for (elm <- arr) yield op(elm)
    }

    val array = map(Array(1, 3, 4, 6, 7), x => x * x)
    println(array.mkString(","))


    /**
     * 自定义filter，有参数，且参数再后面只使用一次，则参数省略且后面参数用_表示
     */
    def filter(arr: Array[Int], op: Int => Boolean) = {
      for (elm <- arr) yield op(elm)
    }

    val array1 = filter(Array(1, 2, 3, 4, 5, 6, 7, 8), x => x % 2 == 1)
    println(array1.mkString(","))
  }

  /**
   * （3）reduce聚合。有多个参数，且每个参数再后面只使用一次，则参数省略且后面参数用_表示，第n个_代表第n个参数
   */
  def reduce(arr: Array[Int], op: (Int, Int) => Int) = {

    var init: Int = arr(0)

    for (elm <- 1 until arr.length) {
      init = op(init, elm)
    }
    init
  }

  println(reduce(Array(1, 2, 3, 4, 5, 6), (x, y) => x * y))

}
