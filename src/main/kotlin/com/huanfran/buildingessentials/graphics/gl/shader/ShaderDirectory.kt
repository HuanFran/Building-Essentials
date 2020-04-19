package com.huanfran.buildingessentials.graphics.gl.shader

import com.huanfran.buildingessentials.graphics.gl.Things
import com.huanfran.buildingessentials.graphics.gl.enumeration.ShaderType
import com.huanfran.buildingessentials.graphics.gl.shader.exception.ShaderNotFoundException
import java.io.File

class ShaderDirectory(val directory: File, val name: String) {


    /*
    Shaders, listed by type
     */



    private val VERTEX_SHADERS = ArrayList<Shader>()

    private val FRAGMENT_SHADERS = ArrayList<Shader>()

    private val GEOMETRY_SHADERS = ArrayList<Shader>()



    /*
    Shader initialisation from appropriate files within the directory (shader files must end in .vert, .frag, or .geom)
     */



    private fun isVertexShader(fileName: String) = fileName.endsWith(".vert")

    private fun isFragmentShader(fileName: String) = fileName.endsWith(".frag")

    private fun isGeometryShader(fileName: String) = fileName.endsWith(".geom")



    fun readShaders() {
        val shaderFiles = Things.filesInFolder(directory)

        for(file in shaderFiles) {
            when {
                isVertexShader(file.name) -> VERTEX_SHADERS.add(Shader(ShaderType.VERTEX_SHADER, file))
                isFragmentShader(file.name) -> FRAGMENT_SHADERS.add(Shader(ShaderType.FRAGMENT_SHADER, file))
                isGeometryShader(file.name) -> GEOMETRY_SHADERS.add(Shader(ShaderType.GEOMETRY_SHADER, file))
            }
        }
    }



    fun getShaders(programName: String) : Triple<Shader, Shader, Shader?> {
        val vertex = getVertexShader(programName)
        val fragment = getFragmentShader(programName)
        val geometry = getGeometryShader(programName)

        when {
            vertex == null -> throw ShaderNotFoundException(programName, this, ShaderType.VERTEX_SHADER)
            fragment == null -> throw ShaderNotFoundException(programName, this, ShaderType.FRAGMENT_SHADER)
            else -> return Triple(vertex, fragment, geometry)
        }
    }




    /*
    Some true Kotlin fanciness right here.
     */



    fun getVertexShader(name: String) = VERTEX_SHADERS.firstOrNull { it.name == name }

    fun getFragmentShader(name: String) = FRAGMENT_SHADERS.firstOrNull { it.name == name }

    fun getGeometryShader(name: String) = GEOMETRY_SHADERS.firstOrNull { it.name == name }


}