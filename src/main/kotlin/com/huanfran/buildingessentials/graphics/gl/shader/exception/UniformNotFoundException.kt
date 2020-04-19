package com.huanfran.buildingessentials.graphics.gl.shader.exception

import com.huanfran.buildingessentials.graphics.gl.shader.ShaderProgram
import com.huanfran.buildingessentials.graphics.gl.shader.exception.UniformException

class UniformNotFoundException(uniformName: String, program: ShaderProgram) :
    UniformException("The uniform [name=$uniformName] could not be found in the shader program ${programString(program)}")