package tech.sda.arcana.spark.representation
import org.apache.spark.rdd.RDD
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import scala.collection.mutable.ListBuffer
import com.intel.analytics.bigdl.tensor.Tensor
import shapeless._0
import tech.sda.arcana.spark.classification.cnn.Core


object sentenceToTensor {
  //Transfer one question one sentence to multi-represential tensor
  
   val vectorLength:Int=50
  val sentenceWordCount:Int=4
  
  
      //return each line of the glov representation as follows:
      // { String(word),Array[string](the vector representatiparsedQuestionson) }
          def parseLine(line:String)={
          //if(!line.isEmpty()){
                val fields = line.split(" ")
                val word = fields(0).toString()
                val representation:Array[String]=new Array[String](vectorLength)
                  for(x<-0 to vectorLength-1){
                    representation(x)=fields(x+1)
                  }
         // }
          (word, representation)
        }
      
      //return each question of the text file as an array of words with an mumber in the
      //beginning to identify the order of this question
        def parseQuestion(line:String)={
          val words=line.split(" ")
          //build the return element which looks as follows:
          //RDD[word it self inside the sentence,(number of the sentence,number of the element)]
          val orderedWords=words.drop(1).zipWithIndex.map{case(line,i) => (line,(words(0).toLong,i))}
          (orderedWords)
        }
        
        
        
        //////////////////////////////************************************************
          def test(sentence:(Long, Iterable[((Long, Int), Array[String])]))={
            val tensor=Tensor[Float](sentenceWordCount,vectorLength)
            val tensorStorage= tensor.storage.fill(0, 1, sentenceWordCount*vectorLength)
            var vec=sentence._2.toSeq.sortBy(x=>x._1._2)
           
            var storageCounter:Int=0
            vec.last._2.foreach{x=>
                                tensorStorage(storageCounter)=x.toFloat
                                storageCounter=storageCounter+1
                                }
            vec=vec.init
            
            for(i <-0 to sentenceWordCount-1){
              for(j <-0 to vectorLength-1){
                
              }
            }
            
            (tensor)
        }
        
        
        //////////////////////////////************************************************
        
  
    def main(args:Array[String]){
      
           
          // Set the log level to only print errors
          Logger.getLogger("org").setLevel(Level.ERROR)
          
          // Create a SparkContext using every core of the local machine
          // val sc = new SparkContext("local[*]", "MinTemperatures")
          val s=new Core("model","train","label","test")
          val sc=s.initialize()
          
          
          // Read each line of input data
          val lines = sc.textFile("/home/mhd/Desktop/ARCANA Resources/glove.6B/glove.6B.50d.txt")
          // Read the questions
          val questions = sc.textFile("/home/mhd/Desktop/Data Set/Negative_Questions.txt")
          //Give each question an Id or an order
          val orderedQuestions=questions.zipWithIndex().map{case(line,i) => i.toString+" "+line}
          val parsedLines = lines.map(parseLine)
          //Convert each question to array of words (the output Array[(String, (String, Int))])
          //val parsedQuestions= orderedQuestions.map(parseQuestion)
          //for the joining sake I used flat map to discard the array (the output (String, (String, Int)))
          val parsedQuestions=orderedQuestions.flatMap(parseQuestion)
          
          //the result looks as follows:
          // RDD[(String, ((String        , Int)     ,       Array[String]))]
          // RDD[(word,   ((sentence order,word order,vector representation))]
          //for(i<-result)
          //      i._1,   ((i._2._1._1    ,i._2._1._2, i._2._2             ))
          val result= parsedQuestions.join(parsedLines)
          
          //////////////////////////////************************************************
          
          // try to simplify the structure 
          val resultTest= result.map{case(a,b)=>b}
          
          
          val groupedResultTest=resultTest.groupBy(x=>x._1._1)
          
          
          val sss=Tensor[Float](sentenceWordCount,vectorLength)
          val xxx:Array[Int]=new Array[Int](vectorLength)
          val xx=sss.apply(xxx)
          
          
          
          //////////////////////////////************************************************
          
          val groupedResult=result.groupBy(x=>x._2._1._1)
          //sentenceVectorRepresentation ordered
          val sentenceVecRep=groupedResult.map{ case(a,b)=>(b.toSeq).sortBy(_._2._1._2)  }
          
          val tt=Tensor[Float](1,4,5)
          
          val sentenceVecRepT=sentenceVecRep.map{ case(a)=> a.foreach(_._2._2)}
          
          val ss=Seq(1,2,3,4,5,6,7,9)
          ss(8)
          
          
          val tensor=Tensor[Float](10,10)
         
          /*
          val sampleRDD = sentenceVecRep.map {case (input: Array[Array[Float]], label: Float) =>
            Sample(
              featureTensor = Tensor(input.flatten, Array(sequenceLen, embeddingDim))
                .transpose(1, 2).contiguous(),
              labelTensor = Tensor(Array(label), Array(1)))
          }
          */
          
         // val finalResult=groupedResult.sortBy(x=>x._2._1._2, false)
          //val result1=result.collect()
          val result1=groupedResult.collect()
          result1.foreach(println)
          //http://docs.scala-lang.org/overviews/collections/iterators.html
          //Iterable[   (   String, ((String, Int), Array[String])  )    ]
          val it = Iterator(("Mohamad",(("m",1),(1,2,3))))
          
          
          val test=it.toArray
          
          
          val test1=(it.toSeq).sortBy(_._2._1._1)
          
          
          val x=Tensor[Float](4,5,6)
          x.apply1(i => i+1)
          
         // val x=Table[Float]()          
          /*
          val oo:Int=0
          x.apply(oo => oo+1)
          */
          /*
          x:apply(function()
            i = i + 1
            return i
          end)
          */
          
          //val vectorizedRdd = result1.map {case (string, varr) => varr. }
          
          /*
          for(i <- result1)
          {
            //the second loop for watching the results without using flatmap
            //for(j<- i){
              print("The world: ")
              println(i._1)
              print("Sentence order: ")
              println(i._2._1._1)
              print("Word order: ")
              println(i._2._1._2)
              print("vector representation: ")
              for(j<- i._2._2)
              print(j+",")
              println()
           // }
          }
          ////////////////////////////////////////////
           val tensor=Tensor[Float](sentenceWordCount,vectorLength)
           val tensorStorage= tensor.storage
           */
          
          
          
   /*       
          //https://bigdl-project.github.io/master/#UserGuide/examples/
          //example about RDD then converting to tensor
         
                      
            //Build the tensor which represent the question
            //Achtung when changing this code to take any quetion length you
            //should intialize the tensor with zeros in the begining
            val tensor=Tensor[Float](sentenceWordCount,vectorLength)
            val tensorStorage= tensor.storage()      
            //the length of each row in the tensor
            var temp:Array[String] = new Array[String](vectorLength)
            
            //to fill the tensor
            //define the tensor index
            var tI=0
            for(i <- 0 to senRep.size){
              temp=senRep(i)
              for(j <- 0 to temp.size-1){
                tensorStorage(tI)=temp(j).toFloat
                tI=tI+1
              }
            }
            
            print(tensor)
*/
    }
}