package com.huanfran.buildingessentials.graphics.maths

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
    MISC.
     */



    fun toVector2() = Vector2(x, y)


    fun toXY() = Vector2(x, y)



    fun toXZ() = Vector2(x, z)



    fun toYZ() = Vector2(y, z)



    override fun toString() = "($x, $y, $z)"


}