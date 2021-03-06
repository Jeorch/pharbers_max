package com.pharbers.aqll.alcalc.aljobs

import com.pharbers.aqll.alcalc.alcmd.shellCmdExce
import com.pharbers.aqll.alcalc.alprecess.alFilePrecess


/**
  * Created by qianpeng on 2017/3/17.
  */
object alPkgJob {

}

trait alPkgJob {
	var process : List[alFilePrecess] = Nil

	var cur : Option[List[shellCmdExce]] = None

	def excute(): Unit = {
		if (!process.isEmpty) nextRun()
	}

	def nextRun(): Unit = {
		if (!process.isEmpty) {
			val p = process.head
			process = process.tail
			cur match {
				case None => throw new Exception("job needs current stage")
				case Some(lst) => {
					lst foreach { x =>
						p.precess(x)
					}
				}
				case _ => ???
			}
			nextRun()
		}
	}
}
