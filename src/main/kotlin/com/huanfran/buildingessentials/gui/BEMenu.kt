package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.component.BEButtonComponent
import com.huanfran.buildingessentials.gui.component.BEComponent
import com.huanfran.buildingessentials.gui.component.box.BEHBox
import com.huanfran.buildingessentials.gui.component.box.BEVBox
import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.main.player
import com.huanfran.buildingessentials.main.playerEyePos
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.ClearSurfaceBlocksPacket
import com.huanfran.buildingessentials.networking.UndoRedoPacket
import com.huanfran.buildingessentials.utils.Commands
import com.huanfran.buildingessentials.utils.ResourceLocations
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

object BEMenu : Screen(StringTextComponent("Building")) {


    /*
    Dimensions
     */



    private const val buttonWidth = 32



    /*
    Buttons
     */



    lateinit var left: BEVBox

    lateinit var daytimeButton: BEButtonComponent

    lateinit var nighttimeButton: BEButtonComponent

    lateinit var clearWeatherButton: BEButtonComponent

    lateinit var clearVegetationButton: BEButtonComponent

    lateinit var clearSnow: BEButtonComponent


    lateinit var undoRedo: BEHBox

    lateinit var redo: BEButtonComponent

    lateinit var undo: BEButtonComponent



    override fun init() {
        daytimeButton = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Set time to day",
                ResourceLocations.DAYTIME) { Commands.executeCommand("/time set day") })

        nighttimeButton = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Set time to night",
                ResourceLocations.NIGHTTIME) { Commands.executeCommand("/time set night") })


        clearWeatherButton = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Set weather to clear",
                ResourceLocations.CLEAR_WEATHER) { Commands.executeCommand("/weather clear") })


        clearVegetationButton = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Clear nearby vegetation",
                ResourceLocations.CLEAR_VEGETATION) {
            BEPacketHandler.HANDLER.sendToServer(ClearSurfaceBlocksPacket(1, playerEyePos(player()!!), 20, 10)) })

        clearSnow = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Clear nearby snow",
                ResourceLocations.CLEAR_SNOW) { BEPacketHandler.HANDLER.sendToServer(ClearSurfaceBlocksPacket(2, playerEyePos(player()!!), 20, 10)) })

        undo = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Undo",
                ResourceLocations.UNDO) { BEPacketHandler.HANDLER.sendToServer(UndoRedoPacket(true)) })

        redo = BEButtonComponent(BEButton(
                0,
                0,
                buttonWidth,
                buttonWidth,
                "Redo",
                ResourceLocations.REDO) { BEPacketHandler.HANDLER.sendToServer(UndoRedoPacket(false)) })

        left = BEVBox(20, 20)
        left.spacing = 10

        left.children.add(daytimeButton)
        left.children.add(nighttimeButton)
        left.children.add(clearWeatherButton)
        left.children.add(clearVegetationButton)
        left.children.add(clearSnow)

        undoRedo = BEHBox(80, 20)
        undoRedo.spacing = 10

        undoRedo.children.add(undo)
        undoRedo.children.add(redo)

        addComponent(left)
        addComponent(undoRedo)


    }



    fun addComponent(c: BEComponent) {
        if(c is BEButtonComponent)  addButton(c.button)

        c.children.forEach { addComponent(it) }
    }



    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground()
        super.render(mouseX, mouseY, partialTicks)
    }



    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if(keyCode == KeyBindings.OPEN_MENU.key.keyCode)
            onClose()

        return false
    }



    override fun isPauseScreen(): Boolean = false



    fun swapTo() = Minecraft.getInstance().displayGuiScreen(this)


}