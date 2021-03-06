package com.pharbers.aqll.alcalc.alSchedulerJobs

import java.util.{Calendar, Date}

import akka.actor.{Actor, ActorLogging, Props}
import com.pharbers.aqll.alcalc.alFileHandler.altext.FileOpt
import com.pharbers.aqll.util.GetProperties._

/**
  * Created by qianpeng on 2017/3/27.
  */

trait alFilePath {

	def calcPath = root + program + memorySplitFile

	def path = root + program

	def convertStr(str: String) = str.substring(0, str.lastIndexOf("/"))
}

case class rmFile()

object alScheduleRemoveFiles extends alFilePath{

	def props = Props[alScheduleRemoveFiles]

	val rmLst = calcPath + calc ::
				calcPath + fileTarGz ::
				calcPath + group ::
				calcPath + sync ::
				path + scpPath ::
				path + dumpdb :: Nil
}

class alScheduleRemoveFiles extends Actor with ActorLogging{

	// TODO : 要删除的目录 SCP、sync、group、dbdump
	val remove: Receive = {
		case rmFile() =>
			val time = Calendar.getInstance
//			printf("Hours: %s, Minute: %s, Seconds: %s \n", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND))
			if (hours == time.get(Calendar.HOUR_OF_DAY) &&
				minutes == time.get(Calendar.MINUTE) &&
				seconds > time.get(Calendar.SECOND)) {
				alScheduleRemoveFiles.rmLst foreach (FileOpt(_).rmcAllFiles)
			}
		case _ => ???
	}

	override def receive = remove
}
