package com.pharbers.aqll.calc.log

import org.apache.log4j.{Logger, PropertyConfigurator}
import akka.actor.{ActorSystem, _}
object LogMessage extends App{
    def record_log(senderR : ActorRef, classStr: String, args : String) {
        try {
            PropertyConfigurator.configure("config/log/log4j.properties");//加载.properties文件
            val logger = Logger.getRootLogger
            // 调用方式：record_log(sender,${this.getClass.getName},"")
            logger.info(s"Sender: ${senderR.path} Class: ${classStr} Args: ${args}")
        } catch {
            case ex : Exception => println("can not parse result")
        }
    }
}
