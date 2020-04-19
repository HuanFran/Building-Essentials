package com.huanfran.buildingessentials.item

import net.minecraft.util.Direction
import net.minecraft.util.math.Vec3i

enum class BuildingPlane(val d0: Vec3i, val d1: Vec3i) {



    XY(Vec3i(1, 0, 0), Vec3i(0, 1, 0)),

    XZ(Vec3i(1, 0, 0), Vec3i(0, 0, 1)),

    YZ(Vec3i(0, 1, 0), Vec3i(0, 0, 1));



    val d0Neg = Vec3i(-d0.x, -d0.y, -d0.z)

    val d1Neg = Vec3i(-d1.x, -d1.y, -d1.z)



    companion object {


        fun fromDirection(direction: Direction) = when(direction) {
            Direction.DOWN -> XZ
            Direction.UP -> XZ
            Direction.NORTH -> XY
            Direction.SOUTH -> XY
            Direction.WEST -> YZ
            Direction.EAST -> YZ
        }


    }


}