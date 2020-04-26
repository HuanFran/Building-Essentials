package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.main.rayTraceResult
import com.huanfran.buildingessentials.main.toVector3
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.MirrorRemovalPacket
import com.huanfran.buildingessentials.tile.mirror.MirrorController
import com.huanfran.buildingessentials.tile.mirror.Mirrors
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

object StaffOfMirrors : BEStaff("staff_of_mirrors") {


    var millisAtLastRemoval = 0L
    val minimumMillisBetweenRemovals = 200



    override fun onItemUse(context: ItemUseContext): ActionResultType {
        val player = context.player ?: return ActionResultType.FAIL

        val lookingAt = rayTraceResult(player, 5.0, 0F).hitVec.toVector3()

        Mirrors.handleMirrorNodeCreation(lookingAt.roundToHalf(), context.world.isRemote)

        return ActionResultType.SUCCESS
    }



    /**
     * Client-side only.
     */
    fun tryRemove(controller: MirrorController) {
        if(System.currentTimeMillis() - millisAtLastRemoval < minimumMillisBetweenRemovals) return

        BEPacketHandler.HANDLER.sendToServer(MirrorRemovalPacket(controller.v0, controller.v1))

        millisAtLastRemoval = System.currentTimeMillis()
    }


}