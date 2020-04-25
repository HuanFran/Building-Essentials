package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.gui.component.BEComponent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui

class BETextComponent(text: String) : BEComponent() {


    /**
     * The string displayed on the screen by this component. The [width] of this [BEComponent] is updated when this is
     * set. The [height] is set on initialisation as this only supports single-line strings.
     */
    var text: String = text
        set(value) {
            field = value
            width = renderer().getStringWidth(value)
        }



    init { height = renderer().FONT_HEIGHT}



    /**
     * Renders the [text] in the given [gui] as the given [x] and [y] positions.
     */
    fun render(gui: AbstractGui, x: Int, y: Int) =
            gui.drawString(renderer(), text, x, y, 14737632) //Colour as int? idk. This is white apparently.



    /**
     * A convenient method for retrieving [Minecraft]'s static font renderer instance.
     */
    private fun renderer() = Minecraft.getInstance().fontRenderer


}