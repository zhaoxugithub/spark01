package zx.demo01

object TestDataType {
  def main(args: Array[String]): Unit = {

    /**
     *
     */
    // 正确
    var n1:Byte = 127
    var n2:Byte = -128

    // 错误
    // var n3:Byte = 128
    // var n4:Byte = -129

    /**
     *Scala的整型，默认为Int型，声明Long型，须后加‘l’或‘L’
     */
    var n5 = 10
    println(n5)

    var n6 = 9223372036854775807L
    println(n6)

    /**
     * Scala的浮点型常量默认为Double型，声明Float型常量，须后加‘f’或‘F’。
     */
    // 建议，在开发中需要高精度小数时，请选择Double
    var n7 = 2.2345678912f
    var n8 = 2.2345678912

    println("n7=" + n7)
    println("n8=" + n8)

    /**
     * （1）字符常量是用单引号 ' ' 括起来的单个字符。
     * （2）\t ：一个制表位，实现对齐的功能
     * （3）\n ：换行符
     * （4）\\ ：表示\
     * （5）\" ：表示"
     */
    //（1）字符常量是用单引号 ' ' 括起来的单个字符。
    var c1: Char = 'a'
    println("c1=" + c1)

    //（2）\t ：一个制表位，实现对齐的功能
    println("姓名\t年龄")

    //（3）\n ：换行符
    println("西门庆\n潘金莲")

    //（4）\\ ：表示\
    println("c:\\岛国\\avi")

    //（5）\" ：表示"
    println("同学们都说：\"大海哥最帅\"")
  }
}
