package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.tile.mirror.Mirrors
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import kotlin.collections.ArrayList

object StaffOfExtension : BEStaff("staff_of_extension") {


    override fun onItemUse(context: ItemUseContext): ActionResultType {
        context.player?.let {
            if(!context.world.isRemote) extendAction(context.world, it, context.pos, context.face, 48)
        }

        return ActionResultType.SUCCESS
    }



    /**
     * Begins an extend action from the [startPos] + the [face] direction vector.
     */
    fun extendAction(world: World, player: PlayerEntity, startPos: BlockPos, face: Direction, maxBlocksPlaced: Int) : Int {
        val startState = world.getBlockState(startPos)

        //XZ, XY, or YZ plane, depending on the block face that the user clicked on.
        val plane = BuildingPlane.fromDirection(face)

        //The block that extends out from the block on which the user clicked.
        val firstPos = startPos.add(face.directionVec)

        //The blocks that are pending for processing.
        val surroundings = LinkedList<BlockPos>()

        //These blocks will be processed after the while loop (have their blockstate set to startState)
        val processed = ArrayList<BlockPos>()

        surroundings.add(firstPos)

        //Run until there are no more valid blocks or until the maximum number of blocks has been placed.
        while(surroundings.size > 0 && processed.size < maxBlocksPlaced + 1) {
            val current = surroundings.poll()

            /*
            Only process a block if it is an air or fluid block and if the supporting block is the same as that on which
            the user initially clicked.
             */
            surroundings.addChecked( { 
                (world.isAirBlock(it) || world.getBlockState(it).fluidState.isSource) && 
                        world.getBlockState(it.add(face.opposite.directionVec)).block == startState.block &&
                        !surroundings.contains(it) && !processed.contains(it) } ,
                    //All 8 blocks surrounding the current block on the building plane.
                    current.add(plane.d0),
                    current.add(plane.d0).add(plane.d1),
                    current.add(plane.d1),
                    current.add(plane.d1).add(plane.d0Neg),
                    current.add(plane.d0Neg),
                    current.add(plane.d0Neg).add(plane.d1Neg),
                    current.add(plane.d1Neg),
                    current.add(plane.d1Neg).add(plane.d0))

            processed.add(current)
        }

        //Using an action buffer to set and remove blocks allows undo and redo functionality.
        val actionBuffer = BEActionBuffer(world)

        //Now set the block states.
        for(p in processed) {
            actionBuffer.setBlockState(p, startState)

            //Allow the staff of extension to influence mirrors.
            if(Mirrors.mirroringEnabled) Mirrors.checkPlaceBlock(actionBuffer, player, p, startState)
        }

        //The return value is the number of block states modified.
        return processed.size
    }



    /**
     * A simple private helper method for [extendAction]. Individually adds the given [elements] to this
     * [LinkedList] if they satisfy the given [predicate].
     */
    private fun<T> LinkedList<T>.addChecked(predicate: (T) -> Boolean, vararg elements: T) =
            elements.forEach { if(predicate(it)) this.add(it) }


}
