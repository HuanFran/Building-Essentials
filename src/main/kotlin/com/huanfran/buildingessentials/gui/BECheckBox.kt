package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.utils.ResourceLocations
import net.minecraft.client.gui.widget.button.Button

class BECheckBox(x: Int,
                 y: Int,
                 var checked: Boolean,
                 onPress: (Button) -> Unit) :
        BEButton(x, y, 8, 8, "", ResourceLocations.CHECKBOX, onPress) {


    override fun onPress() {
        checked = !checked

        if(checked)
            res = ResourceLocations.CHECKBOX_CHECKED
        else
            res = ResourceLocations.CHECKBOX

        super.onPress()
    }


}