package com.huanfran.buildingessentials.graphics.gl.shader

import com.huanfran.buildingessentials.graphics.gl.AGL
import com.huanfran.buildingessentials.graphics.gl.Things
import com.huanfran.buildingessentials.graphics.gl.enumeration.ShaderProgramParameter
import com.huanfran.buildingessentials.graphics.gl.enumeration.UniformType
import com.huanfran.buildingessentials.graphics.gl.vbo.Bindable
import org.lwjgl.opengl.GL46C
import com.huanfran.buildingessentials.graphics.gl.shader.exception.UniformNotFoundException
import com.huanfran.buildingessentials.graphics.gl.shader.exception.UniformTypeException

abstract class ShaderProgram(val name: String,
                             val vertex: Shader,
                             val fragment: Shader,
                             val geometry: Shader?) : Bindable {



    /*
    Variables and Values
     */



    val programID = GL46C.glCreateProgram()



    /*
    Secondary constructors and Initialisation
     */



    init {
        initProgram(vertex, fragment, geometry)
    }



    /*
    Overridden methods
     */



    override fun bind() = GL46C.glUseProgram(programID)

    override fun unbind() = GL46C.glUseProgram(0)



    /*
    Methods
     */



    /**
     * Does all the necessary OpenGL things to get the shaders attached to the shader program.
     */
    private fun initProgram(vertex: Shader, fragment: Shader, geometry: Shader?) {
        //Make sure the programID was generated correctly
        Things.glErrorCheck(programID, "Could not create shader program")

        //Attach any non-null shaders
        vertex.attach(programID)
        fragment.attach(programID)
        geometry?.attach(programID)

        AGL.linkProgram(this)

        //Make sure the program was linked correctly
        Things.glErrorCheck(GL46C.glGetProgrami(programID, GL46C.GL_LINK_STATUS),
            "Shader program not linked. " +
                    GL46C.glGetProgramInfoLog(programID, 1024));

        //Detach any non-null shaders
        vertex.detach(programID)
        fragment.detach(programID)
        geometry?.detach(programID)

        AGL.validateProgram(this)
    }



    /*
    OpenGL method implementations
     */



    private fun getProgrami(parameter: ShaderProgramParameter) = AGL.getProgrami(programID, parameter)

    private fun activeUniformCount() = getProgrami(ShaderProgramParameter.ACTIVE_UNIFORMS)

    private fun getActiveUniformName(uniformIndex: Int) = GL46C.glGetActiveUniformName(programID, uniformIndex)

    private fun getUniformLocation(uniformName: String) = GL46C.glGetUniformLocation(programID, uniformName)

    private fun getUniformType(location: Int) = GL46C.glGetActiveUniformsi(programID, location, GL46C.GL_UNIFORM_TYPE)



    /*
    Uniform methods
     */



    private fun generateUniform(name: String, type: UniformType) : Int {
        val location = getUniformLocation(name)

        if(location == -1) throw UniformNotFoundException(name, this)

        val actualType = getUniformType(location)

        //The expensive fromID method is only used if the uniform type is invalid.
        if(actualType != type.id) throw UniformTypeException(
            name,
            this,
            type,
            UniformType.fromID(actualType)
        )

        return location
    }



    /*
    Int Uniforms
     */



    fun generateIntUniform(name: String) =
        IntUniform(name, generateUniform(name, UniformType.INT))

    fun generateIntVec2Uniform(name: String) =
        IntVec2Uniform(name, generateUniform(name, UniformType.INT_VEC2))

    fun generateIntVec3Uniform(name: String) =
        IntVec3Uniform(name, generateUniform(name, UniformType.INT_VEC3))

    fun generateIntVec4Uniform(name: String) =
        IntVec4Uniform(name, generateUniform(name, UniformType.INT_VEC4))



    /*
    Double Uniforms
     */

    
    
    fun generateDoubleUniform(name: String) =
        DoubleUniform(name, generateUniform(name, UniformType.DOUBLE))

    fun generateDoubleVec2Uniform(name: String) =
        DoubleVec2Uniform(name, generateUniform(name, UniformType.DOUBLE_VEC2))

    fun generateDoubleVec3Uniform(name: String) =
        DoubleVec3Uniform(name, generateUniform(name, UniformType.DOUBLE_VEC3))

    fun generateDoubleVec4Uniform(name: String) =
        DoubleVec4Uniform(name, generateUniform(name, UniformType.DOUBLE_VEC4))

    fun generateDoubleMat2Uniform(name: String) =
        DoubleMat2Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT2))

    fun generateDoubleMat2x3Uniform(name: String) =
        DoubleMat2x3Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT2X3))

    fun generateDoubleMat2x4Uniform(name: String) =
        DoubleMat2x4Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT2X4))

    fun generateDoubleMat3x2Uniform(name: String) =
        DoubleMat3x2Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT3X2))

    fun generateDoubleMat3Uniform(name: String) =
        DoubleMat3Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT3))

    fun generateDoubleMat3x4Uniform(name: String) =
        DoubleMat3x4Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT3X4))

    fun generateDoubleMat4x2Uniform(name: String) =
        DoubleMat4x2Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT4X2))

    fun generateDoubleMat4x3Uniform(name: String) =
        DoubleMat4x3Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT4X3))
    
    fun generateDoubleMat4Uniform(name: String) =
        DoubleMat4Uniform(name, generateUniform(name, UniformType.DOUBLE_MAT4))



    /*
    Float Uniforms
     */



    fun generateFloatUniform(name: String) =
        FloatUniform(name, generateUniform(name, UniformType.FLOAT))

    fun generateFloatVec2Uniform(name: String) =
        FloatVec2Uniform(name, generateUniform(name, UniformType.FLOAT_VEC2))

    fun generateFloatVec3Uniform(name: String) =
        FloatVec3Uniform(name, generateUniform(name, UniformType.FLOAT_VEC3))

    fun generateFloatVec4Uniform(name: String) =
        FloatVec4Uniform(name, generateUniform(name, UniformType.FLOAT_VEC4))

    fun generateFloatMat2Uniform(name: String) =
        FloatMat2Uniform(name, generateUniform(name, UniformType.FLOAT_MAT2))

    fun generateFloatMat2x3Uniform(name: String) =
        FloatMat2x3Uniform(name, generateUniform(name, UniformType.FLOAT_MAT2X3))

    fun generateFloatMat2x4Uniform(name: String) =
        FloatMat2x4Uniform(name, generateUniform(name, UniformType.FLOAT_MAT2X4))

    fun generateFloatMat3x2Uniform(name: String) =
        FloatMat3x2Uniform(name, generateUniform(name, UniformType.FLOAT_MAT3X2))

    fun generateFloatMat3Uniform(name: String) =
        FloatMat3Uniform(name, generateUniform(name, UniformType.FLOAT_MAT3))

    fun generateFloatMat3x4Uniform(name: String) =
        FloatMat3x4Uniform(name, generateUniform(name, UniformType.FLOAT_MAT3X4))

    fun generateFloatMat4x2Uniform(name: String) =
        FloatMat4x2Uniform(name, generateUniform(name, UniformType.FLOAT_MAT4X2))

    fun generateFloatMat4x3Uniform(name: String) =
        FloatMat4x3Uniform(name, generateUniform(name, UniformType.FLOAT_MAT4X3))

    fun generateFloatMat4Uniform(name: String) =
        FloatMat4Uniform(name, generateUniform(name, UniformType.FLOAT_MAT4))


}