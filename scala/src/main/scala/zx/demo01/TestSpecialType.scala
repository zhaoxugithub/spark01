package zx.demo01

/**
 * Unit	表示无值，和其他语言中void等同。用作不返回任何结果的方法的结果类型。Unit只有一个实例值，写成()。
 * Null	null , Null 类型只有一个实例值null
 * Nothing	Nothing类型在Scala的类层级最低端；它是任何其他类型的子类型。
 * 当一个函数，我们确定没有正常的返回值，可以用Nothing来指定返回类型，这样有一个好处，就是我们可以把返回的值（异常）赋给其它的函数或者变量（兼容性）
 */
object TestSpecialType {

  def main(args: Array[String]): Unit = {
    /**
     * （1）Unit类型用来标识过程，也就是没有明确返回值的函数。
     * 由此可见，Unit类似于Java里的void。Unit只有一个实例——( )，这个实例也没有实质意义
     */

    def sayOk: Unit = { // unit表示没有返回值，即void

    }

    println(sayOk)

    /**
     * （2）Null类只有一个实例对象，Null类似于Java中的null引用。Null可以赋值给任意引用类型（AnyRef），但是不能赋值给值类型（AnyVal）
     */
    //null可以赋值给任意引用类型（AnyRef），但是不能赋值给值类型（AnyVal）
    var cat = new Cat();
    cat = null // 正确

//    var n1: Int = null // 错误
//    println("n1:" + n1)


    /**
     * Nothing，可以作为没有正常返回值的方法的返回类型，非常直观的告诉你这个方法不会正常返回，而且由于Nothing是其他任意类型的子类，他还能跟要求返回值的方法兼容。
     */
    def test() : Nothing={
      throw new Exception()
    }
    test
  }

}

class Cat {

}
