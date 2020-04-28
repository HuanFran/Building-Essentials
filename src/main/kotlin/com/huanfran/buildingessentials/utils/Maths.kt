package com.huanfran.buildingessentials.utils

import net.minecraft.util.Direction
import kotlin.math.floor

object Maths {





    /*
    Direction
    */



    private val directionsByYawIndex = arrayListOf(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST)

    fun directionFromYaw(yaw: Double) =
            directionsByYawIndex[floor((Math.floorMod(yaw.toInt() + 45, 360) / 90).toDouble()).toInt()]


}