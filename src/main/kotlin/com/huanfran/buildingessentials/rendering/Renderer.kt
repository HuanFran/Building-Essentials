package com.huanfran.buildingessentials.rendering

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.toVector3
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Matrix4f
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.math.BlockPos
import org.lwjgl.opengl.GL11

abstract class Renderer {


    fun tessellator() = Tessellator.getInstance()



    fun builder() = tessellator().buffer



    /**
     * Every quadruple of vertices is considered a quad.
     */
    fun beginQuads() = builder().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)



    fun colour(r: Double, g: Double, b: Double, a: Double) =
            RenderSystem.color4f(r.toFloat(), g.toFloat(), b.toFloat(), a.toFloat())



    fun enableTransparency() {
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
    }



    fun BufferBuilder.pos(matrix: Matrix4f, v: Vector3) =
            pos(matrix, v.x.toFloat(), v.y.toFloat(), v.z.toFloat()).endVertex()

    fun BufferBuilder.pos(matrix: Matrix4f, x: Double, y: Double, z: Double) =
            pos(matrix, x.toFloat(), y.toFloat(), z.toFloat() ).endVertex()

    fun BufferBuilder.quad(matrix: Matrix4f, v0: Vector3, v1: Vector3, v2: Vector3, v3: Vector3) {
        pos(matrix, v0)
        pos(matrix, v1)
        pos(matrix, v2)
        pos(matrix, v3)
    }



    fun playerPos() = Minecraft.getInstance().gameRenderer.activeRenderInfo.projectedView.toVector3()



    fun renderCube(builder: BufferBuilder, matrix: Matrix4f, offset: Double, v: Vector3, width2: Double) {
        val x0 = v.x - width2 - offset
        val x1 = v.x + width2 + offset
        val y0 = v.y - width2 - offset
        val y1 = v.y + width2 + offset
        val z0 = v.z - width2 - offset
        val z1 = v.z + width2 + offset

        //Lower half
        val v0 = Vector3(x0, y0, z0)
        val v1 = Vector3(x1, y0, z0)
        val v2 = Vector3(x1, y0, z1)
        val v3 = Vector3(x0, y0, z1)

        //Upper half
        val v4 = Vector3(x0, y1, z0)
        val v5 = Vector3(x1, y1, z0)
        val v6 = Vector3(x1, y1, z1)
        val v7 = Vector3(x0, y1, z1)

        //Where y = 0
        builder.quad(matrix, v0, v1, v2, v3)

        //Where y = 1
        builder.quad(matrix, v4, v5, v6, v7)

        //Where x = 0
        builder.quad(matrix, v0, v4, v7, v3)

        //Where x = 1
        builder.quad(matrix, v1, v5, v6, v2)

        //Where z = 0
        builder.quad(matrix, v0, v1, v5, v4)

        //Where z = 1
        builder.quad(matrix, v3, v2, v6, v7)
    }



    /**
     *
     */
    fun renderPos(builder: BufferBuilder, matrix: Matrix4f, offset: Float, relativePos: BlockPos) {
        val x0 = relativePos.x.toFloat()
        val x1 = relativePos.x.toFloat() + 1F
        val y0 = relativePos.y.toFloat()
        val y1 = relativePos.y.toFloat() + 1F
        val z0 = relativePos.z.toFloat()
        val z1 = relativePos.z.toFloat() + 1F


        builder.pos(matrix, x0, y1 + offset, z0).endVertex()
        builder.pos(matrix, x1, y1 + offset, z0).endVertex()
        builder.pos(matrix, x1, y1 + offset, z1).endVertex()
        builder.pos(matrix, x0, y1 + offset, z1).endVertex()

        builder.pos(matrix, x0, y0 - offset, z0).endVertex()
        builder.pos(matrix, x1, y0 - offset, z0).endVertex()
        builder.pos(matrix, x1, y0 - offset, z1).endVertex()
        builder.pos(matrix, x0, y0 - offset, z1).endVertex()


        builder.pos(matrix, x1 + offset, y0, z0).endVertex()
        builder.pos(matrix, x1 + offset, y1, z0).endVertex()
        builder.pos(matrix, x1 + offset, y1, z1).endVertex()
        builder.pos(matrix, x1 + offset, y0, z1).endVertex()

        builder.pos(matrix, x0 - offset, y0, z0).endVertex()
        builder.pos(matrix, x0 - offset, y1, z0).endVertex()
        builder.pos(matrix, x0 - offset, y1, z1).endVertex()
        builder.pos(matrix, x0 - offset, y0, z1).endVertex()


        builder.pos(matrix, x0, y0, z1 + offset).endVertex()
        builder.pos(matrix, x1, y0, z1 + offset).endVertex()
        builder.pos(matrix, x1, y1, z1 + offset).endVertex()
        builder.pos(matrix, x0, y1, z1 + offset).endVertex()

        builder.pos(matrix, x0, y0, z0 - offset).endVertex()
        builder.pos(matrix, x1, y0, z0 - offset).endVertex()
        builder.pos(matrix, x1, y1, z0 - offset).endVertex()
        builder.pos(matrix, x0, y1, z0 - offset).endVertex()
    }



    /*
    Extensions
     */



    fun MatrixStack.translate(v: Vector3) = this.translate(v.x, v.y, v.z)


}