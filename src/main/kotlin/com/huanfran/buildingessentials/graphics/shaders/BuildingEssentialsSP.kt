package com.huanfran.buildingessentials.graphics.shaders

import com.huanfran.buildingessentials.graphics.gl.shader.Shader
import com.huanfran.buildingessentials.graphics.gl.shader.ShaderProgram
import com.huanfran.buildingessentials.graphics.maths.Colour
import net.minecraft.client.renderer.Matrix4f
import java.nio.FloatBuffer

open class BuildingEssentialsSP(name: String,
                           vertex: Shader,
                           fragment: Shader) : ShaderProgram(name, vertex, fragment, null) {


    var colourUniform = generateDoubleVec4Uniform("colour")

    var transformUniform = generateFloatMat4Uniform("transform")



    open fun setUniforms(c: Colour, transform: Matrix4f) {
        colourUniform.setUniform(c.r, c.g, c.b, c.a)
        transformUniform.setUniform(false, FloatBuffer.allocate(16).apply { transform.write(this) }.array())
    }


}