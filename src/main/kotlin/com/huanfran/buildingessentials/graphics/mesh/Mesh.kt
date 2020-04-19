package com.huanfran.buildingessentials.graphics.mesh

import com.huanfran.buildingessentials.graphics.gl.vbo.Bindable
import com.huanfran.buildingessentials.graphics.gl.vbo.vao.MeshVAO
import com.huanfran.buildingessentials.graphics.maths.Maths
import com.huanfran.buildingessentials.graphics.maths.Vector3

/**
 * Anything drawn within a window is represented by this Mesh class.
 */
class Mesh(vertices: DoubleArray, indices: IntArray) : Bindable {


    /*
    Variables
     */



    /**
     * The Vertex Array Object (VAO) bundles buffer objects and vertex attribute arrays to make binding easier.
     */
    private val vao = MeshVAO(vertices, indices)

    /**
     * The size of the indices VBO. I.e. the number of triangles in the mesh multiplied by 3. Used in draw calls.
     */
    var indicesCount = indices.size



    /*
    Overridden Functions
     */



    override fun bind() = vao.bind()



    override fun unbind() = vao.unbind()



    /*
    Functions
     */



    fun updateVertices(vertices: List<Vector3>) {
        vao.verticesVBO.bufferSubData(Maths.toDoubleArray(vertices), 0);
    }


}