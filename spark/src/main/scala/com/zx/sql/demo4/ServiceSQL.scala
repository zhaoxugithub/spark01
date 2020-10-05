package com.zx.sql.demo4

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ServiceSQL {
  def main(args: Array[String]): Unit = {

    //创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_Demo")
    val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(conf)
      .getOrCreate()
    //    spark.sql("select * from gulivideo_user_ori").show()
    /*
    +------------------+------+-------+
    |          uploader|videos|friends|
    +------------------+------+-------+
    |   barelypolitical|   151|   5106|
    |            bonk65|    89|    144|
     */
    //    spark.sql("select * from gulivideo_ori").show()
    /*
    +-----------+----------------+----+-----------------+------+------+----+-------+--------+--------------------+
    |    videoId|        uploader| age|         category|length| views|rate|ratings|comments|           relatedId|
    +-----------+----------------+----+-----------------+------+------+----+-------+--------+--------------------+
    |1xbSFrHzFQ0|        Ireton06| 482|[Film, Animation]|   245|  9780| 4.0|      9|       6|[7vNsnB_qFGA, ndn...|
    |4VP4qSjDNQs|    themaster095| 718|[Film, Animation]|   528|    17| 0.0|      0|       0|[FJXOBibQLpc, EnP...|
     */

    //    --1.统计视频观看数Top10
    //    val sql01:String ="select * from gulivideo_ori order by views desc limit 10"
    //    spark.sql(sql01).show()

    //    --2.统计视频类别热度Top10
    //    val sql02:String ="select t1.category_name, count(1) as categoryNum\n" +
    //      "from (select category_name from gulivideo_ori lateral view explode(category) t_category as category_name) t1\n" +
    //      "group by t1.category_name\n" +
    //      "order by categoryNum desc\n" +
    //      "limit 10"
    //    spark.sql(sql02).show()

    //    --3.统计出视频观看数最高的20个视频的所属类别以及类别包含Top20视频的个数
    //    val sql03: String = "select t2.category_Name, concat_ws(\",\", collect_list(t2.videoId)) as videos_List, count(t2.category_Name) as amount\n" +
    //      "from (\n     " +
    //      "    select t.videoId, category_Name\n    " +
    //      "     from (\n           " +
    //      "       select videoId, category\n        " +
    //      "          from gulivideo_ori video\n           " +
    //      "       order by video.views desc\n         " +
    //      "         limit 20) t lateral view explode(t.category) categoryTable as category_Name\n  " +
    //      "   ) t2\n" +
    //      "group by category_Name\n" +
    //      "order by amount desc"
    //
    //    spark.sql(sql03).show()

    //    --4.统计视频观看数Top50所关联视频的所属类别排序
    var startTime = System.currentTimeMillis()
    var sql04: String = "select category_name, concat_ws(\"|\", collect_list(t2.videoId)), count(t2.videoId) as amount\nfrom (\n         select distinct video.videoId, category\n         from gulivideo_ori video\n                  left semi\n                  join (\n             select relateVideoID\n             from (select * from gulivideo_ori video order by video.views desc limit 50) t lateral view explode(relatedId) relateTable as relateVideoID\n         ) t on video.videoId = t.relateVideoID\n     ) t2 lateral view explode(t2.category) category_table as category_name\ngroup by category_name\norder by amount desc"
   // sql04 ="select category_name     as category,\n       count(t5.videoId) as hot\nfrom (\n         select t4.videoId,\n                category_name\n         from (\n                  select distinct(t2.videoId) as videoId,\n                                 t3.category\n                  from (\n                           select explode(relatedId) as videoId\n                           from (\n                                    select *\n                                    from gulivideo_ori\n                                    order by gulivideo_ori.views\n                                        desc\n                                    limit\n                                        50) t1) t2\n                           inner join\n                       gulivideo_ori t3 on t2.videoId = t3.videoId) t4 lateral view explode(category) t_catetory as category_name) t5\ngroup by category_name\norder by hot\n    desc"
    spark.sql(sql04).show()
    println((System.currentTimeMillis()-startTime)/1000)
    /*
      +-------------+-----------------------------------+------+
      |category_name|concat_ws(|, collect_list(videoId))|amount|
      +-------------+-----------------------------------+------+
      |        Music|               0iElNeTBJgg|Fiw3N...|    76|
      |       Comedy|               LzrLNznMOOs|RdXA1...|    45|
      |Entertainment|               B5QoRXxVhMw|1hLYV...|    36|
      |       People|               G2dE3AEGFhY|ERJjQ...|    16|
      |        Blogs|               G2dE3AEGFhY|ERJjQ...|    16|
      |         News|               fDOqL8IQuE8|rXCiA...|    13|
      |     Politics|               fDOqL8IQuE8|rXCiA...|    13|
      |       Sports|               4gkAExWe_cw|9qppA...|    12|
      |         Film|               ObNHHwB8OQg|AHFbp...|     9|
      |      Animals|               MYAo2LmS-bY|PXYRR...|     9|
      |    Animation|               ObNHHwB8OQg|AHFbp...|     9|
      |         Pets|               MYAo2LmS-bY|PXYRR...|     9|
      |      Gadgets|               1ayD0-Xujcs|fD7Ie...|     7|
      |        Games|               1ayD0-Xujcs|fD7Ie...|     7|
      |        Howto|               n7WJeqxuOfQ|jk6IL...|     6|
      |          DIY|               n7WJeqxuOfQ|jk6IL...|     6|
      |          UNA|               vEe3IeHZTOo|61e1h...|     3|
      |       Travel|               r_PGiZGasss|Ig3fw...|     2|
      |       Places|               r_PGiZGasss|Ig3fw...|     2|
      +-------------+-----------------------------------+------+
     */
    //    --5.统计每个类别中的视频热度Top10，以Music为例


    //    --6.统计每个类别中视频流量Top10，以Music为例


    //    --7.统计上传视频最多的用户Top10以及他们上传的观看次数在前20的视频


    //    --8.统计每个类别视频观看数Top10
  }
}
