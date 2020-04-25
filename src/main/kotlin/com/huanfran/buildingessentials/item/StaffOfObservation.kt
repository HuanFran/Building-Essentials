package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.main.isLogicalClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object StaffOfObservation : BEStaff("staff_of_observation") {


    var pos0: BlockPos? = null

    var pos1: BlockPos? = null

    var lookingAt: BlockPos? = null



    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        if(isLogicalClient()) {
            lookingAt?.let {
                if(pos0 == null)
                    pos0 = it
                else if(it != pos0)
                    pos1 = it
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn)
    }


}