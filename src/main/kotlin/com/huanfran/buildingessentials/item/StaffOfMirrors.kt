package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.block.MirrorBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

object StaffOfMirrors : BEStaff("staff_of_mirrors") {


    override fun onItemUse(context: ItemUseContext): ActionResultType {
        if(!context.world.isRemote)
            context.world.setBlockState(context.pos.add(context.face.directionVec), MirrorBlock.defaultState)

        return ActionResultType.SUCCESS
    }



    override fun onEntitySwing(stack: ItemStack?, entity: LivingEntity?) : Boolean {
        if(entity == null || entity !is PlayerEntity) return false

        //val lookingAt = Mirrors.serverMirrorHandler.lookingAt(entity.getEyePosition(0F).toVector3(), entity.lookVec.toVector3())

       // log("$lookingAt")

        return false
    }


}