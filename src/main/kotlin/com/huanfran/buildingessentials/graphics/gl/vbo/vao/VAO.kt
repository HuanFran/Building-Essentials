package com.huanfran.buildingessentials.graphics.gl.vbo.vao

import com.huanfran.buildingessentials.graphics.gl.AGL
import com.huanfran.buildingessentials.graphics.gl.vbo.Bindable
import org.lwjgl.opengl.GL46C

abstract class VAO : Bindable {


    private val vao = GL46C.glGenVertexArrays()



    /*
    Overridden methods
     */



    override fun bind() = GL46C.glBindVertexArray(vao)

    override fun unbind() = AGL.unbindVAO()


}