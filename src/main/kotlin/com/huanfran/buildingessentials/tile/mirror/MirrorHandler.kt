package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.main.toVector3
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

class MirrorHandler {


    private var currentPos: BlockPos? = null

    val controllers = ArrayList<MirrorController>()



    fun handleMirrorNodeCreation(pos: BlockPos) : MirrorController? =
        if(currentPos == null) {
            currentPos = pos

            null
        } else {
            val controller = MirrorController(currentPos!!, pos)

            controllers.add(controller)

            currentPos = null

            controller
        }



    fun checkPlaceBlock(world: IWorld, pos: BlockPos, state: BlockState) =
        controllers.forEach { it.checkPlaceBlock(world, pos.toVector3() + 0.5, state, this, 0) }



    fun checkBreakBlock(world: IWorld, pos: BlockPos) =
        controllers.forEach { it.checkBreakBlock(world, pos.toVector3() + 0.5, this, 0) }



    fun handleMirrorRemoval(world: IWorld, pos: BlockPos) {
        val controllerToRemove = controllers.firstOrNull {
            pos == it.pos0 || pos == it.pos1
        }

        controllerToRemove?.let {
            controllers.remove(it)

            if(pos == it.pos0)
                world.removeBlock(it.pos1, false)
            else
                world.removeBlock(it.pos0, false)
        }

        //Reset the current pos if the mirror node at pos was not part of a controller.
        if(controllerToRemove == null) currentPos = null

        controllers.remove(controllerToRemove)
    }


}