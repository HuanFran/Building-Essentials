package com.huanfran.buildingessentials.rendering

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.item.StaffOfMirrors
import com.huanfran.buildingessentials.main.toVector3
import com.huanfran.buildingessentials.tile.mirror.MirrorController
import com.huanfran.buildingessentials.tile.mirror.Mirrors
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

object GlobalMirrorRenderer : Renderer() {


    fun renderMirrors(stack: MatrixStack) {
        if(!Mirrors.mirroringEnabled || !Mirrors.mirrorRenderingEnabled) return

        //Some info from the player is needed.
        val player = Minecraft.getInstance().player ?: return

        //Where the player is in the world.
        val playerPos = Minecraft.getInstance().gameRenderer.activeRenderInfo.projectedView.toVector3()

        //The mirror that the player is looking at (null if they are not looking at a mirror).
        val lookingAt = if(player.heldItemMainhand.item == StaffOfMirrors)
            Mirrors.clientMirrorHandler.lookingAt(player, playerPos)
        else
            null

        //Render each mirror. Render it differently if the player is holding the staff of mirrors and is looking at it.
        Mirrors.clientMirrorHandler.controllers.forEach { renderMirrorGlobally(it, playerPos, stack, lookingAt == it) }
    }



    fun renderMirrorNode(stack: MatrixStack, pos: Vector3, mode: Int) {
        stack.push()

        stack.translate(pos - playerPos())

        RenderSystem.enableDepthTest()
        RenderSystem.disableTexture()

        enableTransparency()

        when(mode) {
            0 -> colour(0.0, 0.8, 0.6, 1.0)
            1 -> colour(0.8, 0.6, 0.3, 1.0)
            2 -> colour(0.4, 0.4, 1.0, 1.0)
        }

        enableTransparency()

        beginQuads()

        val offset = 0.05
        val width2 = 0.15

        renderCube(builder(), stack.last.matrix, offset, Vector3(0.0, 0.0, 0.0), width2)
        tessellator().draw()



        RenderSystem.enableTexture()

        stack.pop()
    }


    val ALPHA = 0.4F
    val BLUE = 0.8F

    private fun renderMirrorGlobally(controller: MirrorController, playerPos: Vector3, stack: MatrixStack, lookingAt: Boolean) {
        //push indicates that any changes made to the stack will only be temporary. The stack's state can be restored
        //with stack.pop(), which is called after rendering is completed.
        stack.push()

        val playerPosRelative = controller.centre - playerPos

        //Translate by the difference in position of the controller's centre and the player's position so that
        //rendering is done centred on the controller.
        stack.translate(playerPosRelative)

        //Configuring rendering.
        RenderSystem.enableDepthTest()
        RenderSystem.disableTexture()

        //Set the colour. Make it redder if the player is looking at it.
        if(lookingAt)
            RenderSystem.color4f(BLUE, 0.0F, BLUE, ALPHA)
        else
            RenderSystem.color4f(0.0F, 0.0F, BLUE, ALPHA)


        //This enables transparency, however if two mirrors intersect, transparency will be a bit weird.
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)

        //Getting the things that will render the controller.
        val tessellator = Tessellator.getInstance()
        val builder = tessellator.buffer

        //Store the last matrix from the stack to use in rendering.
        val matrix = stack.last.matrix

        //The start of the actual rendering process. Legacy OpenGL, no idea what it actually does.
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)

        //Render the mirror barrier using the end0 and end1 points of the controller.
        with(controller) {
            //The offset prevents the mirror render from overlaying directly onto block faces. If that happens, the
            //block's texture and the mirror will combine and glitch out. This value very small so as not to be noticeable.
            val offset = 0.001F
            
            /*
            Rendering so that the mirror render always faces the player. Taking the dot product of the mirror
            controller's normal direction and the eye position of the player will give the orientation of the player
            relative to the controller. If the result is positive, the player is on the right side of the mirror. If it
            is negative, the player is on the left side. The offset can then be set accordingly to place the mirror in
            FRONT of any blocks whose faces it may have been overlaying. This avoids any graphical glitches and also
            allows the player to see the mirror even when it does overlay blocks. Swapping end0 and end1 in the left and
            right versions of the mirror prevents some unique rendering issues associated with transparency.
            Transparency works best when the mirror 'faces' the player.
             */
            if(isOnRightSide(playerPos)) {
                builder.pos(matrix, end1.x.toFloat() + offset, -height2.toFloat(), end1.z.toFloat() + offset).endVertex()
                builder.pos(matrix, end1.x.toFloat() + offset, height2.toFloat(), end1.z.toFloat() + offset).endVertex()
                builder.pos(matrix, end0.x.toFloat() + offset, height2.toFloat(), end0.z.toFloat() + offset).endVertex()
                builder.pos(matrix, end0.x.toFloat() + offset, -height2.toFloat(), end0.z.toFloat() + offset).endVertex()
            } else {
                builder.pos(matrix, end0.x.toFloat() - offset, -height2.toFloat(), end0.z.toFloat() - offset).endVertex()
                builder.pos(matrix, end0.x.toFloat() - offset, height2.toFloat(), end0.z.toFloat() - offset).endVertex()
                builder.pos(matrix, end1.x.toFloat() - offset, height2.toFloat(), end1.z.toFloat() - offset).endVertex()
                builder.pos(matrix, end1.x.toFloat() - offset, -height2.toFloat(), end1.z.toFloat() - offset).endVertex()
            }
        }

        //Final step to rendering.
        tessellator.draw()

        //Restore the initial state.
        RenderSystem.enableTexture()

        //Restore the stack's state to prevent rendering bugs elsewhere in Minecraft.
        stack.pop()
    }


}