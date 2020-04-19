package com.huanfran.buildingessentials.gui.component.box

import com.huanfran.buildingessentials.gui.component.VAlignment

/**
 * A horizontal alignment box for the Building Essentials mod.
 */
class BEHBox(x: Int, y: Int) : BEBox(x, y) {


    /**
     * The vertical alignment of the children.
     */
    var vAlignment = VAlignment.CENTRE

    /**
     * The largest height amongst the children.
     */
    var largestHeight = 0



    override fun alignElements() {
        var currentX = padding.l

        children.forEach {
            it.x = currentX
            currentX += it.width + spacing

            it.y = when(vAlignment) {
                VAlignment.TOP -> padding.t
                VAlignment.CENTRE -> padding.t - (largestHeight - it.height) / 2
                VAlignment.BOTTOM -> padding.t + largestHeight - it.height
            }
        }

        height = padding.t + largestHeight + padding.b
        width = currentX + padding.r
    }



    override fun updateLargestDimension() {
        largestHeight = 0

        children.forEach { if(it.width > largestHeight) largestHeight = it.width }
    }


}