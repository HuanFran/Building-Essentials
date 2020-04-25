package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.component.box.BEHBox
import com.huanfran.buildingessentials.item.*
import org.lwjgl.glfw.GLFW

class BEToolbarComponent(x: Int, y: Int) : BEHBox(x, y) {


    /*
    Slots
     */

    
    
    val i0 = BEInventorySlotComponent(BEInventorySlot(StaffOfExtension))

    val i1 = BEInventorySlotComponent(BEInventorySlot(TreeAnnihilator))

    val i2 = BEInventorySlotComponent(BEInventorySlot(StaffOfMirrors))
    
    val i3 = BEInventorySlotComponent(BEInventorySlot(StaffOfSelection))
    
    val i4 = BEInventorySlotComponent(BEInventorySlot(StaffOfObservation))
    
    val i5 = BEInventorySlotComponent(BEInventorySlot())
    
    val i6 = BEInventorySlotComponent(BEInventorySlot())
    
    val i7 = BEInventorySlotComponent(BEInventorySlot())
    
    val i8 = BEInventorySlotComponent(BEInventorySlot())



    val slots = arrayListOf(i0, i1, i2, i3, i4, i5, i6, i7, i8)



    /*
    Variables
     */



    var active = false



    var selected: BEInventorySlotComponent? = null
        set(value) {
            field?.let { it.slot.selected = false }
            value?.let { it.slot.selected = true }
            field = value
        }



    var selectedIndex = -1
        get() = slots.indexOf(selected)



    /*
    Initialisation - add all slots to children.
     */

    
    
    init {
        children.add(i0)
        children.add(i1)
        children.add(i2)
        children.add(i3)
        children.add(i4)
        children.add(i5)
        children.add(i6)
        children.add(i7)
        children.add(i8)
    }



    /*
    Functions - handling key input
     */



    fun slotFromKeycode(code: Int) = when(code) {
        GLFW.GLFW_KEY_1 -> i0
        GLFW.GLFW_KEY_2 -> i1
        GLFW.GLFW_KEY_3 -> i2
        GLFW.GLFW_KEY_4 -> i3
        GLFW.GLFW_KEY_5 -> i4
        GLFW.GLFW_KEY_6 -> i5
        GLFW.GLFW_KEY_7 -> i6
        GLFW.GLFW_KEY_8 -> i7
        GLFW.GLFW_KEY_9 -> i8
        else -> null
    }



    fun handleKeyPress(keyCode: Int) = slotFromKeycode(keyCode)?.let { selected = it; active = true }



    fun handleMouseScroll(scrollDelta: Double) {
        selected = slots[Math.floorMod(selectedIndex - scrollDelta.toInt(), 9)]

        active = true
    }



    
}