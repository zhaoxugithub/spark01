package main.scala.zx.demo07.chapter07

object ImplicitDemo03 {


  def main(args: Array[String]): Unit = {
    test03()
  }

  def test01(): Unit = {
    implicit val sss = "lisi"

    def ooxx(implicit name: String): Unit = {
      println(name)
    }

    ooxx("name...")
    ooxx
  }

  def test02(): Unit = {
    implicit val sss: String = "lisi"
    implicit val ddd: Int = 88

    def ooxx(implicit name: String, age: Int): Unit = {
      println(name + " " + age)
    }

    ooxx("name...", 20)
    ooxx
    ooxx("dsad", 222)
  }

  def test03(): Unit = {
    implicit val sss: String = "lisi"
    implicit val ddd: Int = 88

    def ooxx(age: Int)(implicit name: String): Unit = {
      println(name + " " + age)
    }

    ooxx(20)
    ooxx(30)("name")
  }
}
