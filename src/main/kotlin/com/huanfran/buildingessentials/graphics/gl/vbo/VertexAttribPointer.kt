package com.huanfran.buildingessentials.graphics.gl.vbo

import com.huanfran.buildingessentials.graphics.gl.AGL
import com.huanfran.buildingessentials.graphics.gl.enumeration.DataType
import org.lwjgl.opengl.GL46C

class VertexAttribPointer(val index: Int,
                          val size: Int,
                          val type: DataType,
                          val normalised: Boolean,
                          val stride: Int,
                          val pointer: Long) : Bindable {


    fun set() = AGL.vertexAttribPointer(index, size, type, normalised, stride, pointer)

    override fun bind() = GL46C.glEnableVertexAttribArray(index)

    override fun unbind() = GL46C.glDisableVertexAttribArray(index)


}



val VERTICES3D_ATTRIB_POINTER = VertexAttribPointer(0, 3, DataType.DOUBLE, false, 0, 0)

val VERTICES2D_ATTRIB_POINTER = VertexAttribPointer(0, 2, DataType.DOUBLE, false, 0, 0)

val COLOURS_ATTRIB_POINTER = VertexAttribPointer(1, 3, DataType.DOUBLE, false, 0, 0)

