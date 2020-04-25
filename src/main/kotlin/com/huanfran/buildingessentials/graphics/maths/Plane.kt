package com.huanfran.buildingessentials.graphics.maths

/**
 * [p0] is the origin of the plane (or simply a point on the plane). [n] is either of the two normals to the plane.
 */
class Plane(val p0: Vector3, val n: Vector3) {



    constructor(p0: Vector3, p1: Vector3, p2: Vector3) : this(p0, (p1 cross p2).normalised())



    /**
     * Finds the intersection point of this plane and the ray cast from the given [point][v] in the given
     * [direction][l]. The parameter [onNormalSide] is whether the point is on the normal-facing side of the plane or
     * not.
     */
    fun intersection(v: Vector3, l: Vector3, onNormalSide: Boolean) : Vector3? {
        val denominator = l dot n

        return if(denominator <= 0.0 == onNormalSide)
            null
        else
            v + l * (((p0 - v) dot n) / denominator)
    }


}