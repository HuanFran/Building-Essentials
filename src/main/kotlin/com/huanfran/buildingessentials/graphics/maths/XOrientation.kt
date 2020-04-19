package com.huanfran.buildingessentials.graphics.maths

/**
 * This is more likely to be used to talk about directions in a circular sense rather than those on the X axis. How
 * do you talk about directions when moving around a circle? Take two lines that both originate from the origin. What is
 * the angle between those two lines? Well, there are two equally valid ones as you can trace an angle going clockwise
 * or anticlockwise from one line to the other. To make it clear which angle we are interested in, we can specify the
 * clockwise or anticlockwise angle or 'direction' using this enum. Writing anticlockwise and clockwise is tedious,
 * not concise, and is slow to visualise. Instead, we can say that left and right are synonymous with anticlockwise and
 * clockwise respectively. If you imagine that the point of view of a line originating from the origin is 'looking' from
 * the origin to the vector, you can see how left will always be anticlockwise and right will always be clockwise.
 */
enum class XOrientation {


    /**
     * ANTICLOCKWISE
     */
    LEFT,

    /**
     * SAME X POSITION
     */
    CENTRE,

    /**
     * CLOCKWISE
     */
    RIGHT,

    /**
     * USED WITH QUADRATIC BEZIER CURVES
     */
    NONE;



    fun isRight() = this == RIGHT

    fun isCentre() = this == CENTRE

    fun isLeft() = this == LEFT

    fun isNone() = this == NONE



    companion object {
        fun fromOffset(offset: Double) = if (offset > 0) XOrientation.RIGHT else if (offset < 0) XOrientation.LEFT else XOrientation.CENTRE
    }


}