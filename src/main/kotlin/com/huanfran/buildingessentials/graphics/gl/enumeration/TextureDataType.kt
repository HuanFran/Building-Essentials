package com.huanfran.buildingessentials.graphics.gl.enumeration

import org.lwjgl.opengl.GL46C

enum class TextureDataType(val id: Int) {


    UNSIGNED_BYTE(GL46C.GL_UNSIGNED_BYTE),

    BYTE(GL46C.GL_BYTE),

    UNSIGNED_SHORT(GL46C.GL_UNSIGNED_SHORT),

    SHORT(GL46C.GL_SHORT),

    UNSIGNED_INT(GL46C.GL_UNSIGNED_INT),

    INT(GL46C.GL_INT),

    HALF_FLOAT(GL46C.GL_HALF_FLOAT),

    FLOAT(GL46C.GL_FLOAT),

    UNSIGNED_BYTE_3_3_2(GL46C.GL_UNSIGNED_BYTE_3_3_2),

    UNSIGNED_BYTE_2_3_3_REV(GL46C.GL_UNSIGNED_BYTE_2_3_3_REV),

    UNSIGNED_SHORT_5_6_5(GL46C.GL_UNSIGNED_SHORT_5_6_5),

    UNSIGNED_SHORT_5_6_5_REV(GL46C.GL_UNSIGNED_SHORT_5_6_5_REV),

    UNSIGNED_SHORT_4_4_4_4(GL46C.GL_UNSIGNED_SHORT_4_4_4_4),

    UNSIGNED_SHORT_4_4_4_4_REV(GL46C.GL_UNSIGNED_SHORT_4_4_4_4_REV),

    UNSIGNED_SHORT_5_5_5_1(GL46C.GL_UNSIGNED_SHORT_5_5_5_1),

    UNSIGNED_SHORT_1_5_5_5_REV(GL46C.GL_UNSIGNED_SHORT_1_5_5_5_REV),

    UNSIGNED_INT_8_8_8_8(GL46C.GL_UNSIGNED_INT_8_8_8_8),

    UNSIGNED_INT_8_8_8_8_REV(GL46C.GL_UNSIGNED_INT_8_8_8_8_REV),

    UNSIGNED_INT_10_10_10_2(GL46C.GL_UNSIGNED_INT_10_10_10_2),

    UNSIGNED_INT_2_10_10_10_REV(GL46C.GL_UNSIGNED_INT_2_10_10_10_REV),

    UNSIGNED_INT_24_8(GL46C.GL_UNSIGNED_INT_24_8),

    UNSIGNED_INT_10F_11F_11F_REV(GL46C.GL_UNSIGNED_INT_10F_11F_11F_REV),

    UNSIGNED_INT_5_9_9_9_REV(GL46C.GL_UNSIGNED_INT_5_9_9_9_REV),

    FLOAT_32_UNSIGNED_INT_24_8_REV(GL46C.GL_FLOAT_32_UNSIGNED_INT_24_8_REV);


}