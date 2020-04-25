package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.component.BEComponent

class BEInventorySlotComponent(val slot: BEInventorySlot) : BEComponent() {


    init {
        x = slot.x
        y = slot.y

        width = slot.width
        height = slot.height
    }



    override fun updateOffset() {
        super.updateOffset()

        slot.x = totalX
        slot.y = totalY
    }


}