package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.utils.extensions.rayTrace
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.math.BlockPos

object StaffOfSelection : BEStaff("staff_of_selection") {


    /*
    Selection variables are Client-side only.
     */



    var selection0: BlockPos? = null

    var selection1: BlockPos? = null

    var selection2: BlockPos? = null



    var range = 50.0



    override fun onItemUse(context: ItemUseContext) : ActionResultType {
        if(context.world.isRemote) {
            val player = context.player ?: return ActionResultType.FAIL

            val lookingAt = player.rayTrace(range)

            setSelection(lookingAt.pos)
        }

        return ActionResultType.SUCCESS
    }



    private fun setSelection(pos: BlockPos) = when {
        selection0 == null -> selection0 = pos
        selection1 == null -> selection1 = pos
        selection2 == null -> selection2 = pos
        else -> { selection0 = pos; selection1 = null; selection2 = null}
    }


}