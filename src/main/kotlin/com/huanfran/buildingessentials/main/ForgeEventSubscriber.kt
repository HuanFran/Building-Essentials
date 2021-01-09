package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.gui.BEMenu
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.InformationDemandPacket
import com.huanfran.buildingessentials.rendering.GlobalMirrorRenderer
import com.huanfran.buildingessentials.utils.internal.saveName
import com.huanfran.buildingessentials.utils.internal.server
import com.huanfran.mirror.Mirrors
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.client.event.ClientPlayerNetworkEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStartedEvent
import kotlin.math.ceil

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
        val fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, event.player.heldItemMainhand)
        when(fortune) {
            0 -> Unit
            1 -> event.expToDrop = ceil(event.expToDrop * 1.33F).toInt()
            2 -> event.expToDrop = ceil(event.expToDrop * 1.75F).toInt()
            3 -> event.expToDrop = ceil(event.expToDrop * 2.2F).toInt()
            else -> event.expToDrop = ceil(event.expToDrop * (fortune - 1F)).toInt()
        }

        if(!Mirrors.mirroringEnabled || Mirrors.activeMirrorCount() == 0) return

        Mirrors.checkBreakBlock(event.world, event.pos)
    }


    @SubscribeEvent
    fun onLivingExperienceDrop(event: LivingExperienceDropEvent) {
        val looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, event.attackingPlayer.heldItemMainhand)
        when(looting) {
            0 -> Unit
            1 -> event.droppedExperience = ceil(event.droppedExperience * 1.5F).toInt()
            2 -> event.droppedExperience = ceil(event.droppedExperience * 10F).toInt()
            3 -> event.droppedExperience = ceil(event.droppedExperience * 2.5F).toInt()
            else -> event.droppedExperience = event.droppedExperience * looting
        }
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



    /**
     * Server-side only.
     */
    @SubscribeEvent
    fun onSave(event: WorldEvent.Save) {
        if(!event.world.isRemote) Mirrors.saveMirrors(event.world)
    }



    /**
     * Only triggers on the client-side.
     */
    @SubscribeEvent
    fun onClientJoin(event: ClientPlayerNetworkEvent.LoggedInEvent) {
        BEPacketHandler.HANDLER.sendToServer(InformationDemandPacket(1))
    }



    @SubscribeEvent
    fun onServerStart(event: FMLServerStartedEvent) {
        //Update the internal server information.
        server = event.server
        saveName = event.server.folderName

        Mirrors.loadMirrors()
    }


}