package com.huanfran.buildingessentials.undo

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

/**
 * Encapsulates the changing of a [BlockState] at [pos] from [previousState] to [newState]. This is used in the
 * Building Essentials mod for undo and redo operations.
 */
class BEAction(val pos: BlockPos, private val previousState: BlockState, private val newState: BlockState) {


    /**
     * Undoes this action in the given [world].
     */
    fun undo(world: IWorld) {
        world.setBlockState(pos, previousState, 3)
    }



    /**
     * Redoes this action in the given [world].
     */
    fun redo(world: IWorld) {
        world.setBlockState(pos, newState, 3)
    }


}