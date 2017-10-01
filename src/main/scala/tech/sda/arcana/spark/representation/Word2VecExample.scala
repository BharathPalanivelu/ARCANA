package tech.sda.arcana.spark.representation
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
// $example on$
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
// $example off$
object Word2VecExample {
    
  def main(args: Array[String]): Unit = {

        val sparkSession = SparkSession.builder
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .appName("Word2VecExample")
      .getOrCreate()
    
    
    
    
 // val conf = new SparkConf().setAppName("Word2VecExample")
    val sc = sparkSession.sparkContext
    // $example on$
    val input = sc.textFile("src/main/resources/TL.txt").map(line => line.split(" ").toSeq)
    //val input = sc.textFile("/home/elievex/Resources/vectors_C.txt").map(line => line.split(" ").toSeq)

   //input.foreach { println }
   
    
    val word2vec = new Word2Vec()
    val model = word2vec.fit(input)

    val synonyms = model.findSynonyms("Ali", 5)

    for((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    // Save and load model
    model.save(sc, "file:///C:/Users/ali-d/Dropbox/Universities/University of Bonn/Master thesis/Arcana/ARCANA/src/main/resources/myModel")
   // val sameModel = Word2VecModel.load(sc, "myModelPath")
    // $example off$

        println("DONE")
    sc.stop()

}

}