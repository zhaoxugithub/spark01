package zx.demo02

object TestFunction04 {
  def main(args: Array[String]): Unit = {

    // （1）可变参数
    def test(s: String*): Unit = {
      println(s)
    }

    // 有输入参数：输出 Array
    test("Hello", "Scala")

    // 无输入参数：输出List()
    test()

    // (2)如果参数列表中存在多个参数，那么可变参数一般放置在最后
    def test2(name: String, s: String*): Unit = {
      println(name + "," + s)
    }

    test2("jinlian", "dalang","sdsdas")

    // (3)参数默认值
    def test3(name: String, age: Int = 30): Unit = {
      println(s"$name, $age")
    }

    // 如果参数传递了值，那么会覆盖默认值
    test3("jinlian", 20)

    // 如果参数有默认值，在调用的时候，可以省略这个参数
    test3("dalang")

    // 一般情况下，将有默认值的参数放置在参数列表的后面
    def test4(sex: String = "男", name: String): Unit = {
      println(s"$name, $sex")
    }
    // Scala函数中参数传递是，从左到右
    //test4("wusong")

    //（4）带名参数
    test4(name = "ximenqing")
  }
}
