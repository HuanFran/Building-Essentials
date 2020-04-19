package com.huanfran.buildingessentials.graphics.gl.shader.exception

import com.huanfran.buildingessentials.graphics.gl.enumeration.ShaderType
import com.huanfran.buildingessentials.graphics.gl.shader.ShaderDirectory

class ShaderNotFoundException(programName: String, directory: ShaderDirectory, type: ShaderType) :
    Exception("No shader of type $type was found for this shader program [name=$programName] in the shader directory [name=${directory.name}, path=${directory.directory}")