package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.utils.ResourceLocations
import net.minecraft.client.Minecraft

class BECheckBox(width: Int, height: Int, var checked: Boolean, var text: String, var onPress: (BECheckBox) -> Unit) : BETextlessButton(width, height, "", resFromChecked(checked)) {



    constructor(checked: Boolean, text: String, onPress: (BECheckBox) -> Unit) : this(16, 16, checked, text, onPress)



    /**
     * TODO: Replace this text rendering entirely with components using [BETextComponent].
     */
    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.render(mouseX, mouseY, partialTicks)

        drawString(Minecraft.getInstance().fontRenderer, text, x + width + 8, y + 4, 14737632)
    }



    override fun onPress() {
        checked = !checked

        res = if(checked)
            ResourceLocations.CHECKBOX_CHECKED
        else
            ResourceLocations.CHECKBOX

        onPress(this)
    }



    private companion object {
        fun resFromChecked(checked: Boolean) = if(checked)
            ResourceLocations.CHECKBOX_CHECKED
        else
            ResourceLocations.CHECKBOX
    }


}