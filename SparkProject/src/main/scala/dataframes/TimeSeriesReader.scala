package dataframes

import java.util.Properties

import org.apache.spark.sql.{DataFrameReader, SparkSession}


object TimeSeriesReader {

  def main(args: Array[String]): Unit ={
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Spark JDBC basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val dataframerReader=spark.read
    dataframe(dataframerReader)

  }

  def dataframe(dataFrameReader:DataFrameReader){
    val jdbcDF = dataFrameReader
      .format("jdbc")
      .option("url", "jdbc:postgresql://localhost:5432/tutorial")
      .option("dbtable", "newbornsensex")
      .option("user", "postgres")
      .option("password", "java1234")
      .load()
    jdbcDF.show(100)

  }

}
