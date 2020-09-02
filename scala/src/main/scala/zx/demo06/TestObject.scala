package zx.demo06

//约等于  static  单例对象
//static
//单例  new    scala的编译器很人性化   让你人少写了很多代码
object TestObject {

  private val name = "object:zhangsan"
  println("aaa")

  def main(args: Array[String]): Unit = {
    println("main--object")

    println("------------------TestA-------------------")

    var ta: TestA = new TestA("zhaoxu")
    ta.ddName = "hhhhhhhh"
    ta.mes()


    var ta1 = new TestA(11) //先执行完this("and") 构造
    ta1.mes()


    println("------------------TestB----------------")

    var tb = new TestB("zhaoxu1111") //name-----10
    tb.mes() //zhaoxu1111-----10
  }

  println("-------------------TestObject-----------")
  var to: TestObject = new TestObject()
  to.printMsg()

  println("bbb")
}

//类里，裸露的代码是默认构造中的。有默认构造
//个性化构造！！
//类名构造器中的参数就是类的成员属性，且默认是val类型，且默认是private
//只有在类名构造其中的参数可以设置成var，其他方法函数中的参数都是val类型的，且不允许设置成var类型
class TestA(var name: String) { //name 默认是是val，可以指定是var

  var ddName = name
  var age = 10

  /**
   * def this 这里的参数和class TestObject 的参数类型不能一样
   *
   * @param defAge
   */
  def this(defAge: Int) {
    this("and")
    this.age = defAge
  }

  println(this.ddName + "-----" + this.age)

  def mes(): Unit = {
    println(this.ddName + "-----" + this.age)
  }
}

class TestB {

  var ddName = "name"
  var age = 10

  /**
   * def this 这里的参数和class TestObject 的参数类型不能一样
   *
   * @param defAge
   */
  def this(defAge: Int) {
    this()
    this.age = defAge
  }

  def this(defName: String) {
    this()
    this.ddName = defName
  }

  println(this.ddName + "-----" + this.age)

  def mes(): Unit = {
    println(this.ddName + "-----" + this.age)
  }
}


class TestObject {
  var a: Int = 3

  //  private val value = new ooxx()

  println(s"ooxx....up$a....")

  def printMsg(): Unit = {
    println(s"sex: ${TestObject.name}")
  }

  println(s"ooxx....up${a + 4}")
}
