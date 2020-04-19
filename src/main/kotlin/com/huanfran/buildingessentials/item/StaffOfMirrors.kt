package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.block.MirrorBlock
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

object StaffOfMirrors : BEItem("staff_of_mirrors") {


    override fun onItemUse(context: ItemUseContext): ActionResultType {
        if(context.world.isRemote)
            context.world.setBlockState(context.pos.add(context.face.directionVec), MirrorBlock.defaultState)

        return ActionResultType.SUCCESS
    }


}