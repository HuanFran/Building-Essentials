package com.huanfran.buildingessentials.gui.component.box

import com.huanfran.buildingessentials.gui.component.HAlignment

/**
 * A vertical alignment box for the Building Essentials mod.
 */
class BEVBox(x: Int, y: Int) : BEBox(x, y) {


    /**
     * The highest width amongst the children.
     */
    var highestWidth = 0



    override fun alignElements() {
        updateLargestDimension()

        var currentY = padding.t

        children.forEach {
            it.y = currentY
            currentY += it.height + spacing

            it.x = when(hAlignment) {
                HAlignment.LEFT -> padding.l
                HAlignment.CENTRE -> padding.l - (highestWidth - it.width) / 2
                HAlignment.RIGHT -> padding.l + highestWidth - it.width
            }
        }

        width = highestWidth + padding.l + padding.r
        height = currentY + padding.b
    }



    fun test() {
        updateLargestDimension()
    }



    override fun updateLargestDimension() {
        highestWidth = 0

        children.forEach { if(it.width > highestWidth) highestWidth = it.width }
    }


}