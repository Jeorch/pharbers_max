package com.pharbers.aqll.calc.maxresult

import com.pharbers.aqll.calc.util.MD5
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}
import com.pharbers.aqll.calc.util.dao._data_connection
import java.util.Date

import com.pharbers.aqll.calc.util.dao.from

import scala.collection.mutable.ArrayBuffer
import com.mongodb.casbah.Imports._
import com.pharbers.util.dao.MongoDBCollManager

class Insert {
    
    def maxResultInsert(mr: List[(String, (Long, Double, Double, ArrayBuffer[(String)], ArrayBuffer[(String)], ArrayBuffer[(String)], String, ArrayBuffer[(String)], ArrayBuffer[String], ArrayBuffer[String], ArrayBuffer[String]))]) (m: (String, String, String, Long)) = {
        def maxInser() = {
            _data_connection.getCollection(m._2).createIndex(MongoDBObject("Hospital" -> 1))
            _data_connection.getCollection(m._2).createIndex(MongoDBObject("ProductMinunt" -> 1))
            _data_connection.getCollection(m._2).createIndex(MongoDBObject("Market" -> 1))
            _data_connection.getCollection(m._2).createIndex(MongoDBObject("Timestamp" -> 1))

            val bulk = _data_connection.getCollection(m._2).initializeUnorderedBulkOperation

//            mr.toList.foreach { x =>
//                bulk.insert(Map("ID"-> m._3,
//                                "Units" -> x._2._3,
//                                "Sales" -> x._2._2,
//                                "Hospital" -> x._2._4.head,
//                                "ProductMinunt" -> x._2._5.head,
//                                "Market" -> x._2._6.head,
//                                "Timestamp" -> x._2._1,
//                                "Createtime" -> m._4,
//                                "Filepath" -> m._1,
//                                "Rtype" -> x._2._7))
//            }

            mr.toList.filterNot(x => x._2._2 == 0 && x._2._3 == 0).foreach { x =>
              bulk.insert(Map("ID"-> m._3,
                "f_units" -> x._2._2,
                "f_sales" -> x._2._3,
                "Panel_ID" -> x._2._4.head,
                "Product" -> x._2._5.head,
                "City" -> x._2._8.head,
                "Date" -> x._2._1))
            }
            bulk.execute()
//            mr.toList map { x =>
//                 val builder = MongoDBObject.newBuilder
//                 builder += "ID" -> m._3
//                 builder += "Units" -> x._2._3
//                 builder += "Sales" -> x._2._2
//                 builder += "Hospital" -> x._2._4.head
//                 builder += "ProductMinunt" -> x._2._5.head
//                 builder += "Market" -> x._2._6.head
//    //                 builder += "Condition" -> Map("Hospital" -> x._2._4.head , "ProductMinunt" -> x._2._5.head, "Market" -> x._2._6.head)
//                 builder += "Timestamp" -> x._2._1
//                 builder += "Createtime" -> m._4
//                 builder += "Filepath" -> m._1
//                 builder += "Rtype" -> x._2._7
//                _data_connection.getCollection(m._2) += builder.result
//             }
        }
        //2727265
         println(s"mr.toList.size = ${mr.toList.filterNot(x => x._2._2 == 0 && x._2._3 == 0).size}")

         println(s"aaa111.sum = ${mr.toList.filter(_._2._9.head.equals("1")).map(_._2._2).sum}")
         println(s"bbb111.sum = ${mr.toList.filter(_._2._9.head.equals("1")).map(_._2._3).sum}")

      println(s"aaa000.sum = ${mr.toList.filter(_._2._9.head.equals("0")).map(_._2._2).sum}")
      println(s"bbb000.sum = ${mr.toList.filter(_._2._9.head.equals("0")).map(_._2._3).sum}")

        println(s"mr.toList.map(_._2._1).sum = ${mr.toList.map(_._2._2).sum}")
        println(s"mr.toList.map(_._2._2).sum = ${mr.toList.map(_._2._3).sum}")
         println(s"m._2 = ${m._2}")
         
         val conditions = ("ID" -> m._3)
         val count = (from db() in m._2 where conditions count)
         println(s"count = ${count}")
         count match {
             case 0 => {
                 maxInser()
             }
             
             case _ => {
                 val rm =MongoDBObject(conditions)
                 _data_connection.getCollection(m._2).remove(rm)
                 maxInser()
             }
         }
     }
    
    def maxFactResultInsert(model:  (Double, Double, Int, List[String], List[String]))(m: (String, String, String, Long)) = {
        def maxInser() = {
            val builder = MongoDBObject.newBuilder
            builder += "ID" -> m._3
            builder += "CompanyID" -> m._2
            builder += "Units" -> model._2
            builder += "Sales" -> model._1
            builder += "HospitalNum" -> model._3
            builder += "ProductMinuntNum" -> model._5.size
            val lsth_builder = MongoDBList.newBuilder
            model._4 foreach (lsth_builder += _)
            val lstm_builder = MongoDBList.newBuilder
            model._5 foreach (lstm_builder += _)
            
            builder += "Condition" -> Map("Hospital" -> lsth_builder.result, "ProductMinunt" -> lstm_builder.result)
            builder += "Timestamp" -> m._4
            builder += "Filepath" -> m._1.substring(m._1.lastIndexOf("\\")+1, m._1.length)
            _data_connection.getCollection("FactResult") += builder.result
        }
        val conditions = ("ID" -> m._3)
        val count = (from db() in "FactResult" where conditions count)
        count match {
             case 0 => {
                 maxInser()
             }
             
             case _ => {
                 val rm =MongoDBObject(conditions)
                 _data_connection.getCollection("FactResult").remove(rm)
                 maxInser()
             }
         }
    }
}