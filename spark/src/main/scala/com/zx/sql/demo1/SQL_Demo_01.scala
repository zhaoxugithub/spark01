package com.zx.sql.demo1

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * df 与RDD装换
 */
object SQL_Demo_01 {


  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")


    //sparkSession 封装了HiveContext和SQLContext
    val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //RDD=>DataFrame=>DataSet转换需要引入隐式转换规则，否则无法转换
    //spark不是包名，是上下文环境对象名
    import sparkSession.implicits._

    //csv   format   jdbc   json   load   option   options   orc   parquet   schema   table   text   textFile
    val df: DataFrame = sparkSession.read.json("spark/src/main/data/jsondata/person.json")
    //查看所有的表信息
    df.show()

    println("----------------SQL 风格语法-------------------------")

    //SQL 风格语法
    //对DataFrame创建一个普通临时表,有效期只是在session范围内
    df.createOrReplaceTempView("person")

    sparkSession.sql("select * from person where name ='zx'").show()
    sparkSession.sql("select avg(age) from person").show()

    //如果是用临时普通表就会爆出如下异常：Exception in thread "main" org.apache.spark.sql.AnalysisException: Table or view not found: person; line 1 pos 14;
    //'Project [*]
    //+- 'UnresolvedRelation [person]
    //sparkSession.newSession().sql("select * from person").show()

    //创建一个全局临时表：有效期在应用范围表
    df.createGlobalTempView("person1")

    sparkSession.sql("select * from global_temp.person1").show()
    sparkSession.newSession().sql("select * from global_temp.person1").show()

    println("--------------DSL 风格语法-----------------------------")
    //DSL 风格语法不需要创建临时表
    df.select("*").show()
    df.select("name").show()


    //涉及到运算的时候，每列都必须要使用$,或者采用引号表达式：单引号+字段名
    df.select($"name", $"age" + 1).show()
    df.select('name, 'age + 1 as "newAge").show()
    df.groupBy("age").count().show()


    println("---------------创建DataSet---------------------------------")
    //class之前添加case可以自动生成equal、hashcode、toString、copy方法和他的伴生对象，并且为伴生对象生成apply、unapply方法。例

    //Seq是一个特征, 代表可以保证不变的索引序列
    val value1: Dataset[Person] = Seq(Person("zhangsan", 2)).toDS()
    value1.show()

    //2）使用基本类型的序列创建DataSet
    //在实际使用的时候，很少用到把序列转换成DataSet，更多的是通过RDD 来得到DataSet
    val value2: Dataset[Int] = Seq(1, 2, 3, 4, 5, 6).toDS()
    value2.show()


    println("---------------------RDD转成DataSet---------------------------------")

    val value3: RDD[User] = sparkSession.sparkContext.makeRDD(List(("zhangsan", 20), ("lisi", 30))).map(t => User(t._1, t._2))
    val value4: Dataset[User] = value3.toDS()
    value4.show()

    println("---------------------DataSet转成RDD---------------------------------")
    val rdd1: RDD[User] = value4.rdd
    rdd1.foreach(println)

    println("---------------DataFrame 与RDD转换---------------------------------")
    //dataRDD 转成dataFrame
    val value: RDD[(Int, String, Int)] = sparkSession.sparkContext.makeRDD(List((1, "qiaofeng", 30), (2, "xuzhu", 28), (3, "duanyu", 20)))
    val dataFrame: DataFrame = value.toDF("id", "name", "age")
    dataFrame.show()

    //类比dataset


    println("---------------DataFrame 与DataSet转换---------------------------------")
    val dataFrame2: DataFrame = sparkSession.sparkContext.makeRDD(List(("zhangsan", 30), ("lisi", 40))).toDF("name", "age")
    val ds1: Dataset[User] = dataFrame2.as[User]
    ds1.show()

    val frame: DataFrame = ds1.toDF()

    //释放资源
    sparkSession.stop()

  }
}
case class Person(name: String, age: Int)
case class User(name: String, age: Int)

