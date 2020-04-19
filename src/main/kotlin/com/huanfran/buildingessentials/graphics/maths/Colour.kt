package com.huanfran.buildingessentials.graphics.maths

class Colour(var r: Double, var g: Double, var b: Double, var a: Double) {


    constructor(r: Double, g: Double, b: Double) : this(r, g, b, 1.0)

    constructor(all: Double) : this(all, all, all)


}



val BLACK = Colour(0.0)

val WHITE = Colour(1.0)

val BACKGROUND_COLOUR = Colour(0.4, 0.6, 0.3, 1.0)