package com.huanfran.buildingessentials.rendering

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.utils.extensions.toVector3
import com.huanfran.buildingessentials.utils.internal.player
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Matrix4f
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.math.BlockRayTraceResult
import org.lwjgl.opengl.GL11

abstract class Renderer {


    /*
    Variables
     */



    val tessellator = Tessellator.getInstance()

    val builder = tessellator.buffer



    /*
    Convenience functions
     */



    /**
     * Every quadruple of vertices is considered a quad.
     */
    fun beginQuads() = builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)



    fun colour(r: Double, g: Double, b: Double, a: Double) =
            RenderSystem.color4f(r.toFloat(), g.toFloat(), b.toFloat(), a.toFloat())



    fun setup() {
        RenderSystem.enableDepthTest()
        RenderSystem.disableTexture()
    }



    fun cleanup() {
        RenderSystem.disableTexture()
    }



    fun enableTransparency() {
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
    }



    /*
    Drawing
     */



    fun pos(matrix: Matrix4f, v: Vector3) =
            builder.pos(matrix, v.x.toFloat(), v.y.toFloat(), v.z.toFloat()).endVertex()

    fun pos(matrix: Matrix4f, x: Double, y: Double, z: Double) =
            builder.pos(matrix, x.toFloat(), y.toFloat(), z.toFloat() ).endVertex()

    fun quad(matrix: Matrix4f, v0: Vector3, v1: Vector3, v2: Vector3, v3: Vector3) {
        pos(matrix, v0)
        pos(matrix, v1)
        pos(matrix, v2)
        pos(matrix, v3)
    }



    /*
    Specific shape drawing
     */



    fun renderCube(matrix: Matrix4f, offset: Double, v: Vector3, width2: Double) {
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
        quad(matrix, v0, v1, v2, v3)

        //Where y = 1
        quad(matrix, v4, v5, v6, v7)

        //Where x = 0
        quad(matrix, v0, v4, v7, v3)

        //Where x = 1
        quad(matrix, v1, v5, v6, v2)

        //Where z = 0
        quad(matrix, v0, v1, v5, v4)

        //Where z = 1
        quad(matrix, v3, v2, v6, v7)
    }



    /*
    Player info
     */



    fun playerPos() = Minecraft.getInstance().gameRenderer.activeRenderInfo.projectedView.toVector3()

    fun lookVector() = player().lookVec.toVector3()

    fun rayTrace(range: Double, partialTicks: Float = 0F, interactsWithFluids: Boolean = false) =
            player().pick(range, partialTicks, interactsWithFluids) as BlockRayTraceResult



    /*
    Extensions
     */



    fun MatrixStack.translate(v: Vector3) = this.translate(v.x, v.y, v.z)


}