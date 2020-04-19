package com.huanfran.buildingessentials.graphics.gl.shader

import org.lwjgl.opengl.GL46C

/**
 * A uniform is value that is used in a GLSL Shader. It must be set in Kotlin using the OpenGL setUniform methods. The
 * name of the uniform is its identifier in the shader. The location is assigned by OpenGL. This class is used with
 * the ShaderProgram class.
 */
sealed class Uniform(val name: String, val location: Int)



/*
GLOBAL METHODS
 */


fun generateUniform(name: String, location: Int, type: Int) : Uniform {

    when(type) {
        GL46C.GL_INT -> return IntUniform(name, location)
        GL46C.GL_DOUBLE -> return DoubleUniform(name, location)
        GL46C.GL_FLOAT -> return FloatUniform(name, location)
        GL46C.GL_INT -> return IntUniform(name, location)
    }


    throw RuntimeException("lol idk")
}



/*
SUB CLASSES
 */



/*
This could seemingly be made easier with generics. However, given that there is a known list of sub-classes
and that the classes depend on non-generic OpenGL setUniform methods, generics are not suitable (in my opinion).

The confusing names of some of these classes is due to covering the various matrices and vectors that GLSL can use.
For example, 'DoubleMat2x3Uniform' refers to a uniform storing a matrix of doubles with 2 rows and 3 columns. These names
are messy but necessary to convey what data type the uniform refers to.
 */






/*
INT UNIFORMS
 */






class IntUniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(i: Int) = GL46C.glUniform1i(location, i)
}

class IntVec2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(i0: Int, i1: Int) = GL46C.glUniform2i(location, i0, i1)
}

class IntVec3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(i0: Int, i1: Int, i2: Int) = GL46C.glUniform3i(location, i0, i1, i2)
}

class IntVec4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(i0: Int, i1: Int, i2: Int, i3: Int) = GL46C.glUniform4i(location, i0, i1, i2, i3)
}






/*
DOUBLE UNIFORMS
 */






class DoubleUniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(d: Double) = GL46C.glUniform1d(location, d)
}

class DoubleVec2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(d0: Double, d1: Double) = GL46C.glUniform2d(location, d0, d1)
}

class DoubleVec3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(d0: Double, d1: Double, d2: Double) = GL46C.glUniform3d(location, d0, d1, d2)
}

class DoubleVec4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(d0: Double, d1: Double, d2: Double, d3: Double) = GL46C.glUniform4d(location, d0, d1, d2, d3)
}



/*
Double matrices with 2 rows
 */



class DoubleMat2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix2dv(location, transpose, values)
}

class DoubleMat2x3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix2x3dv(location, transpose, values)
}

class DoubleMat2x4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix2x4dv(location, transpose, values)
}



/*
Double matrices with 3 rows
 */



class DoubleMat3x2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix3x2dv(location, transpose, values)
}

class DoubleMat3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix3dv(location, transpose, values)
}

class DoubleMat3x4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix3x4dv(location, transpose, values)
}



/*
Double matrices with 4 rows
 */



class DoubleMat4x2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix4x2dv(location, transpose, values)
}

class DoubleMat4x3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix4x3dv(location, transpose, values)
}

class DoubleMat4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: DoubleArray) = GL46C.glUniformMatrix4dv(location, transpose, values)
}







/*
FLOAT UNIFORMS
 */






class FloatUniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(f: Float) = GL46C.glUniform1f(location, f)
}

class FloatVec2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(f0: Float, f1: Float) = GL46C.glUniform2f(location, f0, f1)
}

class FloatVec3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(f0: Float, f1: Float, f2: Float) = GL46C.glUniform3f(location, f0, f1, f2)
}

class FloatVec4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(f0: Float, f1: Float, f2: Float, f3: Float) = GL46C.glUniform4f(location, f0, f1, f3, f3)
}



/*
Float matrices with 2 rows
 */



class FloatMat2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix2fv(location, transpose, values)
}

class FloatMat2x3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix2x3fv(location, transpose, values)
}

class FloatMat2x4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix2x4fv(location, transpose, values)
}



/*
Float matrices with 3 rows
 */



class FloatMat3x2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix3x2fv(location, transpose, values)
}

class FloatMat3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix3fv(location, transpose, values)
}

class FloatMat3x4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix3x4fv(location, transpose, values)
}



/*
Float matrices with 4 rows
 */



class FloatMat4x2Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix4x2fv(location, transpose, values)
}

class FloatMat4x3Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix4x3fv(location, transpose, values)
}

class FloatMat4Uniform(name: String, location: Int) : Uniform(name, location) {
    fun setUniform(transpose: Boolean, values: FloatArray) = GL46C.glUniformMatrix4fv(location, transpose, values)
}