package com.huanfran.buildingessentials.graphics.gl.enumeration

import com.huanfran.buildingessentials.graphics.gl.enumeration.identification.GLTarget
import org.lwjgl.opengl.GL46C

enum class ShaderType(val id: Int) : GLTarget {


    VERTEX_SHADER(GL46C.GL_VERTEX_SHADER),

    FRAGMENT_SHADER(GL46C.GL_FRAGMENT_SHADER),

    GEOMETRY_SHADER(GL46C.GL_GEOMETRY_SHADER);


}