package com.pharbers.aqll.util.alcmd

import java.io._

sealed class ShellResultDefines (val t : Int, val d : String)

object ShellResult {
    case object success extends ShellResultDefines(0, "success")
    case object faild extends ShellResultDefines(-1, "faild")
}

trait shellCmdExce {

    var process : Process = null
    val cmd : String

    def excute : Int = {
        import ShellResult.success._
        import ShellResult.faild._

        try {
            println(cmd)
            val builder = new ProcessBuilder("/bin/bash", "-c", cmd)
            val process = builder.start()

            val ir = new InputStreamReader(process.getInputStream())
            val input = new LineNumberReader(ir)

            var line : String = null
            process.waitFor()

//            do {
//                line = input.readLine()
//                println(line)
//
//            } while (line != null)

            ShellResult.success.t

        } catch {
            case _ : IOException => {
                println("io exception occurs")
                ShellResult.faild.t
            }

            case ex : Exception => {
                println(ex.getMessage)
                ShellResult.faild.t
            }
        }
    }
}
