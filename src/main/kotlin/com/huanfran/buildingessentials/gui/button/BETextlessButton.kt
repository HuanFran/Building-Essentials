package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.gui.BEScreen
import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.utils.BETextureLocation
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.widget.button.AbstractButton
import net.minecraft.client.gui.widget.button.Button

abstract class BETextlessButton(width: Int, height: Int, var tooltipText: String, var res: BETextureLocation) : AbstractButton(0, 0, width, height, "") {


    /**
     * The alpha value for this button in its normal state.
     */
    var normalAlpha = 1.0F

    /**
     * The alpha value for this button when the mouse is hovered over it.
     */
    var hoveredAlpha = 0.7F

    /**
     * The alpha value for this button when the user is clicking on it.
     */
    var clickedAlpha = 0.4F



    /**
     * Override the render function to use custom textures and some other things.
     */
    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (!visible) return

        //Update the super class's isHovered as it was set in the (now overridden) super method.
        isHovered = isMouseOver(mouseX.toDouble(), mouseY.toDouble())

        //This does nothing by default but can be implemented by sub-classes.
        if(isHovered) onHovered()

        //Make the button more transparent if the user is interacting with it (or don't, depending on the values)
        alpha = if(isHovered)
            if(KeyBindings.isLeftMousePressed())
                clickedAlpha
            else
                hoveredAlpha
        else
            normalAlpha

        //Bind the texture for this button.
        Minecraft.getInstance().getTextureManager().bindTexture(res)

        //Enable transparency.
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha)
        RenderSystem.enableBlend()

        //Tell Minecraft how to render the bound texture to the screen.
        doBlit()

        //Render tooltip near the button. Done after rendering the button so that it is in front of the button.
        if(isHovered && provideTooltipText() != "")
            BEScreen.tooltipToRender = Triple(provideTooltipText(), mouseX, mouseY)
    }



    /**
     * Constructs a [BEButtonComponent] with this button as its parameter.
     */
    fun toComponent() = BEButtonComponent(this)



    /*
    Open/Abstract Functions
     */



    /**
     * Provides the tooltip that will be rendered when this button is hovered over. This is set to [tooltipText] by
     * default.
     */
    open fun provideTooltipText() = tooltipText



    /**
     * Tells Minecraft how to render the currently bound texture to the screen. This is set to [fullTextureBlit] by default.
     */
    open fun doBlit() = fullTextureBlit()



    /**
     * No default implementation.
     */
    override fun onPress() {}



    /**
     * Called when the mouse is hovered over this button. By default, this function does nothing.
     */
    open fun onHovered() {}



    /*
    Texture methods
     */



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
            Button.blit(screenXStart, screenYStart, 0, textureXStart.toFloat(), textureYStart.toFloat(), textureXEnd, textureYEnd, spriteSheetWidth, spriteSheetHeight)



    /**
     * For textures whose entire areas are used. This option will be used for most buttons and is the default for this
     * [doBlit] function.
     */
    fun fullTextureBlit() =
            buildingBlit(x, y, 0, 0, width, height, width, height)



    /**
     * For textures that are not fully used. For example, my inventory slot texture is 32x32 pixels, but Minecraft's
     * inventory sizes are 20x20 pixels. So, I use this function, making sure to have the slot's width and height at 20.
     */
    fun incompleteTextureBlit() =
            buildingBlit(x, y, 0, 0, width, height, res.width, res.height)


}