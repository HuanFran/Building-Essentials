package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.button.BEButtonComponent
import com.huanfran.buildingessentials.gui.button.BECheckBoxComponent
import com.huanfran.buildingessentials.gui.component.BEComponent
import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.main.currentScreen
import com.huanfran.buildingessentials.main.minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

abstract class BEScreen : Screen(StringTextComponent("")) {


    companion object {
        /**
         * Serves to separate the rendering of tooltips from the rendering of buttons and other GUI components. This is
         * because tooltips should always be rendered last, in front of other components. This allows any button that is
         * being hovered over to specify that its tooltip should be rendered.
         */
        var tooltipToRender: Triple<String, Int, Int>? = null
    }



    fun addComponent(c: BEComponent) {
        when(c) {
            is BEButtonComponent -> addButton(c.button)
            is BECheckBoxComponent -> addButton(c.checkBox)
            is BEInventorySlotComponent -> addButton(c.slot)
        }

        c.children.forEach { addComponent(it) }
    }



    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground()

        super.render(mouseX, mouseY, partialTicks)

        //Render the tooltip of the currently hovered-over button. This is done last to render it in front of buttons.
        tooltipToRender?.let { renderTooltip(it.first, it.second, it.third) }

        //Reset the tooltip so that it doesn't linger after the user has moved the mouse away from the button.
        tooltipToRender = null
    }



    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == KeyBindings.OPEN_MENU.key.keyCode)
            BEMenu.onClose()

        return false
    }



    override fun isPauseScreen(): Boolean = false



    fun swapTo() = minecraft().displayGuiScreen(this)



    fun isOpen() = currentScreen() == this


}