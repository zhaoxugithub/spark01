package zx.demo01

object TestVar {
  def main(args: Array[String]): Unit = {
    // p1是var修饰的，p1的属性可以变，而且p1本身也可以变
    var p1 = new Person()
    p1.name = "zhangsan"
    p1 = null
    // p2是val修饰的，那么p2本身就不可变（即p2的内存地址不能变），但是，p2的属性是可以变，因为属性并没有用val修饰。
    val p2 = new Person
    p2.name = "lisi"
    // p2 = null // 错误的，因为p2是val修饰的
  }
}

class Person {
  var name: String = "jinlian"
}
