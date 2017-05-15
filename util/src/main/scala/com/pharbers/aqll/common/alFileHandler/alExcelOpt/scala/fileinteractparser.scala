package com.pharbers.aqll.common.alFileHandler.alExcelOpt.scala

import akka.actor.ActorRef
import com.pharbers.aqll.old.calc.alcalc.almodel.{AdminHospitalData, AdminHospitalDataBase, IntegratedData}

trait fileinteractparser extends interactparser {
	override def handleOneTarget(target : target_type) = Unit
}

case class hospdatainteractparser(xml_file_name: String, xml_file_name_ch : String) extends fileinteractparser {
	override val a : ActorRef = null
	type target_type = AdminHospitalDataBase
	override def targetInstance = new target_type
}

case class integrateddataparser(xml_file_name: String, xml_file_name_ch: String) extends fileinteractparser {
    override val a: ActorRef = null
    type target_type = IntegratedData
	override def targetInstance = new target_type
}

case class exceldataparser(xml_file_name: String, xml_file_name_ch: String, targetHandle: BaseExcle) extends fileinteractparser {
	override val a : ActorRef = null
	type target_type = BaseExcle
	override def targetInstance = targetHandle
}