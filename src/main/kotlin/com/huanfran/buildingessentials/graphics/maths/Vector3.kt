package com.huanfran.buildingessentials.graphics.maths

import kotlin.math.round
import kotlin.math.sqrt

class Vector3(var x: Double,
              var y: Double,
              var z: Double) {


    /*
    BASIC OPERATOR ARITHMETIC.
     */



    operator fun plus(b: Vector3) = Vector3(x + b.x, y + b.y, z + b.z)

    operator fun plus(b: Vector2) = Vector3(x + b.x, y + b.y, z)

    operator fun plus(s: Double) = Vector3(x + s, y + s, z + s)



    operator fun minus(b: Vector3) = Vector3(x - b.x, y - b.y, z - b.z)

    operator fun minus(b: Vector2) = Vector3(x - b.x, y - b.y, z)

    operator fun minus(s: Double) = Vector3(x - s, y - s, z - s)



    operator fun times(b: Vector3) = Vector3(x * b.x, y * b.y, z * b.z)

    operator fun times(b: Vector2) = Vector3(x * b.x, y * b.y, z)

    operator fun times(s: Double) = Vector3(x * s, y * s, z * s)



    operator fun div(b: Vector3) = Vector3(x / b.x, y / b.y, z / b.z)

    operator fun div(b: Vector2) = Vector3(x / b.x, y / b.y, z)

    operator fun div(s: Double) = Vector3(x / s, y / s, z / s)



    operator fun unaryMinus() = Vector3(-x, -y, -z)



    /*
    BASIC NON-OPERATOR ARITHMETIC.
    */



    fun plus(x: Double, y: Double, z: Double) = Vector3(this.x + x, this.y + y, this.z + z)

    fun plus(x: Double, y: Double) = Vector3(this.x + x, this.y + y, z)



    fun minus(x: Double, y: Double, z: Double) = Vector3(this.x - x, this.y - y, this.z - z)

    fun minus(x: Double, y: Double) = Vector3(this.x - x, this.y - y, z)



    fun times(x: Double, y: Double, z: Double) = Vector3(this.x * x, this.y * y, this.z * z)

    fun times(x: Double, y: Double) = Vector3(this.x * x, this.y * y, z)



    fun div(x: Double, y: Double, z: Double) = Vector3(this.x / x, this.y / y, this.z / z)

    fun div(x: Double, y: Double) = Vector3(this.x / x, this.y / y, z)



    /*
    Other
     */



    fun length() = sqrt(x*x + y*y + z * z)

    fun normalised() : Vector3 = length().let { return Vector3(x / it, y / it, z / it)}

    infix fun dot(v: Vector3) = v.x * x + v.y * y + v.z * z

    infix fun cross(v: Vector3) = Vector3(
            y*v.z - z*v.y,
            z*v.x - x*v.z,
            x*v.y - y*v.x)

    fun round() = Vector3(round(x), round(y), round(z))

    fun roundToHalf() = (this * 2.0).round() / 2.0



    /*
    Rotation
     */



    fun rotatedXY(radians: Double) = xy.rotated(radians).asXY(z)

    fun rotatedXZ(radians: Double) = xz.rotated(radians).asXZ(y)

    fun rotatedYZ(radians: Double) = yz.rotated(radians).asYZ(x)



    /*
    MISC.
     */



    var xy: Vector2 = Vector2(x, y)
        get() = Vector2(x,y)



    var xz: Vector2 = Vector2(x, z)
        get() = Vector2(x,z)



    var yz: Vector2 = Vector2(y, z)
        get() = Vector2(y,z)



    override fun toString() = "($x, $y, $z)"



    override fun equals(other: Any?) = other is Vector3 && other.x == x && other.y == y && other.z == z


}