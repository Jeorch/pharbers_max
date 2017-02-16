package com.pharbers.aqll.calc.excel.model

import com.pharbers.aqll.calc.common.DefaultData
import com.pharbers.aqll.calc.excel.IntegratedData.IntegratedData
import com.pharbers.aqll.calc.excel.Manage.AdminHospitalDataBase

//trait ModelRunTrait {
//    def modelrun(element: AdminHospitalDataBase, element2: integratedData): modelRunData = null
//    
//    def modelrundata(output: Stream[((Int, Int, String), integratedData)]): Stream[modelRunData]
//}
//
//abstract class ModelRunAbstract extends ModelRunTrait

//case class ModelRunDefines() extends ModelRunAbstract {
//    
//    override def modelrundata(output: Stream[((Int, Int, String), integratedData)]): Stream[modelRunData] = {
//        var index = 0
//        (output.map { x =>
//        	index = index + 1
//        	println(s"current precentage : ${index} / ${output.size}")
//        	val element2 = x._2
//        	DefaultData.hospdatabase map { element =>
//        	    modelrun(element, element2)
//        	}
//        }).flatten
//    }
//    
//}

class ModelRunFactory(){
    def apply(n: Int, output: Stream[((Integer, String), IntegratedData)]) = {
        n match {
            case 0 => westMedicineIncome(output)
            case _ => ???
        }
    }
    
    def westMedicineIncome(output: Stream[((Integer, String), IntegratedData)]): Stream[modelRunData] = {
        var index = 0
        println(s"output.size = ${output.size}")
//      (output.map {x =>
//          index = index + 1
//          println(s"current precentage : ${index} / ${output.size}")
//          val element1 = x._2
//          (element1.map{ element2 =>
//            val element3 = DefaultData.hospdatabase.filter(z => x._1._2.equals(z.getSegment))
//
//            element3.map{ y =>
//              new westMedicineIncome(element.getCompany, element2.getYearAndmonth, 0, 0, element2.getMinimumUnit, element2.getMinimumUnitCh, element2.getMinimumUnitEn, element2.getMarket1Ch, element2.getMarket1En, y.getSegment, y.getFactor, y.getIfPanelAll, y.getIfPanelTouse, y.getHospId, y.getHospName, y.getPhaid, y.getIfCounty, y.getHospLevel, y.getRegion, y.getProvince, y.getPrefecture, y.getCityTier, y.getSpecialty1, y.getSpecialty2, y.getReSpecialty, y.getSpecialty3, y.getWestMedicineIncome, y.getDoctorNum, y.getBedNum, y.getGeneralBedNum, y.getMedicineBedNum, y.getSurgeryBedNum, y.getOphthalmologyBedNum, y.getYearDiagnosisNum, y.getClinicNum, y.getMedicineNum, y.getSurgeryNum, y.getHospitalizedNum, y.getHospitalizedOpsNum, y.getIncome, y.getClinicIncome, y.getClimicCureIncome, y.getHospitalizedIncome, y.getHospitalizedBeiIncome, y.getHospitalizedCireIncom, y.getHospitalizedOpsIncome, y.getDrugIncome, y.getClimicDrugIncome, y.getClimicWestenIncome, y.getHospitalizedDrugIncome, y.getHospitalizedWestenIncome, 0.0, 0.0)
//            }
//          }).flatten
//        }).flatten.filterNot(_ == null)



        (output.map { x =>
          index = index + 1
          println(s"current precentage : ${index} / ${output.size}")
          val element2 = x._2
          DefaultData.hospdatabase map { element =>
            new westMedicineIncome(element.getCompany, element2.getYearAndmonth, 0, 0, element2.getMinimumUnit, element2.getMinimumUnitCh, element2.getMinimumUnitEn, element2.getMarket1Ch, element2.getMarket1En, element.getSegment, element.getFactor, element.getIfPanelAll, element.getIfPanelTouse, element.getHospId, element.getHospName, element.getPhaid, element.getIfCounty, element.getHospLevel, element.getRegion, element.getProvince, element.getPrefecture, element.getCityTier, element.getSpecialty1, element.getSpecialty2, element.getReSpecialty, element.getSpecialty3, element.getWestMedicineIncome, element.getDoctorNum, element.getBedNum, element.getGeneralBedNum, element.getMedicineBedNum, element.getSurgeryBedNum, element.getOphthalmologyBedNum, element.getYearDiagnosisNum, element.getClinicNum, element.getMedicineNum, element.getSurgeryNum, element.getHospitalizedNum, element.getHospitalizedOpsNum, element.getIncome, element.getClinicIncome, element.getClimicCureIncome, element.getHospitalizedIncome, element.getHospitalizedBeiIncome, element.getHospitalizedCireIncom, element.getHospitalizedOpsIncome, element.getDrugIncome, element.getClimicDrugIncome, element.getClimicWestenIncome, element.getHospitalizedDrugIncome, element.getHospitalizedWestenIncome, 0.0, 0.0)
          }
        }).flatten
    }
    
}