package com.huanfran.buildingessentials.graphics.gl.vbo.vao

import com.huanfran.buildingessentials.graphics.gl.enumeration.BufferTarget
import com.huanfran.buildingessentials.graphics.gl.enumeration.BufferUsage
import com.huanfran.buildingessentials.graphics.gl.vbo.DoubleVBO
import com.huanfran.buildingessentials.graphics.gl.vbo.IntVBO
import com.huanfran.buildingessentials.graphics.gl.vbo.VERTICES2D_ATTRIB_POINTER

/**
 * The VAO of a mesh contains a vertices VBO and an indices VBO. The [verticesVBO] holds the points that make up the
 * mesh. The [indicesVBO] holds the data necessary for converting those points into a displayable format. In simpler
 * terms, the indices describe triangles of points taken from the vertices VBO.
 */
class MeshVAO(vertices: DoubleArray, indices: IntArray) : VAO() {


    /*
    Variables
     */



    var verticesVBO: DoubleVBO

    var indicesVBO: IntVBO



    /*
    Initialisation
     */



    init {
        super.bind()
        verticesVBO = generateVerticesVBO(vertices)
        indicesVBO = generateIndicesVBO(indices)
        super.unbind()
    }



    /*
    Overridden Methods
     */



    override fun bind() {
        super.bind()
        verticesVBO.pointer?.bind()
        indicesVBO.bind()
    }



    override fun unbind() {
        super.unbind()
        verticesVBO.pointer?.unbind()
        indicesVBO.unbind()
    }


}




/*
Static methods for VBO generation.
 */



private fun generateVerticesVBO(vertices: DoubleArray) = DoubleVBO(
    BufferTarget.ARRAY_BUFFER, BufferUsage.STATIC_DRAW, VERTICES2D_ATTRIB_POINTER, vertices)

private fun generateIndicesVBO(indices: IntArray) =
    IntVBO(BufferTarget.ELEMENT_ARRAY_BUFFER, BufferUsage.STATIC_DRAW, null, indices)