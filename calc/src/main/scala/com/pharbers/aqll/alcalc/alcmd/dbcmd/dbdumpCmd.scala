package com.pharbers.aqll.alcalc.alcmd.dbcmd

import com.pharbers.aqll.alcalc.alcmd.shellCmdExce
import com.pharbers.aqll.util.GetProperties

case class dbdumpCmd(val db : String, val coll : String) extends shellCmdExce {
    val cmd = "/usr/bin/mongodump " +
        DBConfig(db, coll, s"${GetProperties.dumpdb}", Some(s"${GetProperties.dumpdb_ip}"), Some(2017), Some("Pharbers"), Some("Pharbers2017.")).toArgs
}
