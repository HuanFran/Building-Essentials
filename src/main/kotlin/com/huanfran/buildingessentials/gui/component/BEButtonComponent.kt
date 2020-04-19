package com.huanfran.buildingessentials.gui.component

import com.huanfran.buildingessentials.gui.BEButton

class BEButtonComponent(val button: BEButton) : BEComponent() {


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