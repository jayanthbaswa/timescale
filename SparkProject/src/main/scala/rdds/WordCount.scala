package rdds

import org.apache.spark.sql.SparkSession

object WordCount {
 def main(args: Array[String]){
  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Spark SQL basic example")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()


  val bookRDD=spark.sparkContext.textFile("///home//jay//Downloads//sample.txt", 4)

  //Regex to clean text
  val pat = """[^\w\s\$]"""
  val cleanBookRDD=bookRDD.map(line=>line.replaceAll(pat, ""))

  val wordsRDD=cleanBookRDD.flatMap(line=>line.split(" "))

  val wordMapRDD=wordsRDD.map(word=>(word,1))

  val wordCountMapRDD=wordMapRDD.reduceByKey(_+_)

  wordCountMapRDD.collect().foreach(println);
 }

}
