package com.pharbers.aqll.alcalc.alFileHandler.altext

import com.pharbers.aqll.alcalc.alFileHandler.alFileHandler
import com.pharbers.aqll.alcalc.aldata.{alMemoryPortion, alStorage, alPersisportion}
import com.pharbers.aqll.calc.util.MD5

import java.util.Date

/**
  * Created by BM on 09/03/2017.
  */
object alTextSync {
    def apply(path : String, s : alStorage) = (new alTextSync).sync(path, s)
}

class alTextSync extends alFileHandler with CreateInnerSync {
    val parser = CreateInnerSync

    override def sync(path : String, s : alStorage) = {
        s.doCalc
        if (s.isPortions) {
            s.portions.foreach { p =>
                val file = MD5.md5(new Date().getTime.toString)
                parser.startSync(path + "/" + file , p.data)
            }
        } else {
            val file = MD5.md5(new Date().getTime.toString)
            parser.startSync(path + "/" + file, s.data)
        }
        Unit
    }
}

case class inner_sync(h : alFileHandler) {
    implicit val f : String => Any = x => x
    def startSync(file : String, data : List[Any]) = FileOpt(file).pushData2File(data)
}

trait CreateInnerSync { this : alFileHandler =>
    def CreateInnerSync : inner_sync = new inner_sync(this)
}