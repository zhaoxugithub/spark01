package com.zx.spark.demo07

import java.util.Properties

import ch.hsr.geohash.GeoHash
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object GeohashDict {

  def main(args: Array[String]): Unit = {

    //创建Spark配置文件对象
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local[*]")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    import spark.implicits._
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "root")

    val df: DataFrame = spark.read.jdbc("jdbc:mysql://zhaoxu.aliyun.com:3306/test", "t_md_areas", properties)

    /**
     * `ID` int(11) NOT NULL COMMENT '???',
     * `AREANAME` text NOT NULL COMMENT '???',
     * `PARENTID` int(11) NOT NULL COMMENT '?ϼ????',
     * `SHORTNAME` text COMMENT '???Ƽ',
     * `LEVEL` int(11) NOT NULL DEFAULT '0' COMMENT '???????',
     * `FLAG` int(11) DEFAULT '1' COMMENT '?߼?״̬??1??ʹ?ã?0????ʹ?ã?',
     * `WGS84_LNG` double DEFAULT NULL COMMENT '????????ϵWGS84,???',
     * `WGS84_LAT` double DEFAULT NULL COMMENT '????????ϵWGS84,γ?',
     * `GCJ02_LNG` double DEFAULT NULL COMMENT '????????ϵ,????(?ߵ¡???Ѷ??ͼ?顢??????ͼ)',
     * `GCJ02_LAT` double DEFAULT NULL COMMENT '????????ϵ,γ??(?ߵ¡???Ѷ??ͼ?顢??????ͼ)',
     * `BD09_LNG` double DEFAULT NULL COMMENT '?ٶ?????ϵ,???',
     * `BD09_LAT` double DEFAULT NULL COMMENT '?ٶ?????ϵ,γ?',
     */

    df.createOrReplaceTempView("t_md_areas")
    val frame: DataFrame = spark.sql("SELECT\n\tta.AREANAME AS province,\n\tta2.AREANAME AS city,\n\tta3.AREANAME AS district,\n\tta3.BD09_LAT,\n\tta3.BD09_LNG \nFROM\n\tt_md_areas ta\n\tINNER JOIN t_md_areas ta2 ON ta2.PARENTID = ta.ID \n\tAND ta2.LEVEL = 2 \n\tAND ta.PARENTID = 0 \n\tAND ta.`LEVEL` = 1\n\tINNER JOIN t_md_areas ta3 ON ta3.PARENTID = ta2.ID \n\tAND ta3.LEVEL = 3")
    val result: DataFrame = frame.map(row => {
      val lng: Double = row.getAs[Double]("BD09_LNG")
      val lat: Double = row.getAs[Double]("BD09_LAT")
      val province: String = row.getAs[String]("province")
      val city: String = row.getAs[String]("city")
      val district: String = row.getAs[String]("district")

      val geo: String = GeoHash.geoHashStringWithCharacterPrecision(lat, lng, 5)
      (province, city, district, geo)
    }).toDF("province", "city", "district", "geo")

    result.write.json("spark/src/main/data/geo")

    spark.close()
  }
}
