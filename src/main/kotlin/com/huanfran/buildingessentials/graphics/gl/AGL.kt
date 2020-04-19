package com.huanfran.buildingessentials.graphics.gl

import com.huanfran.buildingessentials.graphics.gl.enumeration.*
import org.lwjgl.opengl.GL46C
import com.huanfran.buildingessentials.graphics.gl.shader.Shader
import com.huanfran.buildingessentials.graphics.gl.shader.ShaderProgram
import com.huanfran.buildingessentials.graphics.maths.Colour
import java.nio.*

object AGL {



    /*
    VAO
    */



    fun unbindVAO() = GL46C.glBindVertexArray(0)



    /*
    VBO Binding
    */



    fun bindBuffer(target: BufferTarget, buffer: Int) = GL46C.glBindBuffer(target.id, buffer)

    fun unbindBuffer(target: BufferTarget) = bindBuffer(target, 0)



    /*
    Buffering
    */



    fun bufferData(target: BufferTarget, data: DoubleArray, usage: BufferUsage) = GL46C.glBufferData(target.id, data, usage.id)

    fun bufferData(target: BufferTarget, data: IntArray, usage: BufferUsage) = GL46C.glBufferData(target.id, data, usage.id)

    fun bufferData(target: BufferTarget, data: FloatArray, usage: BufferUsage) = GL46C.glBufferData(target.id, data, usage.id)

    fun bufferData(target: BufferTarget, data: LongArray, usage: BufferUsage) = GL46C.glBufferData(target.id, data, usage.id)

    fun bufferData(target: BufferTarget, data: ShortArray, usage: BufferUsage) = GL46C.glBufferData(target.id, data, usage.id)



    fun bufferSubData(target: BufferTarget, offset: Long, data: DoubleArray) = GL46C.glBufferSubData(target.id, offset, data)

    fun bufferSubData(target: BufferTarget, offset: Long, data: IntArray) = GL46C.glBufferSubData(target.id, offset, data)

    fun bufferSubData(target: BufferTarget, offset: Long, data: FloatArray) = GL46C.glBufferSubData(target.id, offset, data)

    fun bufferSubData(target: BufferTarget, offset: Long, data: LongArray) = GL46C.glBufferSubData(target.id, offset, data)

    fun bufferSubData(target: BufferTarget, offset: Long, data: ShortArray) = GL46C.glBufferSubData(target.id, offset, data)



    /*
    Buffer Information
    */



    fun getBufferParameter(target: BufferTarget, parameter: BufferParameter) = GL46C.glGetBufferParameteri(target.id, parameter.id)


    fun getBufferSubData(target: BufferTarget, offset: Long, data: DoubleArray) = GL46C.glGetBufferSubData(target.id, offset, data)

    fun getBufferSubData(target: BufferTarget, offset: Long, data: IntArray) = GL46C.glGetBufferSubData(target.id, offset, data)

    fun getBufferSubData(target: BufferTarget, offset: Long, data: FloatArray) = GL46C.glGetBufferSubData(target.id, offset, data)

    fun getBufferSubData(target: BufferTarget, offset: Long, data: LongArray) = GL46C.glGetBufferSubData(target.id, offset, data)

    fun getBufferSubData(target: BufferTarget, offset: Long, data: ShortArray) = GL46C.glGetBufferSubData(target.id, offset, data)



    /*
    Attrib pointers
    */



    fun vertexAttribPointer(index: Int, size: Int, type: DataType, normalised: Boolean, stride: Int, pointer: Long) =
        GL46C.glVertexAttribPointer(index, size, type.id, normalised, stride, pointer)



    /*
    Creation and Usage of Shaders and of Programs
    */



    fun linkProgram(program: ShaderProgram) = GL46C.glLinkProgram(program.programID)

    fun compileShader(shader: Shader) = GL46C.glCompileShader(shader.shaderID)

    fun validateProgram(program: ShaderProgram) = GL46C.glValidateProgram(program.programID)

    fun useProgram(program: ShaderProgram) = GL46C.glUseProgram(program.programID)

    fun useNoProgram() = GL46C.glUseProgram(0)



    /*
    Shader information
    */



    fun getProgrami(programID: Int, parameter: ShaderProgramParameter) = GL46C.glGetProgrami(programID, parameter.id)

    fun getActiveUniformName(program: ShaderProgram, uniformIndex: Int): String = GL46C.glGetActiveUniformName(program.programID, uniformIndex)

    fun getUniformLocation(program: ShaderProgram) = GL46C.glGetUniformLocation(program.programID, program.name)



    /*
    Draw Calls
     */



    fun drawElements(mode: DrawMode, count: Int, type: IndicesDataType, indicesIndex: Long) =
        GL46C.glDrawElements(mode.id, count, type.id, indicesIndex)



    /*
    Colours
    */



    fun clearColour(r: Double, g: Double, b: Double, a: Double) = GL46C.glClearColor(r.toFloat(), g.toFloat(), b.toFloat(), a.toFloat())

    fun clearColour(colour: Colour) =
        clearColour(
            colour.r,
            colour.g,
            colour.b,
            colour.a
        )



    /*
    Texture Binding
     */



    fun bindTexture(target: TextureTarget, textureID: Int) = GL46C.glBindTexture(target.id, textureID)

    fun unbindTexture(target: TextureTarget) = GL46C.glBindTexture(target.id, 0)

    fun generateMipmap(target: TextureMipmapTarget) = GL46C.glGenerateMipmap(target.id)



    /*
    glTexImage2D
     */



    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: IntBuffer) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: DoubleBuffer) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: FloatBuffer) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: ShortBuffer) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: ByteBuffer) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: IntArray) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: DoubleArray) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: FloatArray) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)

    fun texImage2D(target: TextureTarget,
                   mipmapLevel: Int,
                   internalFormat: TextureInternalFormat,
                   width: Int,
                   height: Int,
                   borderWidth: Int,
                   dataFormat: TextureDataFormat,
                   dataType: TextureDataType,
                   pixels: ShortArray) =
        GL46C.glTexImage2D(target.id, mipmapLevel, internalFormat.id, width, height, borderWidth, dataFormat.id, dataType.id, pixels)


}
