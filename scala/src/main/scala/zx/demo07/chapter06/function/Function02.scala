package main.scala.zx.demo07.chapter06.function

object Function02 {

  def main(args: Array[String]): Unit = {

    val tiger = new Tiger
    val tiger1 = test(10, tiger)
    println(tiger1.name)
  }

  def test(age: Int, tiger: Tiger): Tiger = {
    tiger.name = "test"
    tiger
  }
}

class Tiger {
  var name = "tiger"
}
