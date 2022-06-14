package main.scala.zx.demo07.chapter06.clasz

object ConstructDemo1 {
  def main(args: Array[String]): Unit = {
    val person = new Person1("jack", 20)
    println(person)

    //如果主构造器无参数，小括号可省略，构建对象时调用的构造方法的小括号也可以省略
    val personWithout = new PersonWithout
    println(personWithout.name)
    println(personWithout.age)
  }
}

//Scala构造器作用是完成对新对象的初始化，构造器没有返回值。
class Person1(pName: String, pAge: Int) { //1.主构造器, 直接放置于类名之后
  var name: String = pName
  var age: Int = pAge

  override def toString: String = {
    "name=" + this.name + ", age=" + this.age
  }
}

//如果主构造器无参数，小括号可省略，构建对象时调用的构造方法的小括号也可以省略
class PersonWithout {
  var name: String = _
  var age: Int = _

  //这种不是构造方法
  def PersonWithout(name: String, age: Int): Unit = {
    this.name = name
    this.age = age
  }

  def this(name:String,age:Int){
    //需要先调用无参构造
    this()
    this.name = name
    this.age = age
  }
}


//如果想让主构造器变成私有的，可以在()之前加上private，
// 这样用户不能直接通过主构造器来构造对象了
class PersonPrivate private() {

}

