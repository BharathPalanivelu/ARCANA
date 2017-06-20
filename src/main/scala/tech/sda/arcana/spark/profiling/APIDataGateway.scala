package tech.sda.arcana.spark.profiling

import java.io._
import scala.util.parsing.json._
import scala.collection.mutable
import org.apache.spark.sql.SparkSession
import net.sansa_stack.rdf.spark.io.NTripleReader
import net.sansa_stack.rdf.spark.model.{JenaSparkRDDOps, TripleRDD}
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession;
import java.net.{URI => JavaURI}
import scala.collection.mutable
import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)

/*
 * My Keys -> merriam webster      
      Key (Dictionary):7a94d88d-4647-416b-bb3c-74bed96d4188
      Key (Thesaurus): e8c94890-746e-4df8-98da-08cdf5d84e53
 * 
 * My Keys -> Big Huge Thesaurus
 *    Key is fe297721a04ca9641ae3a5b1ae3033a2 
 *    
 * My Keys -> uclassify.com
 *    Key is L5ZjO3PO2YlO
 */


object APIData {
  
  // 1st way to do it
  @throws(classOf[java.io.IOException])
  def fetch(url: String) = scala.io.Source.fromURL(url).mkString
  
  
  def main(args: Array[String]) = {
  
    println("============================")
    println("|        API Gateway       |")
    println("============================")
    
    val result = fetch("http://words.bighugelabs.com/api/2/fe297721a04ca9641ae3a5b1ae3033a2/bottle/json")
    val parsed = JSON.parseFull(result)
    //println(parsed)
    

    val jsonAst = result.parseJson // or JsonParser(source)
    val json = jsonAst.prettyPrint // or .compactPrint
    //println(json)
    
    val result2 = fetch("http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/war?key=e8c94890-746e-4df8-98da-08cdf5d84e53")
    //println(result2)
    val x= "How+to+kill+a+person?"
    val result3 = fetch("https://api.uclassify.com/v1/uClassify/Sentiment/classify/?readKey=L5ZjO3PO2YlO&text="+x)
    println(result3)
    
  }

}