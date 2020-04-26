package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.toVector3
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.networking.MirrorCreationPacket
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
     * The location of the lastly-placed mirror node that isn't part of a controller. This is used to create
     * the pairs of nodes that exist in and define [MirrorController]s.
     */
    var currentPos: Vector3? = null

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
     * Client-side only.
     */
    fun handleMirrorNodeCreation(pos: Vector3) {
        if(currentPos == pos) return

        controllers.forEach { if(pos == it.v0 || pos == it.v1) return }

        currentPos?.let { BEPacketHandler.HANDLER.sendToServer(MirrorCreationPacket(it, pos, Mirrors.defaultWidth2, Mirrors.defaultLength2, Mirrors.defaultHeight2)) }

        currentPos = if(currentPos == null)
            pos
        else
            null
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



    fun handleMirrorRemoval(v0: Vector3, v1: Vector3) = controllers.removeIf { it.v0 == v0 && it.v1 == v1 }



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