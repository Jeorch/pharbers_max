package com.pharbers.aqll.calc.export

import java.text.SimpleDateFormat
import java.util.Calendar

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.BasicDBObject
import com.mongodb.DBObject

import scala.collection.immutable.List
import com.pharbers.aqll.calc.export.DataWriteIn._
import com.pharbers.aqll.calc.util.DateUtil.polishMonth
import com.pharbers.aqll.calc.util.GetProperties
import com.pharbers.aqll.calc.util.dao.from

/**
  * Created by Wli on 2017/3/8 0008.
  */
object ResultQuery {
//	var map = Map("datatype" -> "省份数据","company" -> "098f6bcd4621d373cade4e832627b4f6","market" -> "AI_R#","staend" -> "01/2015#12/2016#")
//	finalresult_func(map)
	/*Stitching query conditions*/
	def finalresult_func(data : Map[String,Any]) : List[Map[String,Any]] = {
		try{
			var markets = data.get("market").get.asInstanceOf[String].split("#").filter(!_.equals(""))
			val datetiem = data.get("staend").get.asInstanceOf[String].split("#")
			val fm = new SimpleDateFormat("MM/yyyy")
			var conditions = List("Date" $gte fm.parse(datetiem.head).getTime $lte fm.parse(datetiem.tail.head).getTime)
			if(markets.size!=0){
				conditions = List("Date" $gte fm.parse(datetiem.head).getTime $lte fm.parse(datetiem.tail.head).getTime,"Market" $in markets)
			}
			println(s"conditions=${conditions}")
			val fileName = WriteDataToCSV(data,conditions)
			println(s"fileName=$fileName")
		}catch {
			case ex : Exception => println("Written to the file anomalies.")
		}
		List(Map("result" -> "Success"))
	}
}

object TestGroupByPCH extends App {
//	db.getCollection('098f6bcd4621d373cade4e832627b4f6').aggregate([{$match:{Market : "AI_R","Date" : {$gte: 1420041600000, $lte : 1420041600000}}},
//	{$group:{
//		_id:{
//		Date:"$Date",
//		Provice:"$Provice",
//		City:"$City",
//		Market:"$Market",
//		Product:"$Product"
//	},
//		f_sales_sum:{$sum:"$f_sales"},
//		f_units_sum:{$sum:"$f_units"}
//	}
//	}])

	/*List(
		{ "$match" : { "Market" : "AI_R"}},
		{ "$match" : { "Date" : { "$gte" : 1420041600000 , "$lte" : 1480521600000}}},
		{ "$group" : { "_id" : { "Date" : "$Date" , "Provice" : "$Provice" , "City" : "$City" , "Market" : "$Market" ,
		"Product" : "$Product"} , "f_sales_sum" : { "$sum" : "$f_sales"} , "f_units_sum" : { "$sum" : "$f_units"}}}
	)*/


	// 首先利$match筛选出where条件
	val data = Map("datatype" -> "省份数据","market" -> "AI_R#","staend" -> "01/2015#12/2016#")
	queryData(data)
	def queryData(data : Map[String,Any]){
		val datetiem = data.get("staend").get.asInstanceOf[String].split("#")
		var markets = data.get("market").get.asInstanceOf[String].split("#").filter(!_.equals(""))
		var datatype = data.get("datatype").get.asInstanceOf[String]
		val fm = new SimpleDateFormat("MM/yyyy")

		// 利用$group进行分组
		var _group : DBObject = new BasicDBObject("Date", "$Date")
		_group.put("Date", "$Date")
		_group.put("Provice", "$Provice")
		if(datatype.equals("城市数据") || datatype.equals("医院数据")){
			_group.put("City", "$City")
			if(datatype.equals("医院数据")){
				_group.put("Panel_ID", "$Panel_ID")
			}
		}
		_group.put("Market", "$Market")
		_group.put("Product", "$Product")
		val groupFields : DBObject = new BasicDBObject("_id", _group)

		//求和
		groupFields.put("Sales", new BasicDBObject("$sum", "$f_sales"))
		groupFields.put("Units", new BasicDBObject("$sum", "$f_units"))
		val group : DBObject = new BasicDBObject("$group", groupFields)

		val conditions : List[DBObject] = List(("$match" $eq ("Market" $eq markets.head)),("$match" $eq ("Date" $gte fm.parse(datetiem.head).getTime $lte fm.parse(datetiem.tail.head).getTime)),group)
		println(conditions)
		val result = (from db() in "098f6bcd4621d373cade4e832627b4f6" where conditions).selectAggregate(resultData(_,datatype)).toList
		println(result.size)
	}

	def resultData(x: MongoDBObject,datatype: String): Map[String,Any] = {
		val _id = x.getAs[MongoDBObject]("_id").get
		val timeDate = Calendar.getInstance
		timeDate.setTimeInMillis(_id.getAs[Number]("Date").get.longValue)
		var yearmonth = s"${timeDate.get(Calendar.YEAR).toString}${polishMonth((timeDate.get(Calendar.MONTH)+1).toString)}"
		var map = Map("Date" -> yearmonth)
		datatype match {
			case "省份数据" => {
				map ++ Map(
					"Provice" -> _id.getAs[String]("Provice").get,
					"Market" -> _id.getAs[String]("Market").get,
					"Product" -> _id.getAs[String]("Product").get,
					"Sales" -> x.getAs[Number]("Sales").get.doubleValue(),
					"Units" -> x.getAs[Number]("Units").get.doubleValue()
				)
			}
			case "城市数据" => {
				map ++ Map(
					"Provice" -> _id.getAs[String]("Provice").get,
					"City" -> _id.getAs[String]("City").get,
					"Market" -> _id.getAs[String]("Market").get,
					"Product" -> _id.getAs[String]("Product").get,
					"Sales" -> x.getAs[Number]("Sales").get.doubleValue(),
					"Units" -> x.getAs[Number]("Units").get.doubleValue()
				)
			}
			// 由于医院层面GroupBy之后，数据量过大，会导致内存溢出。
			// Exceeded memory limit for $group, but didn't allow external sort. Pass allowDiskUse:true to opt in.
			/*case "医院数据" => {
				map ++ Map(
					"Provice" -> _id.getAs[String]("Provice").get,
					"City" -> _id.getAs[String]("City").get,
					"Panel_ID" -> _id.getAs[String]("Panel_ID").get,
					"Market" -> _id.getAs[String]("Market").get,
					"Product" -> _id.getAs[String]("Product").get,
					"Sales" -> x.getAs[Number]("Sales").get.doubleValue(),
					"Units" -> x.getAs[Number]("Units").get.doubleValue()
				)
			}*/

		}

	}
}