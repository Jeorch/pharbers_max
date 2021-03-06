package com.pharbers.aqll.excel.core

import com.pharbers.aqll.excel.model._
import akka.actor.ActorRef
import com.pharbers.aqll.excel.core._

trait fileinteractparser extends interactparser {
	override def handleOneTarget(target : target_type) = Unit
}

case class hospdatainteractparser(xml_file_name : String, xml_file_name_ch : String) extends fileinteractparser {
	override val a : ActorRef = null
	type target_type = AdminHospitalDataBase
	override def targetInstance = new target_type
}

case class hospmatchinteractparser(xml_file_name: String, xml_file_name_ch: String) extends fileinteractparser {
    override val a: ActorRef = null
    type target_type = AdminHospitalMatchingData
    override def targetInstance = new target_type
}

case class marketinteractparser(xml_file_name: String, xml_file_name_ch: String) extends fileinteractparser {
    override val a: ActorRef = null
    type target_type = AdminMarket
    override def targetInstance = new target_type
}

case class productinteractparser(xml_file_name: String, xml_file_name_ch: String) extends fileinteractparser {
    override val a: ActorRef = null
    type target_type = AdminProduct
    override def targetInstance = new target_type
}
