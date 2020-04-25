package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.toVector3
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.IWorld

/**
 * Handles [MirrorController]s created on a logical side, either the logical client or the logical server. There are
 * only ever two instances of this class, both found in the [Mirrors] object. This class is necessary as the action
 * must be split between the two logical sides to avoid concurrency errors.
 */
class MirrorHandler {


    /**
     * The [BlockPos] where the last mirror node was placed that isn't part of a controller. This is used to create
     * the [BlockPos] pairs that exist in [MirrorController]s.
     */
    private var currentPos: BlockPos? = null

    /**
     * All active [MirrorController]s that have been created on the logical side represented by this [MirrorHandler].
     */
    val controllers = ArrayList<MirrorController>()

    /**
     * A list of all mirrored block placements that have taken place during the current place event. This is used to
     * prevent duplicate mirroring in multi-mirror configurations.
     */
    val intraEventMirroredPositions = ArrayList<BlockPos>()



    /**
     * Handles the creation of a mirror block. This assigns controllers where necessary, using the [currentPos] variable
     * and the [controllers] list.
     */
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



    /**
     * Version of [checkPlaceBlock] that takes [BEActionBuffer] in place of [IWorld]. This allows for undo-redo
     * functionality involving mirrors.
     */
    fun checkPlaceBlock(buffer: BEActionBuffer, player: PlayerEntity, pos: BlockPos, state: BlockState) {
        val rayTrace = Minecraft.getInstance().objectMouseOver as BlockRayTraceResult

        controllers.forEach { it.checkPlaceBlock(buffer, player, pos, state, rayTrace.hitVec, rayTrace.face, player.rotationYaw.toDouble(), this, 0) }

        intraEventMirroredPositions.clear()
    }



    /**
     * Handles the mirroring of a block placement made by the given [player].
     */
    fun checkPlaceBlock(world: IWorld, player: PlayerEntity, pos: BlockPos, state: BlockState) =
            checkPlaceBlock(BEActionBuffer(world, false), player, pos, state)



    /**
     * Handles the mirroring of a block removal in the given [world] at the given [pos].
     */
    fun checkBreakBlock(world: IWorld, pos: BlockPos) =
            controllers.forEach { it.checkBreakBlock(BEActionBuffer(world, false), pos.toVector3() + 0.5, this, 0) }



    /**
     * Version of [checkBreakBlock] taking [BEActionBuffer] in place of [IWorld]. This allows for undo-redo
     * functionality.
     */
    fun checkBreakBlock(buffer: BEActionBuffer, pos: BlockPos) =
            controllers.forEach { it.checkBreakBlock(buffer, pos.toVector3() + 0.5, this, 0) }



    /**
     * Handles the removal of a mirror block from the [world] at the given [pos]. If the node is part of a
     * [MirrorController], the controller is removed from the [controllers] list and the other node of the controller's
     * pair is also broken.
     */
    fun handleMirrorRemoval(world: IWorld, pos: BlockPos) {
        //See if the mirror node is part of a controller pair.
        val controllerToRemove = controllers.firstOrNull {
            pos == it.pos0 || pos == it.pos1
        }

        controllerToRemove?.let {
            controllers.remove(it)

            //Remove the other member of the pair.
            if(pos == it.pos0)
                world.removeBlock(it.pos1, false)
            else
                world.removeBlock(it.pos0, false)
        }

        //Reset the current pos if the mirror node at pos was not part of a controller.
        if(controllerToRemove == null) currentPos = null

        controllers.remove(controllerToRemove)
    }



    /**
     * Strictly client-side
     */
    fun lookingAt(player: PlayerEntity, playerPos: Vector3) : MirrorController? {
        var closestController: MirrorController? = null
        var shortestDistance: Double? = null

        controllers.forEach { c ->

            c.intersectionChecked(playerPos, player.lookVec.toVector3())?.let { p ->

                (p - playerPos).length().let {
                    if (shortestDistance == null || it < shortestDistance!!) {
                        closestController = c
                        shortestDistance = it
                    }
                }
            }
        }

        //Return null if there are blocks in the way.
        shortestDistance?.let { d ->
            val trace = player.pick(d, 0F, false) as BlockRayTraceResult

            if(trace.type == RayTraceResult.Type.BLOCK && (trace.hitVec.toVector3() - playerPos).length() < d)
                return null
        }

        return closestController
    }


}