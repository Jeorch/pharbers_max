package com.pharbers.aqll.alcalc.alFileHandler.altext

import java.io.{File, PrintWriter}

import scala.io.Source

/**
  * Created by Alfred on 09/03/2017.
  */
object FileOpt {
    def apply(path : String) : FileOpt = new FileOpt(path)
}

class FileOpt(val file : String) {
    def isExist : Boolean = new File(file).exists()

    def pushData2File(lst : List[Any]) = {
        val writer = new PrintWriter(new File(file))
        lst foreach (x => writer.println(x))
        writer.close()
    }

    def requestDataFromFile(f : String => Any) : List[Any] = Source.fromFile(file).getLines().map(f(_)).toList
}