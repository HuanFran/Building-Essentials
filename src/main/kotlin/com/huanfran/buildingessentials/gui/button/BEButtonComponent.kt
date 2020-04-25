package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.gui.component.BEComponent

class BEButtonComponent(val button: BETextlessButton) : BEComponent() {


    init {
        x = button.x
        y = button.y

        width = button.width
        height = button.height
    }



    override fun updateOffset() {
        super.updateOffset()

        button.x = totalX
        button.y = totalY
    }


}