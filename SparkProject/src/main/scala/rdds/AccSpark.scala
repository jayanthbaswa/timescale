package rdds

import org.apache.spark.sql.SparkSession

object AccSpark {
  def main(args: Array[String]): Unit ={
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val sc =spark.sparkContext

  }
}
