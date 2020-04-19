package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.tile.mirror.Mirrors
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import kotlin.collections.ArrayList

object StaffOfExtension : BEItem("staff_of_extension") {


    override fun onItemUse(context: ItemUseContext): ActionResultType {
        if(!context.world.isRemote) extendAction(context.world, context.pos, context.face, 48)

        return ActionResultType.SUCCESS
    }



    /**
     * Begins an extend action from the [startPos] + the [face] direction vector.
     */
    fun extendAction(world: World, startPos: BlockPos, face: Direction, maxBlocksPlaced: Int) : Int {
        val startState = world.getBlockState(startPos)
        val plane = BuildingPlane.fromDirection(face)

        val firstPos = startPos.add(face.directionVec)

        val surroundings = LinkedList<BlockPos>()

        val processed = ArrayList<BlockPos>()

        surroundings.add(firstPos)

        while(surroundings.size > 0 && processed.size < maxBlocksPlaced + 1) {
            val current = surroundings.poll()

            surroundings.addChecked( { 
                (world.isAirBlock(it) || world.getBlockState(it).fluidState.isSource) && 
                        world.getBlockState(it.add(face.opposite.directionVec)).block == startState.block &&
                        !surroundings.contains(it) && !processed.contains(it) } ,
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

        for(p in processed) {
            actionBuffer.setBlockState(p, startState)

            if(Mirrors.mirroringEnabled) Mirrors.checkPlaceBlock(world, p, startState)
        }

        return processed.size
    }



    /**
     * A simple private helper method for [extendAction]. Individually adds the given [elements] to this
     * [LinkedList] if they satisfy the given [predicate].
     */
    private fun<T> LinkedList<T>.addChecked(predicate: (T) -> Boolean, vararg elements: T) =
            elements.forEach { if(predicate(it)) this.add(it) }


}
