package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.block.MirrorBlock
import com.huanfran.buildingessentials.tile.mirror.Mirrors
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ForgeEventSubscriber {


    /*
    Block Placement
     */



    @SubscribeEvent
    fun onBlockPlaced(event: BlockEvent.EntityPlaceEvent) {
        if(!Mirrors.mirroringEnabled || Mirrors.activeMirrorCount() == 0) return

        if(event.state.block != MirrorBlock) Mirrors.checkPlaceBlock(event.world, event.pos, event.state)
    }



    /*
    Block Breaking
     */



    @SubscribeEvent
    fun onBlockBroken(event: BlockEvent.BreakEvent) {
        if(!Mirrors.mirroringEnabled || Mirrors.activeMirrorCount() == 0) return

        if(event.state.block != MirrorBlock) Mirrors.checkBreakBlock(event.world, event.pos)
    }


}