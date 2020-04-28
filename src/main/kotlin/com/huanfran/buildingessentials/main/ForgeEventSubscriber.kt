package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.gui.BEMenu
import com.huanfran.buildingessentials.rendering.GlobalMirrorRenderer
import com.huanfran.mirror.Mirrors
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

/**
 * This class contains events that are triggered as a part of Minecraft's default running. Those that are a part of
 * Building Essential's running should be handled in the [EventSubscriber].
 */
@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ForgeEventSubscriber {


    /*
    Block Placement
     */



    @SubscribeEvent
    fun onBlockPlaced(event: BlockEvent.EntityPlaceEvent) {
        if(!Mirrors.mirroringEnabled || Mirrors.activeMirrorCount() == 0) return

        if(event.entity == null || event.entity !is PlayerEntity) return

        Mirrors.checkPlaceBlock(event.world, event.entity as PlayerEntity, event.pos, event.state)
    }



    /*
    Block Breaking
     */



    @SubscribeEvent
    fun onBlockBroken(event: BlockEvent.BreakEvent) {
        if(!Mirrors.mirroringEnabled || Mirrors.activeMirrorCount() == 0) return

        Mirrors.checkBreakBlock(event.world, event.pos)
    }



    /*
    Mouse movement
     */



    @SubscribeEvent
    fun onMouseScroll(event: GuiScreenEvent.MouseScrollEvent.Post) {
        if(BEMenu.isOpen()) BEMenu.toolbar.handleMouseScroll(event.scrollDelta)
    }



    /*
    Rendering
     */



    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        GlobalMirrorRenderer.renderMirrors(event.matrixStack, event.partialTicks)

        //if(heldItem() == StaffOfObservation)
           // ObservationRenderer.render(event.matrixStack, event.partialTicks, StaffOfObservation.pos0, StaffOfObservation.pos1)
    }



    /*
    World
     */



    @SubscribeEvent
    fun onSave(event: WorldEvent.Save) {

    }



    @SubscribeEvent
    fun test(event: WorldEvent.Load) {
        event.world.worldInfo.worldName
    }




}