package com.huanfran.buildingessentials.rendering

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.item.StaffOfMirrors
import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.utils.extensions.rayTrace
import com.huanfran.buildingessentials.utils.extensions.toVector3
import com.huanfran.buildingessentials.utils.internal.heldItem
import com.huanfran.buildingessentials.utils.internal.player
import com.huanfran.mirror.MirrorController
import com.huanfran.mirror.Mirrors
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.util.math.RayTraceResult

/**
 * Renders [MirrorController]s globally. Rendering is not confined to tile entities, so the render can be viewed by
 * any player anywhere.
 */
object GlobalMirrorRenderer : Renderer() {


    fun renderMirrors(stack: MatrixStack, partialTicks: Float) {
        if(!Mirrors.mirroringEnabled || !Mirrors.mirrorRenderingEnabled) return

        val world = Minecraft.getInstance().player?.world ?: return
        val handler = Mirrors.getHandler(world) ?: return

        //The mirror that the player is looking at (null if they are not looking at a mirror or are in a menu).
        val lookingAt = if(Minecraft.getInstance().currentScreen == null && heldItem().item == StaffOfMirrors)
            handler.lookingAt(player(), playerPos())
        else
            null

        //Render a mirror node where the player is looking.
        if(heldItem().item == StaffOfMirrors)
            renderPlacementNode(stack, partialTicks)

        //Logic to remove a mirror with left click while holding the staff of mirrors. onEntitySwing can't be used as
        //that also triggers on right click. Minecraft doesn't have a method for onLeftClick for items.
        lookingAt?.let {
            if(KeyBindings.isLeftMousePressed())
                StaffOfMirrors.tryRemove(it)
        }

        //Render the current node (the one that is not part of a controller, if it exists)
        handler.currentPos?.let { renderMirrorNode(stack, it, 1) }

        //Render each mirror.
        handler.controllers.forEach { renderMirrorGlobally(it, playerPos(), stack, lookingAt == it) }

        //Render each mirror's nodes.
        handler.controllers.forEach {
            renderMirrorNode(stack, it.v0, 2)
            renderMirrorNode(stack, it.v1, 2)
        }
    }



    private fun renderPlacementNode(stack: MatrixStack, partialTicks: Float) = player().rayTrace(5.0, partialTicks).let {
        if(it.type == RayTraceResult.Type.BLOCK) renderMirrorNode(stack, it.hitVec.toVector3().roundToHalf(), 0)

        val t = it.hitVec.toVector3().roundToHalf()

        //renderPipeTest(stack, t, t + Vector3(3.0, 0.0, 0.0))
        //renderPipeTest(stack, t, t + Vector3(0.0, 3.0, 0.0))
        //renderPipeTest(stack, t, t + Vector3(0.0, 0.0, 3.0))
    }



    private fun renderPipeTest(stack: MatrixStack, start: Vector3, end: Vector3) {
        setup()
        stack.push()

        stack.translate(start - playerPos())

        enableTransparency()

        beginQuads()

        renderPipe(stack.last.matrix, Vector3(0.0, 0.0, 0.0), end - start, 0.1)

        tessellator.draw()

        RenderSystem.enableTexture()

        stack.pop()
    }



    private fun renderMirrorNode(stack: MatrixStack, pos: Vector3, mode: Int) {
        setup()
        stack.push()

        stack.translate(pos - playerPos())

        enableTransparency()

        when(mode) {
            0 -> colour(0.0, 0.8, 0.6, 1.0)
            1 -> colour(0.8, 0.6, 0.3, 1.0)
            2 -> colour(0.4, 0.4, 1.0, 1.0)
        }

        beginQuads()

        val offset = 0.05
        val width2 = 0.15

        renderCube(stack.last.matrix, offset, Vector3(0.0, 0.0, 0.0), width2)

        tessellator.draw()

        RenderSystem.enableTexture()

        stack.pop()
    }



    private fun renderMirrorGlobally(controller: MirrorController, playerPos: Vector3, stack: MatrixStack, lookingAt: Boolean) {
        setup()
        stack.push()

        //Centre on the controller.
        stack.translate(controller.centre - playerPos)

        //Change the colour if the player is looking at the mirror.
        if(lookingAt)
            colour(0.4, 0.0, 1.0, 0.6)
        else
            colour(0.0, 0.0, 0.8, 0.4)
        
        enableTransparency()

        val matrix = stack.last.matrix

        beginQuads()

        //Render the mirror barrier using the end0 and end1 points of the controller.
        with(controller) {
            val offset = 0.001

            //Render so that the mirror is always 'facing' the player to avoid weird graphical glitches.
            if(isOnRightSide(playerPos)) {
                pos(matrix, end1.x + offset, -height2, end1.z + offset)
                pos(matrix, end1.x + offset, height2, end1.z + offset)
                pos(matrix, end0.x + offset, height2, end0.z + offset)
                pos(matrix, end0.x + offset, -height2, end0.z + offset)
            } else {
                pos(matrix, end0.x - offset, -height2, end0.z - offset)
                pos(matrix, end0.x - offset, height2, end0.z - offset)
                pos(matrix, end1.x - offset, height2, end1.z - offset)
                pos(matrix, end1.x - offset, -height2, end1.z - offset)
            }
        }

        tessellator.draw()

        cleanup()

        stack.pop()
    }


}