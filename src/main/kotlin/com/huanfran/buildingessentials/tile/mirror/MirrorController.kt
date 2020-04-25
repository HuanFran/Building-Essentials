package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.graphics.maths.Maths
import com.huanfran.buildingessentials.graphics.maths.Plane
import com.huanfran.buildingessentials.graphics.maths.Vector2
import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.*
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemUseContext
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.IWorld

/**
 * The core of mirroring in Building Essentials. Represents a mirror defined by the mirror nodes at [pos0] and [pos1].
 * The [width2], [length2], and [height2] are the width, length, and height of the mirror's active area divided by
 * two. They are given this way to make the maths a bit simpler.
 */
class MirrorController(val pos0: BlockPos,
                       val pos1: BlockPos,
                       val width2: Double,
                       val length2: Double,
                       val height2: Double) {


    /*
    Secondary constructor
     */



    /**
     * Constructs this mirror controller with the width, length, and height set to default values.
     */
    constructor(pos0: BlockPos, pos1: BlockPos) :
            this(pos0, pos1, Mirrors.defaultWidth2, Mirrors.defaultLength2, Mirrors.defaultHeight2)



    /*
    Variables
     */


    /**
     * The angle of the mirror's direction vector. This is measured anticlockwise from the positive x axis. Minecraft's
     * yaw system works differently, going positive Z -> negative X -> negative Z -> positive X etc.
     */
    var directionAngle: Double

    var directionAngleYaw: Double

    var centre: Vector3

    var end0: Vector3

    var end1: Vector3

    var direction: Vector2

    var rightNormal: Vector2

    var mirrorPlane: Plane



    /*
    Initialisation
     */



    init {
        //Temporary vector3 representations of the block positions.
        var v0 = pos0.toVector3() + 0.5
        var v1 = pos1.toVector3() + 0.5

        //The centre of the controller, i.e. the average of v0 and v1.
        centre = (v0 + v1) / 2.0

        //The xz direction vector between the first and second positions. Length of 1.
        direction = (v1 - v0).xz.normalised()

        //Rotating by -directionAngle will align the mirror's active area to the xz plane.
        directionAngle = direction.angle()

        //Transforming the direction angle into the yaw angle convention that Minecraft works in.
        directionAngleYaw = Maths.toDegrees(directionAngle) + 90

        //The normal to the right-hand side of the direction.
        rightNormal = direction.rightNormal()

        //The ends of the mirror, used for rendering.
        end0 = (direction * length2).asXZ(centre.y)
        end1 = (-direction * length2).asXZ(centre.y)

        //The plane that represents this mirror.
        mirrorPlane = Plane(centre, (direction.asXZ(centre.y) cross Vector3(0.0, 1.0, 0.0)).normalised())
    }



    /*
    Functions
     */



    /**
     * Checks if the given [vector][v] is in this mirror's active area. I.e. if it should be mirrored.
     */
    fun isInActiveArea(v: Vector3) : Boolean {
        //Rotate the given vector so that the vector is on the same axes as the bounding square.
        val v1 = (v - centre).rotatedXZ(-directionAngle)

        //v1 is the distance between v and the centre, so no distances have to be checked, only dimensions.
        if(v1.y > -height2 && v1.y < height2 &&
                v1.x > -length2 && v1.x < length2 &&
                v1.z > -width2 && v1.z < width2)
            return true

        return false
    }



    /**
     * Gets the vector that is opposite the given [vector][v] relative to the mirror plane. How this was calculated can
     * be found here: https://www.desmos.com/calculator/ajvvob2586.
     */
    fun mirroredPos(v: Vector3) = (v.xz - (rightNormal * (rightNormal dot (v - centre).xz) * 2.0)).asXZ(v.y)



    /**
     * Returns the [mirroredPos] if the given [vector][v] satisfies the [isInActiveArea] function. Otherwise, returns
     * null.
     */
    fun mirroredPosChecked(v: Vector3) = if(isInActiveArea(v)) mirroredPos(v) else null



    /**
     * Calculates the mirrored angle of the given [yaw] angle (generally a player's rotation yaw). This is the
     * equivalent of [mirroredPos] for angles.
     */
    fun mirroredAngle(yaw: Double) = 2 * directionAngleYaw - yaw



    fun intersection(playerPos: Vector3, playerLook: Vector3) = mirrorPlane.intersection(playerPos, playerLook, isOnRightSide(playerPos))



    fun intersectionChecked(playerPos: Vector3, playerLook: Vector3) = intersection(playerPos, playerLook).let {
        if(it != null && isInActiveArea(it))
            it
        else
            null
    }



    fun isOnRightSide(v: Vector3) = (v - centre).xz dot rightNormal > 0



    /**
     * Mimics Minecraft's process of placing a block from the player's hand. This is done on the opposite side of this
     * mirror. Rather than taking a [IWorld], this method takes a [BEActionBuffer], which allows for undo-redo logic to
     * be applied. The placement must take place within the mirrors [active area][isInActiveArea].
     */
    fun checkPlaceBlock(buffer: BEActionBuffer,
                        player: PlayerEntity,
                        pos: BlockPos,
                        state: BlockState,
                        hitVec: Vec3d,
                        direction: Direction,
                        yaw: Double,
                        handler: MirrorHandler,
                        iteration: Int) {
        //The position must be offset by 0.5 before mirroring in order to place it at the centre of the block.
        val mirrored = mirroredPosChecked(pos.toVector3() + 0.5) ?: return
        val mirroredPos = mirrored.toBlockPos()
        val mirroredState = buffer.world.getBlockState(mirroredPos)

        //Preventing situations where multiple mirrors try to mirror to the same location, which often happens with
        //square or radial multi-mirror configurations.
        if(handler.intraEventMirroredPositions.contains(mirroredPos)) return

        //If the position is on the mirror's direction line, there is no need to mirror. This is the only situation where this will trigger.
        if(mirroredPos == pos) return

        //Prevent (or allow, based on configuration) mirrored blocks from destroying already-present blocks. Slabs are
        //a special case as placing a top-slab requries a bottom-slab to be present.
        if(!Mirrors.mirrorReplacingEnabled && !isReplacable(buffer.world, mirroredPos)
                && mirroredState.block !is SlabBlock && state.block !is SlabBlock) return

        //The hitVec must be mirrored as it provides directional details.
        val mirroredHitVec = mirroredPos(hitVec.toVector3()).toVec3d()

        //The mirrored angle that the player is facing (on xz plane).
        val mirroredYaw = mirroredAngle(yaw)

        //The mirrored xz direction of the player. Depending on the block, this will be reversed. For example, chests
        //and furnaces always face towards the player when placed, but stairs face away.
        val mirroredHorizontalDirection = if(state.block !is StairsBlock)
            directionFromYaw(mirroredYaw).opposite
        else
            directionFromYaw(mirroredYaw)

        //Creation of a ray trace result with the mirrored parameters to mimic a mirrored position of the player.
        val mirroredRayTrace = BlockRayTraceResult(mirroredHitVec, direction, mirroredPos, false)

        //Creating an item use context with the mirrored information that will be used to place the block.
        val useContext = ItemUseContext(player, player.activeHand, mirroredRayTrace)

        //This is the state that the mirrored block will be in.
        var placementState = state.block.getStateForPlacement(BlockItemUseContext(useContext)) ?: return

        //Configure the facing as it is not actually set (unfortunately) from the mirrored ray trace result, but rather
        //directly from the player's facing in the getStateForPlacement method.
        if(placementState.properties.contains(HorizontalBlock.HORIZONTAL_FACING))
            placementState = placementState.with(HorizontalBlock.HORIZONTAL_FACING, mirroredHorizontalDirection)

        //This is where the mirrored position and state is set.
        buffer.setBlockState(mirroredPos, placementState)

        //Post-placement logic is run here. This is called after setBlockState in Minecraft's original place item methods.
        state.block.onBlockPlacedBy(useContext.world, mirroredPos, placementState, player, useContext.item)

        //Add the mirrored placement to the list to avoid potential re-mirroring (see the start of this function).
        handler.intraEventMirroredPositions.add(mirroredPos)

        //Only allow two iterations (the block has already been placed) to avoid endless looping issues.
        if(iteration == 1) return

        //Allow mirroring to affect other mirrors, enabling radial configurations and more.
        handler.controllers.forEach { c -> if(c != this) c.checkPlaceBlock(buffer, player, mirroredPos, state, mirroredHitVec, direction, mirroredYaw, handler, iteration + 1) }
    }



    /**
     * Attempts to break the block at the mirrored position of [v], then calls this function for all other controllers
     * contained within the given [handler]. This allows mirrors to affect each other when blocks are broken. The
     * [iteration] prevents blocks from being repeatedly broken. The highest iteration is set as two by default, meaning
     * that a block break can only be mirrored twice.
     */
    fun checkBreakBlock(buffer: BEActionBuffer, v: Vector3, handler: MirrorHandler, iteration: Int) {
        mirroredPosChecked(v)?.let { opposite ->
            buffer.removeBlock(opposite.toBlockPos())

            if (iteration == 1) return

            handler.controllers.forEach { c -> if (c != this) c.checkBreakBlock(buffer, opposite, handler, iteration + 1) }
        }
    }


}