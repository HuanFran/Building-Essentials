package com.huanfran.buildingessentials.graphics.gl.shader.exception

import com.huanfran.buildingessentials.graphics.gl.shader.ShaderProgram

open class UniformException(message: String) : Exception(message) {


    companion object {
        fun programString(program: ShaderProgram) = "[name=${program.name}, type=${program.javaClass.name}]"
    }


}