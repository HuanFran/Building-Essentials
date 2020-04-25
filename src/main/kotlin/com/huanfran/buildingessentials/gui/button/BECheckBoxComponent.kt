package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.gui.component.BEComponent

class BECheckBoxComponent(val checkBox: BECheckBox) : BEComponent() {


    init {
        x = checkBox.x
        y = checkBox.y

        width = checkBox.width
        height = checkBox.height
    }



    override fun updateOffset() {
        super.updateOffset()

        checkBox.x = totalX
        checkBox.y = totalY
    }


}