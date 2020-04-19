package com.huanfran.buildingessentials.undo

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BEActionBuffer(val world: World, undoRedoEnabled: Boolean = true) {


    val actions = ArrayList<BEAction>()



    init {
        if(undoRedoEnabled)
            Undo.addBuffer(this)
    }



    fun setBlockState(pos: BlockPos, state: BlockState) {
        actions.add(BEAction(pos, world.getBlockState(pos), state))

        world.setBlockState(pos, state)
    }



    fun removeBlock(pos: BlockPos) {
        actions.add(BEAction(pos, world.getBlockState(pos), world.getFluidState(pos).blockState))

        world.removeBlock(pos, false)
    }



    fun undoAll() = actions.forEach { it.undo(world) }



    fun redoAll() = actions.forEach { it.redo(world) }


}