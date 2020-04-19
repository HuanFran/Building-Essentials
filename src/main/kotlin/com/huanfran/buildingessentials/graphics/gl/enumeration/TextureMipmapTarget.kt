package com.huanfran.buildingessentials.graphics.gl.enumeration

import org.lwjgl.opengl.GL46C

enum class TextureMipmapTarget(val id: Int) {


    TEXTURE_1D(GL46C.GL_TEXTURE_1D),

    TEXTURE_2D(GL46C.GL_TEXTURE_2D),

    TEXTURE_3D(GL46C.GL_TEXTURE_3D),

    TEXTURE_1D_ARRAY(GL46C.GL_TEXTURE_1D_ARRAY),

    TEXTURE_2D_ARRAY(GL46C.GL_TEXTURE_2D_ARRAY),

    TEXTURE_CUBE_MAP(GL46C.GL_TEXTURE_CUBE_MAP);



    companion object {


        fun fromTextureTarget(target: TextureTarget) : TextureMipmapTarget? {
            for(v in values())
                if(target.id == v.id)
                    return v

            return null
        }


    }

}