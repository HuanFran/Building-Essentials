package com.huanfran.buildingessentials.graphics.maths

import net.minecraft.util.math.BlockPos
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan

object Maths {



    /*
    USEFUL CONSTANTS
     */



    const val TAU = 2 * Math.PI //360 degrees

    const val PI = Math.PI //180 degrees

    const val PI_ON_2 = PI / 2 //90 degrees

    const val PI_ON_3 = PI / 3 //30 degrees

    const val PI_ON_4 = PI / 4 //45 degrees

    const val PI_ON_6 = PI / 6 //30 degrees

    const val ROOT2 = 1.41421356237

    const val ROOT3 = 1.73205080757

    const val SIN45 = ROOT2 / 2 //cos and sin of 45 degrees

    const val COS45 = SIN45 //cos and sin of 45 degrees

    const val COS30 = ROOT3 / 2 //cos of 30 degrees, sin of 60 degrees

    const val SIN60 = COS30 //cos of 30 degrees, sin of 60 degrees



    /*
    TRIG
     */



    fun sin(radians: Double) = kotlin.math.sin(radians)

    fun arcSin(sin: Double) = asin(sin)

    fun cos(radians: Double) = kotlin.math.cos(radians)

    fun arcCos(cos: Double) = acos(cos)

    fun tan(radians: Double) = kotlin.math.tan(radians)

    fun arcTan(oppositeY: Double, adjacentX: Double): Double {
        var tan = atan(oppositeY / adjacentX)

        if (adjacentX < 0)
            tan += PI
        else if (oppositeY < 0)
            tan += TAU

        return tan
    }



    /*
    OTHER THINGS
     */



    fun toRadians(degrees: Double) = degrees / 180.0 * PI

    fun toDegrees(radians: Double) = radians * 180.0 / PI



    /*
    VECTOR LIST EXTENSIONS
     */


    /**
     * Fancy Kotlin
     */
    fun toDoubleArray(vectors: List<Vector3>) = DoubleArray(vectors.size * 3).apply {
        for (i in vectors.indices) {
            val index = i * 3
            val vector = vectors[i]

            this[index] = vector.x
            this[index + 1] = vector.y
            this[index + 2] = vector.z
        }
    }



    fun vertices(pos: BlockPos) = with(pos) {
        arrayListOf(
                Vector3(x.toDouble(), y.toDouble(), z.toDouble()),
                Vector3((x + 1).toDouble(), y.toDouble(), (z + 1).toDouble()),
                Vector3((x + 1).toDouble(), (y + 1).toDouble(), (z + 1).toDouble())
        )
    }


}






























