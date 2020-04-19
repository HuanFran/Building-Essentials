package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.main.renderTooltip
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.ResourceLocation

open class BEButton(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        var tooltipText: String,
        var res: ResourceLocation,
        onPress: (Button) -> Unit) : Button(x, y, width, height, "", onPress) {


    /**
     * Override the render function to use custom textures and some other things.
     */
    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (!visible) return

        //Update the super class's isHovered as it was set in the (now overridden) super method.
        isHovered = isMouseOver(mouseX.toDouble(), mouseY.toDouble())

        //Make the button more transparent if the user is interacting with it.
        alpha = if(isHovered)
            if(KeyBindings.isLeftMousePressed())
                0.4F
            else
                0.7F
        else
            1.0F

        //Bind the texture for this button.
        Minecraft.getInstance().getTextureManager().bindTexture(res)

        //Enable transparency.
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha)
        RenderSystem.enableBlend()
        //RenderSystem.defaultBlendFunc()
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)

        //Tell Minecraft how to render the bound texture to the screen.
        textureBlit()

        //Render tooltip near the button. Done after rendering the button so that it is in front of the button.
        if(isHovered && tooltipText != "") renderTooltip(tooltipText, mouseX, mouseY)
    }



    /**
     * Tells Minecraft how to render the currently bound texture. This method is undocumented and it was really hard
     * to figure out what it does. So, I decided to make these buildingBlit methods that provide documentation and
     * intuitive parameter names. They act a bit oddly so I advise messing around with them until you get an intuitive
     * feel for how they affect the textures of your widgets.
     */
    fun buildingBlit(screenXStart: Int,
                     screenYStart: Int,
                     textureXStart: Int,
                     textureYStart: Int,
                     textureXEnd: Int,
                     textureYEnd: Int) =
            blit(screenXStart, screenYStart, textureXStart, textureYStart, textureXEnd, textureYEnd)



    /**
     * Version of [buildingBlit] that also takes in the [spriteSheetWidth] and [spriteSheetHeight]. Minecraft sets
     * these to 256 by default in other blit methods.
     */
    fun buildingBlit(screenXStart: Int,
                     screenYStart: Int,
                     textureXStart: Int,
                     textureYStart: Int,
                     textureXEnd: Int,
                     textureYEnd: Int,
                     spriteSheetWidth: Int,
                     spriteSheetHeight: Int) =
            blit(screenXStart, screenYStart, 0, textureXStart.toFloat(), textureYStart.toFloat(), textureXEnd, textureYEnd, spriteSheetWidth, spriteSheetHeight)



    /**
     * A blit method specifically for these [BEButton]s. Each button has an individual texture, so the sprite
     * sheet dimensions are set to the width and height of the button.
     */
    private fun textureBlit() =
        buildingBlit(x, y, 0, 0, width, height, width, height)


}