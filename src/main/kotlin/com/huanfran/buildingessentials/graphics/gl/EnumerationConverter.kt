package com.huanfran.buildingessentials.graphics.gl

import java.io.*

object EnumerationConverter {


    fun convert(source: File, output: File) {
        val lines = ArrayList<String>()

        BufferedReader(FileReader(source)).use {
            while(it.ready()) {
                val l = it.readLine()
                lines.add("$l(GL46C.GL_$l)")
            }
        }

        BufferedWriter(FileWriter(output)).use {
            for(i in lines.indices){
                it.write(lines[i] + if(i == lines.size - 1) ";" else "," + "\n\n")
            }
        }
    }


}