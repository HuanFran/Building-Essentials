package com.huanfran.buildingessentials.graphics.gl.shader.exception

import com.huanfran.buildingessentials.graphics.gl.enumeration.UniformType
import com.huanfran.buildingessentials.graphics.gl.shader.ShaderProgram

class UniformTypeException(uniformName: String, program: ShaderProgram, expected: UniformType, found: UniformType) :
    UniformException("Expected type: $expected. Found type: $found. The expected type of this uniform [name=$uniformName] did not match the type found in the shader program ${programString(program)}")