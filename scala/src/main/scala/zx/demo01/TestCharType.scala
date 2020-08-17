package zx.demo01

object TestCharType {
  def main(args: Array[String]): Unit = {

    var name: String = "jinlian"
    var age: Int = 18

    //（1）字符串，通过+号连接
    println(name + " " + age)

    //（2）printf用法字符串，通过%传值。
    printf("name=%s age=%d\n", name, age)

    //（3）字符串，通过$引用
    val s =
      """
        |select
        |    name,
        |    age
        |from user
        |where name="zhangsan"
            """.stripMargin

    println(s)

    val s1 =
      s"""
         |select
         |    name,
         |    age
         |from user
         |where name="$name" and age=${age + 2}
            """.stripMargin
    println(s1)
    val s2 = s"name=$name"
    println(s2)


    var username = "zhaoxu"
    var age2 = "24"
    var sex = "男"

    var describe =
      s"""
         |this is a boy
         |name is "$username"
         |and age is "$age2"
         |and sex is "$sex"
         |""".stripMargin

    println(describe)
  }
}
