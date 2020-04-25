package com.huanfran.buildingessentials.undo

import net.minecraft.block.BlockState
import net.minecraft.block.DoublePlantBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

/**
 * Contains an [ArrayList] of [BEAction]s and the [world] in which these actions took place. An instance of this class
 * represents a single operation done by the Building Essentials mod, one that can be undone or redone. For example,
 * using the Staff of Extension will create one instance of this class and add it to the undo-redo buffer in the
 * [Undo] class.
 */
class BEActionBuffer(val world: IWorld, makeFunctional: Boolean = true) {


    /**
     * All [BEAction]s that were performed during this operation.
     */
    private val actions = ArrayList<BEAction>()



    init {
        if(makeFunctional) Undo.addBuffer(this)
    }



    /**
     * Adds a [BEAction] to the [actions] list and calls the [world]'s setBlockState function with the given [flags].
     */
    fun setBlockState(pos: BlockPos, state: BlockState, flags: Int) {
        actions.add(BEAction(pos, world.getBlockState(pos), state))

        world.setBlockState(pos, state, flags)
    }



    /**
     * Adds a [BEAction] to the [actions] list and calls the [world]'s setBlockState function with 3 as the flags value,
     * which is the default, causing a block update and sending the change to clients.
     */
    fun setBlockState(pos: BlockPos, state: BlockState) = setBlockState(pos, state, 3)



    /**
     * Adds a [BEAction] to the [actions] list and calls the [world]'s removeBlock function. If the block state at that
     * [pos] is a [DoublePlantBlock], the action is not added to the list. Double plant blocks cause some issues when
     * undoing and redoing.
     */
    fun removeBlock(pos: BlockPos) {
        val state = world.getBlockState(pos)

        //Double plant blocks do weird things.
        if(state.block !is DoublePlantBlock)
            actions.add(BEAction(pos, state, world.getFluidState(pos).blockState))

        world.removeBlock(pos, false)
    }



    /**
     * Calls [BEAction.undo] for all [actions]. Actions should never be individually undone. They must all be undone at
     * the same time as is done in this function.
     */
    fun undoAll() = actions.forEach { it.undo(world) }



    /**
     * Calls [BEAction.redo] for all [actions]. Actions should never be individually redone. They must all be redone at
     * the same time as is done in this function.
     */
    fun redoAll() = actions.forEach { it.redo(world) }


}