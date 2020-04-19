package com.huanfran.buildingessentials.keys

import com.huanfran.buildingessentials.gui.BEMenu
import com.huanfran.buildingessentials.main.MODID
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = MODID, value = [Dist.CLIENT])
object EventKeyInput {


    @SubscribeEvent
    fun handlEventInput(event: TickEvent.ClientTickEvent) {
        if(KeyBindings.OPEN_MENU.isKeyDown) BEMenu.swapTo()
    }


}