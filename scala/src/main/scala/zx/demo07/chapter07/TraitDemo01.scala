package main.scala.zx.demo07.chapter07

//Trait定义可以被多继承

trait ddd {

}

trait eee {

}

//加了trait就可以多继承
object TraitDemo01 extends ddd with eee {


  def main(args: Array[String]): Unit = {
    val me = new MM("me", 20)
    val me1 = new MM("me", 20)
    //如果加了case 就会相同
    println(me.equals(me1))
    println(me == me1)
  }
}


case class MM(name: String, age: Int) {

}