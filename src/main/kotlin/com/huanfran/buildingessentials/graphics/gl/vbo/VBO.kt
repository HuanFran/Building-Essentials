package com.huanfran.buildingessentials.graphics.gl.vbo

import com.huanfran.buildingessentials.graphics.gl.AGL
import com.huanfran.buildingessentials.graphics.gl.enumeration.BufferParameter
import com.huanfran.buildingessentials.graphics.gl.enumeration.BufferTarget
import com.huanfran.buildingessentials.graphics.gl.enumeration.BufferUsage
import org.lwjgl.opengl.GL46C

/**
 * NOTE: Generics are perhaps not appropriate here as all sub-classes are known (hence 'sealed class' and a lack of
 * adherence to a 'generic' class). However, using generics does make this file more concise.
 */
sealed class VBO<T>(var target: BufferTarget,
                    var usage: BufferUsage,
                    var pointer: VertexAttribPointer? = null,
                    data: T? = null) : Bindable {


    var buffer: Int = GL46C.glGenBuffers()



    init {
        doBound {
            pointer?.set()
            data?.let { bufferData(it) }
        }
    }



    /*
    Binding and some fancy functions
     */



    override fun bind() = AGL.bindBuffer(target, buffer)



    override fun unbind() = AGL.unbindBuffer(target)



    final override fun<T> getBound(thing: () -> T) : T = super.getBound(thing)



    final override fun doBound(thing: () -> Unit) = super.doBound(thing)



    /*
    Buffering Data
     */



    //Used to allocate the VBOs initial data contents
    abstract fun bufferData(data: T)
    fun bufferDataNow(data: T) = doBound { bufferData(data) }



    //Used to update the VBOs data contents, perhaps more efficient than buffer data after initial allocation.
    abstract fun bufferSubData(data: T, offset: Long)
    fun bufferSubDataNow(data: T, offset: Long) = doBound { bufferSubData(data, offset) }



    /*
    Buffer Information
     */



    fun getBufferParameter(parameter: BufferParameter) = AGL.getBufferParameter(target, parameter)
    fun getBufferParameterNow(parameter: BufferParameter) = doBound { getBufferParameter(
        BufferParameter.BUFFER_SIZE) }



    fun getBufferSize() = getBufferParameter(BufferParameter.BUFFER_SIZE)
    fun getBufferSizeNow() = doBound { getBufferSize() }



    abstract fun getSubData(offset: Long) : T
    fun getSubDataNow(offset: Long) = doBound { getSubData(offset) }


}






/*
SEALED SUB-CLASSES. Int, Double, Float, Long, and Short VBOs.
 */






class IntVBO(target: BufferTarget, usage: BufferUsage, pointer: VertexAttribPointer?, data: IntArray?) :
    VBO<IntArray>(target, usage, pointer, data) {


    override fun bufferData(data: IntArray) = AGL.bufferData(target, data, usage)

    override fun bufferSubData(data: IntArray, offset: Long) = AGL.bufferSubData(target, offset, data)

    override fun getSubData(offset: Long) : IntArray {
        val array = IntArray(getBufferSize() - offset.toInt())
        AGL.getBufferSubData(target, offset, array)
        return array
    }

}






class DoubleVBO(target: BufferTarget, usage: BufferUsage, pointer: VertexAttribPointer?, data: DoubleArray?) :
    VBO<DoubleArray>(target, usage, pointer, data) {


    override fun bufferData(data: DoubleArray) = AGL.bufferData(target, data, usage)

    override fun bufferSubData(data: DoubleArray, offset: Long) = AGL.bufferSubData(target, offset, data)

    override fun getSubData(offset: Long) : DoubleArray {
        val array = DoubleArray(getBufferSize() - offset.toInt())
        AGL.getBufferSubData(target, offset, array)
        return array
    }


}






class FloatVBO(target: BufferTarget, usage: BufferUsage, pointer: VertexAttribPointer?, data: FloatArray) :
    VBO<FloatArray>(target, usage, pointer, data) {


    override fun bufferData(data: FloatArray) = AGL.bufferData(target, data, usage)

    override fun bufferSubData(data: FloatArray, offset: Long) = AGL.bufferSubData(target, offset, data)

    override fun getSubData(offset: Long) : FloatArray {
        val array = FloatArray(getBufferSize() - offset.toInt())
        AGL.getBufferSubData(target, offset, array)
        return array
    }


}






class LongVBO(target: BufferTarget, usage: BufferUsage, pointer: VertexAttribPointer?, data: LongArray) :
    VBO<LongArray>(target, usage, pointer, data) {


    override fun bufferData(data: LongArray) = AGL.bufferData(target, data, usage)

    override fun bufferSubData(data: LongArray, offset: Long) = AGL.bufferSubData(target, offset, data)

    override fun getSubData(offset: Long) : LongArray {
        val array = LongArray(getBufferSize() - offset.toInt())
        AGL.getBufferSubData(target, offset, array)
        return array
    }


}






class ShortVBO(target: BufferTarget, usage: BufferUsage, pointer: VertexAttribPointer?, data: ShortArray?) :
    VBO<ShortArray>(target, usage, pointer, data) {


    override fun bufferData(data: ShortArray) = AGL.bufferData(target, data, usage)

    override fun bufferSubData(data: ShortArray, offset: Long) = AGL.bufferSubData(target, offset, data)

    override fun getSubData(offset: Long) : ShortArray {
        val array = ShortArray(getBufferSize() - offset.toInt())
        AGL.getBufferSubData(target, offset, array)
        return array
    }


}