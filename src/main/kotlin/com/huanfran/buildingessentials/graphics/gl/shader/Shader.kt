package com.huanfran.buildingessentials.graphics.gl.shader

import com.huanfran.buildingessentials.graphics.gl.Things
import com.huanfran.buildingessentials.graphics.gl.enumeration.ShaderType
import org.lwjgl.opengl.GL46C
import java.io.File

/**
 * Represents a programmable step in the rendering pipeline. A vertex and fragment shader is necessary for displaying
 * anything in OpenGL.
 */
class Shader(val shaderType: ShaderType, val file: File, val name: String) {


    /*
    Variables
     */



    val shaderID = createShader(Things.loadShaderCode(file), shaderType)



    /*
    Secondary Constructors
     */



    constructor(shaderType: ShaderType, file: File) : this(shaderType, file, file.nameWithoutExtension)



    /*
    Methods
     */



    fun attach(programID: Int) = GL46C.glAttachShader(programID, shaderID)

    fun detach(programID: Int) = GL46C.glDetachShader(programID, shaderID)

    fun delete() = GL46C.glDeleteShader(shaderID)


}






/*
Static Methods
 */






private fun createShader(shaderCode: String?, shaderType: ShaderType) : Int {
    if(shaderCode == null) return 0

    val shaderID = GL46C.glCreateShader(shaderType.id)

    Things.glErrorCheck(shaderID, "Could not create shader of type $shaderType")

    GL46C.glShaderSource(shaderID, shaderCode)
    GL46C.glCompileShader(shaderID)

    Things.glErrorCheck(
        GL46C.glGetShaderi(shaderID, GL46C.GL_COMPILE_STATUS),
        "Shader not compiled: " + GL46C.glGetShaderInfoLog(shaderID, 1024))

    return shaderID
}