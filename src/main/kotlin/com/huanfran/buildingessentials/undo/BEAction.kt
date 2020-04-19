package com.huanfran.buildingessentials.undo

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BEAction(val pos: BlockPos, val previousState: BlockState, val newState: BlockState) {


    fun undo(world: World) {
        world.setBlockState(pos, previousState)
    }



    fun redo(world: World) {
        world.setBlockState(pos, newState)
    }


}