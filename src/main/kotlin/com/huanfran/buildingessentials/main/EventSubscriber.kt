package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.block.BEBlock
import com.huanfran.buildingessentials.block.blockitem.BEBlockItem
import com.huanfran.buildingessentials.item.*
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.IForgeRegistryEntry

/**
 * Handles events that are specific to this mod. Mainly, this deals with registries.
 */
@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object EventSubscriber {


    /*
    Common
     */



    private fun<T : IForgeRegistryEntry<T>> setup(entry: T, res: ResourceLocation) =
            entry.apply { entry.setRegistryName(res) }



    private fun<T : IForgeRegistryEntry<T>> setup(entry: T, name: String) =
            setup(entry, ResourceLocation(MODID, name))



    /*
    Items
     */



    @SubscribeEvent
    fun onRegisterItems(event: Register<Item>) {
        event.registerAll(
                StaffOfExtension,
                TreeAnnihilator,
                StaffOfMirrors,
                StaffOfSelection,
                StaffOfObservation
        )
    }



    private fun setupItem(item: BEItem): Item =
            setup(item, item.name)



    private fun Register<Item>.registerAll(vararg items: BEItem) =
            items.forEach { registry.register(setupItem(it)) }



    private fun setupBlockItem(blockItem: BEBlockItem) = setup(blockItem, blockItem.block.registryName!!)



    private fun Register<Item>.registerAll(vararg blockItems: BEBlockItem) =
            blockItems.forEach { registry.register(setupBlockItem(it)) }



    /*
    Tile Entities
     */



    @SubscribeEvent
    fun onRegisterTileEntityTypes(event: Register<TileEntityType<*>>) {

    }



    /*
    Blocks
     */



    @SubscribeEvent
    fun onRegisterBlocks(event: Register<Block>) {

    }



    private fun setupBlock(block: BEBlock) = setup(block, block.name)



    private fun Register<Block>.registerAll(vararg items: BEBlock) =
            items.forEach { registry.register(setupBlock(it) ) }


}