package com.huanfran.buildingessentials.save

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.mirror.MirrorController
import com.huanfran.mirror.MirrorHandler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

/**
 * Saves and loads mirrors from world save files.
 */
object MirrorSaving {


    fun writeMirrors(handler: MirrorHandler, path: String) = BufferedWriter(FileWriter(path)).use { writer ->
        handler.controllers.forEach {
            with(it) {
                writer.write("(${v0.x},${v0.y},${v0.z}):(${v1.x},${v1.y},${v1.z}):$width2:$length2:$height2")
            }
        }
    }



    fun readMirrors(path: String) = try {
        val controllers = ArrayList<MirrorController>()

        BufferedReader(FileReader(path)).use { reader ->
            while(reader.ready()) {
                val line = reader.readLine()
                val parts = line.split(":")

                val v0 = Vector3(parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble())
                val v1 = Vector3(parts[3].toDouble(), parts[4].toDouble(), parts[5].toDouble())

                val width2 = parts[6].toDouble()
                val length2 = parts[7].toDouble()
                val height2 = parts[8].toDouble()

                controllers.add(MirrorController(v0, v1, width2, length2, height2))
            }
        }

        controllers
    } catch(e: Exception) {
        ArrayList()
    }


}