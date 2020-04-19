package com.huanfran.buildingessentials.graphics.gl.enumeration

import com.huanfran.buildingessentials.graphics.gl.enumeration.identification.GLParameter
import org.lwjgl.opengl.GL46C

enum class BufferParameter(val id: Int) : GLParameter {


    BUFFER_SIZE(GL46C.GL_BUFFER_SIZE),

    BUFFER_USAGE(GL46C.GL_BUFFER_USAGE),

    BUFFER_ACCESS(GL46C.GL_BUFFER_ACCESS),

    BUFFER_MAPPED(GL46C.GL_BUFFER_MAPPED),

    BUFFER_ACCESS_FLAGS(GL46C.GL_BUFFER_ACCESS_FLAGS),

    BUFFER_MAP_LENGTH(GL46C.GL_BUFFER_MAP_LENGTH),

    BUFFER_MAP_OFFSET(GL46C.GL_BUFFER_MAP_OFFSET),

    BUFFER_IMMUTABLE_STORAGE(GL46C.GL_BUFFER_IMMUTABLE_STORAGE),

    BUFFER_STORAGE_FLAGS(GL46C.GL_BUFFER_STORAGE_FLAGS);


}