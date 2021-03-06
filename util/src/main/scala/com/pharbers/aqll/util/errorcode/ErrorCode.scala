package com.pharbers.aqll.util.errorcode

object ErrorCode {
  	case class ErrorNode(name : String, code : Int, message : String)

  	private def xls : List[ErrorNode] = List(
  		//new ErrorNode("token exprie", -1, "inputing token is exprition"),
  		new ErrorNode("error reading data", -2, "读取数据出错"),
        new ErrorNode("data is null", -100, "文件数据无效"),
        new ErrorNode("unknown error", -999, "未知错误")
  		//new ErrorNode("unknown error", -999, "unknown error")
  	)

  	def getErrorCodeByName(name : String) : Int = (xls.find(x => x.name == name)) match {
  			case Some(y) => y.code
  			case None => -9999
  		}
  	
   	def getErrorMessageByCode(code : Int) : String = (xls.find(x => x.code == code)) match {
  			case Some(y) => y.message
  			case None => "unknow error"
  		}
   	
   	def getErrorMessageByName(name : String) : String = (xls.find(x => x.name == name)) match {
  			case Some(y) => y.message
  			case None => "unknow error"
  		}
   
   	def errorMessageByCode(code : Int) : (Int, String) = (code, getErrorMessageByCode(code))
}