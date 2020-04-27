package com.huanfran.buildingessentials.gui

import com.huanfran.buildingessentials.gui.button.BEButtonComponent
import com.huanfran.buildingessentials.gui.button.BECheckBox
import com.huanfran.buildingessentials.gui.button.BECheckBoxComponent
import com.huanfran.buildingessentials.gui.button.BEIconButton
import com.huanfran.buildingessentials.gui.component.box.BEHBox
import com.huanfran.buildingessentials.gui.component.box.BEVBox
import com.huanfran.buildingessentials.main.playerEyePos
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.ClearSurfaceBlocksPacket
import com.huanfran.buildingessentials.networking.UndoRedoPacket
import com.huanfran.buildingessentials.utils.Commands
import com.huanfran.buildingessentials.utils.ResourceLocations
import com.huanfran.buildingessentials.utils.player
import com.huanfran.mirror.Mirrors

object BEMenu : BEScreen() {


    /*
    Dimensions
     */



    private const val buttonWidth = 32



    /*
    Buttons
     */



    var left: BEVBox

    var daytimeButton: BEButtonComponent

    var nighttimeButton: BEButtonComponent

    var clearWeatherButton: BEButtonComponent

    var clearVegetationButton: BEButtonComponent

    var clearSnow: BEButtonComponent



    var undoRedo: BEHBox

    var redo: BEButtonComponent

    var undo: BEButtonComponent



    var mirrorConfig: BEVBox

    var mirroringEnabled: BECheckBoxComponent

    var mirrorRenderingEnabled: BECheckBoxComponent

    var mirrorReplacingEnabled: BECheckBoxComponent

    var toolbar: BEToolbarComponent



    /*
    Initialisation
     */



    init {
        daytimeButton = BEButtonComponent(BEIconButton("Set time to day", ResourceLocations.DAYTIME)
        { Commands.executeCommand("/time set day") })



        nighttimeButton = BEButtonComponent(BEIconButton("Set time to night", ResourceLocations.NIGHTTIME)
        { Commands.executeCommand("/time set night") })



        clearWeatherButton = BEButtonComponent(BEIconButton("Set weather to clear", ResourceLocations.CLEAR_WEATHER)
        { Commands.executeCommand("/weather clear") })



        clearVegetationButton = BEButtonComponent(BEIconButton("Clear nearby vegetation", ResourceLocations.CLEAR_VEGETATION)
        { BEPacketHandler.HANDLER.sendToServer(ClearSurfaceBlocksPacket(1, playerEyePos(player()), 30, 20)) })



        clearSnow = BEButtonComponent(BEIconButton("Clear nearby snow", ResourceLocations.CLEAR_SNOW)
        { BEPacketHandler.HANDLER.sendToServer(ClearSurfaceBlocksPacket(2, playerEyePos(player()), 30, 20)) })



        undo = BEButtonComponent(BEIconButton("Undo", ResourceLocations.UNDO)
        { BEPacketHandler.HANDLER.sendToServer(UndoRedoPacket(true)) })



        redo = BEButtonComponent(BEIconButton("Redo", ResourceLocations.REDO)
        { BEPacketHandler.HANDLER.sendToServer(UndoRedoPacket(false)) })



        mirroringEnabled = BECheckBoxComponent(BECheckBox(true, "Mirroring enabled")
        { Mirrors.mirroringEnabled = !Mirrors.mirroringEnabled })



        mirrorRenderingEnabled = BECheckBoxComponent(BECheckBox(true, "Mirror rendering enabled")
        { Mirrors.mirrorRenderingEnabled = !Mirrors.mirrorRenderingEnabled} )



        mirrorReplacingEnabled = BECheckBoxComponent(BECheckBox(false, "Mirrored block replacement enabled")
        { Mirrors.mirrorReplacingEnabled = !Mirrors.mirrorReplacingEnabled } )

        //toolBar = BEToolbarComponent(width / 2 - (20 * 9) / 2, height - 20 * 2)
        //toolBar = BEToolbarComponent(0,0)
        toolbar = BEToolbarComponent(0, 0)

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

        mirrorConfig = BEVBox(80, 20 + 32 + 10)
        mirrorConfig.spacing = 10

        mirrorConfig.children.add(mirroringEnabled)
        mirrorConfig.children.add(mirrorRenderingEnabled)
        mirrorConfig.children.add(mirrorReplacingEnabled)
    }



    override fun init() {
        addComponent(left)
        addComponent(undoRedo)

        toolbar.x = width / 2 - (20 * 9) / 2
        toolbar.y = height - 20 * 2 - 10

        addComponent(toolbar)

        if(Mirrors.activeMirrorCount() != 0) addComponent(mirrorConfig)
    }




    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        toolbar.handleKeyPress(keyCode)

        return false
    }


}