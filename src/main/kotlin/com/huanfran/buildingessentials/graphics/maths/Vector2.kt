package com.huanfran.buildingessentials.graphics.maths

import kotlin.math.sqrt

class Vector2(var x: Double,
              var y: Double) {


    /*
    BASIC OPERATOR ARITHMETIC
     */



    operator fun plus(b: Vector2) = Vector2(x + b.x, y + b.y)

    operator fun plus(s: Double) = Vector2(x + s, y + s)



    operator fun minus(b: Vector2) = Vector2(x - b.x, y - b.y)

    operator fun minus(s: Double) = Vector2(x - s, y - s)



    operator fun times(b: Vector2) = Vector2(x * b.x, y * b.y)

    operator fun times(s: Double) = Vector2(x * s, y * s)



    operator fun div(b: Vector2) = Vector2(x / b.x, y / b.y)

    operator fun div(s: Double) = Vector2(x / s, y / s)



    operator fun unaryMinus() = Vector2(-x, -y)



    /*
    BASIC NON-OPERATOR ARITHMETIC
     */



    fun plus(x: Double, y: Double) = Vector2(this.x + x, this.y + y)

    fun minus(x: Double, y: Double) = Vector2(this.x - x, this.y - y)

    fun times(x: Double, y: Double) = Vector2(this.x * x, this.y * y)

    fun div(x: Double, y: Double) = Vector2(this.x / x, this.y / y)



    /*
    DISTANCES and NORMALS
     */



    /**
     * Shifts x and y, negates y. Maintains length.
     */
    fun leftNormal() = Vector2(-y, x)

    /**
     * Shifts x and y, negates x. Maintains length.
     */
    fun rightNormal() = Vector2(y, -x)

    /**
     * Both normals in an array. [0] = left, [1] = right.
     */
    fun normals() = arrayOf(leftNormal(), rightNormal())

    /**
     * Pythagoras.
     */
    fun length() = sqrt(x*x + y*y)

    /**
     * This vector of length 1 (with some of Kotlin's fancy syntax).
     */
    fun normalised() : Vector2 = length().let { return Vector2(x / it, y / it)}

    /**
     * Distance from this vector to the given vector (this - b).
     */
    fun distance(b: Vector2) = (this - b).length()

    /**
     * This vector scaled to the given length.
     */
    fun ofLength(length: Double) = normalised() * length



    /*
    ANGLES and ORIENTATION
     */



    /**
     * Uses arctan to calculate the angle of this vector from the positive x axis in radians.
     */
    fun angle() = Maths.arcTan(y, x)

    /**
     * Converts all dimensions of this vector from degrees to radians.
     */
    fun toRadians() = Vector2(Maths.toRadians(x), Maths.toRadians(y))

    /**
     * Converts all dimensions of this vector from radians to degrees.
     */
    fun toDegrees() = Vector2(Maths.toDegrees(x), Maths.toDegrees(y))

    /**
     * Used for calculating the angle between two vectors. Dot product = |A||B|cos(Theta).
     */
    infix fun dot(v: Vector2) = x * v.x + y * v.y

    /**
     * Using the dot product across vector dimensions only uses the applicable components.
     */
    infix fun dot(v: Vector3) = x * v.x + y * v.y

    /**
     * The cosine of the angle between two vectors. Dot product = |A||B|cos(Theta).
     */
    fun cosAngleBetween(b: Vector2) = dot(b) / (length() * b.length())

    /**
     * The orientation (clockwise or anticlockwise) of the given vector to the line drawn from the origin to this one.
     */
    fun orientationToThis(p: Vector2) = XOrientation.fromOffset(rightNormal().dot(p))

    /**
     * The smallest (unsigned) angle between this vector and the given vector (using the origin as a common point).
     */
    fun unsignedAngle(p: Vector2) = Maths.arcCos(cosAngleBetween(p))

    /**
     * The anticlockwise signed angle from this vector to the given vector.
     */
    fun leftSignedAngle(p: Vector2) = if(orientationToThis(p).isLeft()) unsignedAngle(p) else Maths.TAU - unsignedAngle(p)

    /**
     * The clockwise signed angle from this vector to the given vector.
     */
    fun rightSignedAngle(p: Vector2) = if(orientationToThis(p) == XOrientation.LEFT) -(Maths.TAU - unsignedAngle(p)) else -unsignedAngle(p)

    /**
     * Uses rotation matrix multiplication.
     */
    fun rotated(radians: Double) : Vector2 {
        val cos = Maths.cos(radians)
        val sin = Maths.sin(radians)

        return Vector2(x * cos - y * sin, x * sin + y * cos)
    }



    companion object { fun origin() = Vector2(0.0, 0.0) }



    override fun toString() = "($x, $y)"


}