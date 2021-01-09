package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.button.BETextlessButton
import com.huanfran.buildingessentials.utils.ResourceLocations
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand

class BEInventorySlot(var itemStack: ItemStack? = null) : BETextlessButton(20, 20, "", ResourceLocations.INVENTORY_SLOT) {


    constructor(item: Item) : this(ItemStack(item))



    var selected = false
        set(value) {
            field = value
            res = resFromSelected(value)
            if(value) itemStack?.let { Minecraft.getInstance().player?.setHeldItem(Hand.MAIN_HAND, it) }

            //Minecraft.getInstance().player?.inventory?.currentItem
        }



    init {
        hoveredAlpha = normalAlpha
        clickedAlpha = normalAlpha
    }



    override fun doBlit() = incompleteTextureBlit()



    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.render(mouseX, mouseY, partialTicks)

        itemStack?.let {
            Minecraft.getInstance().itemRenderer.renderItemIntoGUI(it, x + 2, y + 2)
        }
    }



    private companion object {
        fun resFromSelected(selected: Boolean) = if(selected)
            ResourceLocations.INVENTORY_SLOT_SELECTED
        else
            ResourceLocations.INVENTORY_SLOT
    }


}