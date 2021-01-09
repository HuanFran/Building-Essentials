package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.item.*
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

/**
 * Handles events that are specific to this mod. Mainly, this deals with registries.
 */
@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object EventSubscriber {


    /*
    Items
     */



    private fun Register<Item>.registerItems( vararg items: BEItem) = items.forEach {
        it.setRegistryName(MODID, it.name)
        registry.register(it)
    }



    @SubscribeEvent
    fun onRegisterItems(event: Register<Item>) {
        event.registerItems(
                StaffOfExtension,
                TreeAnnihilator,
                StaffOfMirrors,
                StaffOfSelection,
                StaffOfObservation)
    }




    /*
    Tile Entities
     */



    @SubscribeEvent
    fun onRegisterTileEntityTypes(event: Register<TileEntityType<*>>) {

    }

}