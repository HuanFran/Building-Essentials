package com.huanfran.buildingessentials.graphics.gl.enumeration

import org.lwjgl.opengl.GL46C

enum class DataType(val id: Int) {


    BYTE(GL46C.GL_BYTE),

    UNSIGNED_BYTE(GL46C.GL_UNSIGNED_BYTE),

    SHORT(GL46C.GL_SHORT),

    UNSIGNED_SHORT(GL46C.GL_UNSIGNED_SHORT),

    INT(GL46C.GL_INT),

    UNSIGNED_INT(GL46C.GL_UNSIGNED_INT),

    HALF_FLOAT(GL46C.GL_HALF_FLOAT),

    FLOAT(GL46C.GL_FLOAT),

    DOUBLE(GL46C.GL_DOUBLE),

    UNSIGNED_INT_2_10_10_10_REV(GL46C.GL_UNSIGNED_INT_2_10_10_10_REV),

    INT_2_10_10_10_REV(GL46C.GL_INT_2_10_10_10_REV),

    FIXED(GL46C.GL_FIXED);


}