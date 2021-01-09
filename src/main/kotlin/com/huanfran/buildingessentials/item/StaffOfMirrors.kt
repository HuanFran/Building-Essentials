package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.MirrorRemovalPacket
import com.huanfran.buildingessentials.utils.extensions.rayTrace
import com.huanfran.buildingessentials.utils.extensions.toVector3
import com.huanfran.mirror.MirrorController
import com.huanfran.mirror.Mirrors
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

object StaffOfMirrors : BEStaff("staff_of_mirrors") {



    override fun onItemUse(context: ItemUseContext): ActionResultType {
        if(context.world.isRemote) {
            val player = context.player ?: return ActionResultType.FAIL

            val lookingAt = player.rayTrace(5.0, 0F).hitVec.toVector3()

            Mirrors.handleMirrorNodeCreation(context.world, lookingAt.roundToHalf())
        }

        return ActionResultType.SUCCESS
    }



    /**
     * Milliseconds since a mirror was last removed in the world.
     */
    var millisAtLastRemoval = 0L

    /**
     * Minimum time between mirrors being removed with a left click. This is to prevent multiple mirrors from being
     * removed in a very short time span when left clicking.
     */
    private const val minimumMillisBetweenRemovals = 200


    /**
     * Client-side only. Tries to remove the given controller from the world.
     */
    fun tryRemove(controller: MirrorController) {
        if(System.currentTimeMillis() - millisAtLastRemoval < minimumMillisBetweenRemovals) return

        BEPacketHandler.HANDLER.sendToServer(MirrorRemovalPacket(controller.v0, controller.v1))

        millisAtLastRemoval = System.currentTimeMillis()
    }


}