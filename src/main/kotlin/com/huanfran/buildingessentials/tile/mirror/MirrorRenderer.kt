package com.huanfran.buildingessentials.tile.mirror

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

class MirrorRenderer : TileEntityRenderer<MirrorTileEntity>(TileEntityRendererDispatcher.instance) {


    override fun render(entity: MirrorTileEntity,
                        partialTicks: Float,
                        stack: MatrixStack,
                        buffer: IRenderTypeBuffer,
                        combinedLightIn: Int,
                        combinedOverlayIn: Int) {

        if(!Mirrors.mirroringEnabled || !Mirrors.mirrorRenderingEnabled) return

        //Only render if the controller is present.
        entity.controller?.let {
            stack.push()

            //Make rendering relative to the centre of the controller. Initially, it was at the centre of the entity
            //that holds the controller.
            stack.translate(it.centre.x - entity.pos.x, it.midHeight - entity.pos.y, it.centre.y - entity.pos.z)

            //Configuring rendering.
            RenderSystem.enableDepthTest()
            RenderSystem.disableTexture()
            RenderSystem.color4f(0.0F, 0.0F, 0.4F, 0.4F)
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)

            //Getting the things that will render the controller.
            val tessellator = Tessellator.getInstance()
            val builder = tessellator.buffer

            //Store the last matrix from the stack to use in rendering.
            val matrix = stack.last.matrix

            //The start of the actual rendering process. Legacy OpenGL, no idea what it actually does.
            builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)

            //The y offset, leave at zero.
            val y = 0

            //Render things.
            with(it) {
                //Render the mirror barrier.
                builder.pos(matrix, end0.x.toFloat(), y.toFloat() - height2.toFloat(), end0.y.toFloat()).endVertex()
                builder.pos(matrix, end0.x.toFloat(), y.toFloat() + height2.toFloat(), end0.y.toFloat()).endVertex()
                builder.pos(matrix, end1.x.toFloat(), y.toFloat() + height2.toFloat(), end1.y.toFloat()).endVertex()
                builder.pos(matrix, end1.x.toFloat(), y.toFloat() - height2.toFloat(), end1.y.toFloat()).endVertex()
            }

            //Final step to rendering.
            tessellator.draw()

            //Restore the initial state.
            RenderSystem.enableTexture()
            stack.pop()
        }

    }


    override fun isGlobalRenderer(entity: MirrorTileEntity) = true


}