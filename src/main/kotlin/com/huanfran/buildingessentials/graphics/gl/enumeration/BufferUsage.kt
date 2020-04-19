package com.huanfran.buildingessentials.graphics.gl.enumeration

import com.huanfran.buildingessentials.graphics.gl.enumeration.identification.GLUsage
import org.lwjgl.opengl.GL46C

enum class BufferUsage(val id: Int) : GLUsage {


    DYNAMIC_COPY(GL46C.GL_DYNAMIC_COPY),

    DYNAMIC_DRAW(GL46C.GL_DYNAMIC_DRAW),

    DYNAMIC_READ(GL46C.GL_DYNAMIC_READ),



    STATIC_COPY(GL46C.GL_STATIC_COPY),

    STATIC_DRAW(GL46C.GL_STATIC_DRAW),

    STATIC_READ(GL46C.GL_STATIC_READ),



    STREAM_COPY(GL46C.GL_STREAM_COPY),

    STREAM_DRAW(GL46C.GL_STREAM_DRAW),

    STREAM_READ(GL46C.GL_STREAM_READ);


}