package com.huanfran.buildingessentials.utils.extensions

import com.huanfran.buildingessentials.graphics.maths.Vector3
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import kotlin.math.floor


fun BlockPos.toVector3() = Vector3(x.toDouble(), y.toDouble(), z.toDouble())



fun Vector3.toBlockPos() = BlockPos(floor(x).toInt(), floor(y).toInt(), floor(z).toInt())



fun BlockPos.isNextTo(pos: BlockPos) =
        x in pos.x - 1..pos.x + 1 &&
                y in pos.y - 1..pos.y + 1 &&
                z in pos.z - 1..pos.z + 1



fun Vec3d.toVector3() = Vector3(x, y, z)



fun Vector3.toVec3d() = Vec3d(x, y, z)



fun Vec3d.toBlockPos() = BlockPos(floor(x).toInt(), floor(y).toInt(), floor(z).toInt())