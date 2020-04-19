package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.block.BEBlock
import com.huanfran.buildingessentials.block.MirrorBlock
import com.huanfran.buildingessentials.block.blockitem.BEBlockItem
import com.huanfran.buildingessentials.block.blockitem.MirrorBlockItem
import com.huanfran.buildingessentials.graphics.maths.Maths
import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.graphics.mesh.Mesh
import com.huanfran.buildingessentials.graphics.render.Base
import com.huanfran.buildingessentials.graphics.shaders.Shaders
import com.huanfran.buildingessentials.item.*
import com.huanfran.buildingessentials.tile.BETileEntityTypes
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.IForgeRegistryEntry



@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object EventSubscriber {

    lateinit var MESH: Base


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
                StaffOfSelection
        )

        event.registerAll(
                MirrorBlockItem
        )

        //Bit of a hack. IDK where else to put these. They require the OpenGL context to have already been established.
        //I don't know when that occurs.
        Shaders.initDirectories()
        Shaders.initShaderPrograms()

        val vectors = arrayListOf(Vector3(0.3, 0.4, 0.5), Vector3(0.6, 0.2, 0.1), Vector3(0.2, 0.1, 0.8))
        val m = Mesh(Maths.toDoubleArray(vectors), intArrayOf(0,1,2))

        MESH = Base(m, Shaders.TEST_PROGRAM)
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
        event.registry.registerAll(
                setup(BETileEntityTypes.MIRROR, "mirror")
        )
    }



    /*
    Blocks
     */



    @SubscribeEvent
    fun onRegisterBlocks(event: Register<Block>) {
        event.registerAll(
                MirrorBlock
        )
    }



    private fun setupBlock(block: BEBlock) = setup(block, block.name)



    private fun Register<Block>.registerAll(vararg items: BEBlock) =
            items.forEach { registry.register(setupBlock(it) ) }



    /*
    Rendering
     */



    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {

    }


}