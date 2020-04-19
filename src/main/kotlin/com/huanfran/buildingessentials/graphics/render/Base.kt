package com.huanfran.buildingessentials.graphics.render

import com.huanfran.buildingessentials.graphics.gl.AGL
import com.huanfran.buildingessentials.graphics.gl.enumeration.DrawMode
import com.huanfran.buildingessentials.graphics.gl.enumeration.IndicesDataType
import com.huanfran.buildingessentials.graphics.maths.Colour
import com.huanfran.buildingessentials.graphics.mesh.Mesh
import com.huanfran.buildingessentials.graphics.shaders.BuildingEssentialsSP
import net.minecraft.client.renderer.Matrix4f

class Base(var mesh: Mesh, var program: BuildingEssentialsSP, var colour: Colour = Colour(1.0, 1.0, 1.0, 1.0)) {


    fun render(transform: Matrix4f) {
        program.doBound {
            program.setUniforms(colour, transform)

            mesh.doBound {
                AGL.drawElements(DrawMode.TRIANGLES, mesh.indicesCount, IndicesDataType.UNSIGNED_INT, 0)
            }
        }
    }


}