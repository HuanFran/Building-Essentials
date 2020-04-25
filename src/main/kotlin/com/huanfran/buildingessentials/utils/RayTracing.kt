package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.main.toVec3d
import com.huanfran.buildingessentials.main.toVector3
import com.huanfran.buildingessentials.tile.mirror.MirrorController
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.RayTraceContext
import net.minecraft.world.IWorld

object RayTracing {

    /*
    NOTE: entity.rotationYaw: positiveZ = 0, negativeX = 90, negativeZ = 180, positiveX = 270
     */


    fun test(world: IWorld, entity: PlayerEntity, partialTicks: Float, controller: MirrorController) {
        val eyePos = entity.getEyePosition(partialTicks).toVector3()

        val eyePosOpposite = controller.mirroredPos(eyePos)

        val look = entity.getLook(partialTicks).toVector3()

        val lookOpposite = controller.mirroredPos(look)

        val range = 5.0

        val third = eyePosOpposite + look * range

        val context = RayTraceContext(
                eyePosOpposite.toVec3d(),
                third.toVec3d(),
                RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)

        val result = world.rayTraceBlocks(context)

    }


}