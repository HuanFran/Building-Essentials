package com.huanfran.buildingessentials.graphics.maths

enum class AngleNature {


    /**
     * The angle is between 0 and 180 degrees (0 to pi radians)
     */
    CONCAVE,

    /**
     * The angle is between 180 and 360 degrees (pi to 2pi radians)
     */
    CONVEX,

    /**
     * The angle is either 0 or 180 degrees (0 or pi radians)
     */
    FLAT;



    companion object {

        fun fromAngle(degrees: Double) = when(degrees) {0.0, 180.0 -> FLAT; in 0..180 -> CONCAVE else -> CONVEX}

        fun fromSin(sin: Double) = when {sin > 0 -> CONCAVE; sin < 0 -> CONVEX; else -> FLAT }

    }



    fun opposite() = when(this) {CONCAVE -> CONVEX; CONVEX -> CONCAVE; else -> FLAT}

    fun isConcave() = this == CONCAVE

    fun isConvex() = this == CONVEX

    fun isFlat() = this == FLAT


}