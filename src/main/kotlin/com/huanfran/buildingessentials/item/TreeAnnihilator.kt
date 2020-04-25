package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.block.LeavesBlock
import net.minecraft.block.LogBlock
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

/**
 * Annihilates trees on right click.
 */
object TreeAnnihilator : BEStaff("tree_annihilator") {


    override fun onItemUse(context: ItemUseContext): ActionResultType {
        annihilateAction(context.world, context.pos, 500)

        return ActionResultType.SUCCESS    
    }



    /**
     * Annihilates connected leaves and logs.
     */
    fun annihilateAction(world: World, startPos: BlockPos, maxRemoved: Int) : Int {
        //Make sure that only logs and leaves are targeted.
        world.getBlockState(startPos).block.let {
            if(it !is LogBlock && it !is LeavesBlock) return -1
        }

        //All blocks to be processed.
        val surrounding = LinkedList<BlockPos>()

        //All blocks that have been processed. They will be removed from the world at the end of the function.
        val processed = ArrayList<BlockPos>()

        //Add the initial position.
        surrounding.add(startPos)

        //Loop through until there are no more connected blocks. Don't go above the specified max limit.
        while(surrounding.size > 0 && processed.size < maxRemoved + 1) {
            val current = surrounding.poll()

            //Only choose log and leaves blocks to be processed. Ignore them if they have already been processed.
            surrounding.addChecked(
                    {
                        val block = world.getBlockState(it).block

                        (block is LogBlock || block is LeavesBlock) &&
                                !surrounding.contains(it) &&
                                !processed.contains(it)
                    },

                    //These are all possible configurations of three numbers with -1, 0, 1 as values, except 0,0,0,
                    //which is the current block.
                    current.add(-1, -1, -1),
                    current.add(0, -1, -1),
                    current.add(1, -1, -1),

                    current.add(-1, 0, -1),
                    current.add(0, 0, -1),
                    current.add(1, 0, -1),

                    current.add(-1, 1, -1),
                    current.add(0, 1, -1),
                    current.add(1, 1, -1),


                    current.add(-1, -1, 0),
                    current.add(0, -1, 0),
                    current.add(1, -1, 0),

                    current.add(-1, 0, 0),
                    current.add(1, 0, 0),

                    current.add(-1, 1, 0),
                    current.add(0, 1, 0),
                    current.add(1, 1, 0),


                    current.add(-1, -1, 1),
                    current.add(0, -1, 1),
                    current.add(1, -1, 1),

                    current.add(-1, 0, 1),
                    current.add(0, 0, 1),
                    current.add(1, 0, 1),

                    current.add(-1, 1, 1),
                    current.add(0, 1, 1),
                    current.add(1, 1, 1)
            )

            //Process the current one.
            processed.add(current)
        }

        //Using an action buffer to set and remove blocks allows undo and redo functionality.
        val actionBuffer = BEActionBuffer(world)

        //Process all blocks. I.e. remove them from the world.
        for(p in processed) actionBuffer.removeBlock(p)

        //Return the amount of blocks that were processed.
        return processed.size
    }



    /**
     * A simple private helper method for [annihilateAction]. Individually adds the given [elements] to this
     * [LinkedList] if they satisfy the given [predicate].
     */
    private fun<T> LinkedList<T>.addChecked(predicate: (T) -> Boolean, vararg elements: T) = 
            elements.forEach { if(predicate(it)) this.add(it) }


}