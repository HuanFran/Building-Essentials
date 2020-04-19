package com.huanfran.buildingessentials.graphics.shaders

import com.huanfran.buildingessentials.graphics.gl.Things
import com.huanfran.buildingessentials.graphics.gl.shader.ShaderDirectory
import com.huanfran.buildingessentials.main.BE_LOGGER
import java.io.File

object Shaders {



    /*
    Shader Directories
     */



    val DIRECTORIES = ArrayList<ShaderDirectory>()

    val DEFAULT_DIRECTORY = ShaderDirectory(File(Things.insertFileSep("../src/main/resources/assets/building-essentials/guishaders")), "DEFAULT")

    init { DIRECTORIES.add(DEFAULT_DIRECTORY)}



    fun initDirectories() {
        DIRECTORIES.forEach { it.readShaders()}
    }



    /*
    Compile-time shader programs and their assignment. This is a bit tricky as the programs cannot be created
    statically. They must be created after the OpenGL context has been established. The solution is to set them as 'lateinit' variables and to
    call the initShaderPrograms() function soon after the OpenGL Context has been established.
     */



    //Shader programs
    lateinit var TEST_PROGRAM: BuildingEssentialsSP



    fun initShaderPrograms() {
        val f = File("res" + Things.FILE_SEP + "guishaders")

        BE_LOGGER.debug("DOES IT EXIST: ${f.exists()}")
        File("res").mkdir()
        f.mkdir()
        for(file in Things.filesInFolder(f)) {
            BE_LOGGER.debug("MY FILES: $file")
        }
        TEST_PROGRAM = DEFAULT_DIRECTORY.getShaders("mirror").let {
            BuildingEssentialsSP("mirror", it.first, it.second)
        }
  }


}