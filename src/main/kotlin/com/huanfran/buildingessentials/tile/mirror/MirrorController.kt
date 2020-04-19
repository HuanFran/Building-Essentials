package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.graphics.maths.Vector2
import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.isNextTo
import com.huanfran.buildingessentials.main.toBlockPos
import com.huanfran.buildingessentials.main.toVector3
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

class MirrorController(val pos0: BlockPos,
                       val pos1: BlockPos,
                       val width2: Double,
                       val length2: Double,
                       val height2: Double) {


    /*
    Secondary constructor
     */



    constructor(pos0: BlockPos, pos1: BlockPos) :
            this(pos0, pos1, Mirrors.defaultWidth2, Mirrors.defaultLength2, Mirrors.defaultHeight2)



    /*
    Variables
     */



    var toRotateBy: Double

    var midHeight: Double

    var centre: Vector2

    var end0: Vector2

    var end1: Vector2

    var direction: Vector2

    var rightNormal: Vector2



    /*
    Initialisation
     */



    init {

        //Temporary vector3 representations of the block positions.
        var v0 = pos0.toVector3() + 0.5
        var v1 = pos1.toVector3() + 0.5

        if(pos0.isNextTo(pos1)) {
            val difference = v1 - v0

            v0 += difference / 2.0
            v1 -= difference / 2.0

            //The xy plane in Vector3 is equivalent to the xz plane in block pos.
            v0 += difference.toXY().rightNormal()
            v1 += difference.toXY().leftNormal()
        }

        //The mid point on the y axis (the up-down axis)
        midHeight = (v0.z + v1.z) / 2 + 0.5

        //The centre of the controller, the mid point on the xz plane. The height value is irrelevant here.
        centre = ((v0 + v1) / 2.0).toVector2()

        //The xz direction vector between the first and second positions. Length of 1.
        direction = Vector2(v1.x - v0.x, v1.y - v0.y).normalised()

        //The angle that will align the mirror's active area to the xz axes.
        toRotateBy = -direction.angle()

        //The normal to the right-hand side of the direction.
        rightNormal = direction.rightNormal()

        //The ends of the mirror, used for rendering.
        end0 = direction * length2
        end1 = - direction * length2
    }



    /*
    Functions
     */



    /**
     * Note: The vector [v] must be in the format (x,y,z) where x = minecraft's x, y = minecraft's z, z = minecraft's y.
     * If this vector represents a block, it must be placed in the centre of that block. Use the function [toVector3] to
     * do this automatically.
     */
    private fun isInActiveArea(v: Vector3) : Boolean {
        //Rotate the given vector by the required amount so that the vector is on the same axes as the bounding square.
        val v0 = Vector2(v.x - centre.x, v.y - centre.y).rotated(toRotateBy)

        //Check the height using the midHeight.
        if(v.z > midHeight - height2 && v.z < midHeight + height2) {

            //Then check the x and y values against the length and width.
            if(v0.x > -length2 &&
                    v0.x < length2 &&
                    v0.y > -width2 &&
                    v0.y < width2) {
                return true
            }
        }

        return false
    }



    /**
     * Gets the vector that is opposite the given [vector][v] relative to the mirror plane.
     */
    private fun oppositePos(v: Vector3) = v - (rightNormal * (rightNormal dot (v - centre)) * 2.0)



    /**
     * Returns the [oppositePos] if the given [vector][v] satisfies the [isInActiveArea] function. Otherwise, returns
     * null.
     */
    private fun oppositePosChecked(v: Vector3) = if(isInActiveArea(v)) oppositePos(v) else null



    /**
     * Attempts to place a block at the mirrored position of [v], then calls this function for all other controllers
     * contained within the given [handler]. This allows mirrors to affect each other when blocks are placed. The
     * [iteration] prevents blocks from being repeatedly placed. The highest iteration is set as two by default, meaning
     * that a block placement can only be mirrored twice.
     */
    fun checkPlaceBlock(world: IWorld, v: Vector3, state: BlockState, handler: MirrorHandler, iteration: Int) {
        oppositePosChecked(v)?.let { opposite ->
            world.setBlockState(opposite.toBlockPos(), state, 3)

            if(iteration == 1) return

            handler.controllers.forEach { c -> if(c != this) c.checkPlaceBlock(world, opposite, state, handler, iteration + 1) }
        }
    }



    /**
     * Attempts to break the block at the mirrored position of [v], then calls this function for all other controllers
     * contained within the given [handler]. This allows mirrors to affect each other when blocks are broken. The
     * [iteration] prevents blocks from being repeatedly broken. The highest iteration is set as two by default, meaning
     * that a block break can only be mirrored twice.
     */
    fun checkBreakBlock(world: IWorld, v: Vector3, handler: MirrorHandler, iteration: Int) {
        oppositePosChecked(v)?.let { opposite ->
            world.removeBlock(opposite.toBlockPos(), false)

            if(iteration == 1) return

            handler.controllers.forEach { c -> if(c != this) c.checkBreakBlock(world, opposite, handler, iteration + 1) }
        }
    }


}