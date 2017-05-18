package com.pharbers.aqll.alCalcMemory.alprecess

import com.pharbers.aqll.alCalaHelp.DefaultData
import com.pharbers.aqll.alCalaHelp.alFileHandler.alFileHandlers
import com.pharbers.aqll.alCalc.almodel.java.IntegratedData
import com.pharbers.aqll.alCalcMemory.aldata.alStorage
import com.pharbers.aqll.alCalcMemory.alstages.{alInitStage, alMemoryStage, alPresisStage, alStage}
import com.pharbers.aqll.common.alFileHandler.alExcelOpt.scala.{BaseExcel, alExcelDataParser}

/**
  * Created by Alfred on 10/03/2017.
  */

case class excelHandlers() extends alExcelDataParser(new IntegratedData, DefaultData.integratedxmlpath_en, DefaultData.integratedxmlpath_ch) with alFileHandlers

class alReadExcelPrecess extends alPrecess {
    def precess(j : alStage) : List[alStage] = {

        def precessAcc(path : String) : alStorage = alStorage(path, excelHandlers())

        try {
            j match {
                case it : alInitStage => alStage(precessAcc(it.storages.head.toString) :: Nil) :: Nil
                case it : alPresisStage => alStage(it.storages.map(x => precessAcc(x.toString))) :: Nil
                case _ : alMemoryStage => ???
            }

        } catch {
            case ex : OutOfMemoryError => println("not enough memory"); throw ex
            case ex : Exception => println("unknow error"); throw ex
        }
    }

    def action(j : alStage) = {
        println("read excel is map precess")
        throw new Exception("read excel is map precess")
    }
}