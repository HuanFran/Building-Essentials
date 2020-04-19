package com.huanfran.buildingessentials.gui.component.box

import com.huanfran.buildingessentials.gui.component.BEComponent

abstract class BEBox(x: Int, y: Int) : BEComponent(x, y, 0, 0) {


    /**
     * The distance between children,
     */
    var spacing = 0



    override fun onAdd(child: BEComponent) {
        super.onAdd(child)

        updateLargestDimension()

        alignElements()
    }



    override fun onRemove(child: BEComponent) {
        super.onRemove(child)

        updateLargestDimension()

        alignElements()
    }



    override fun onChildRescaled(child: BEComponent) {
        updateLargestDimension()

        alignElements()
    }



    /**
     * Realigns all children of this Box. Updates their positions based on the [spacing], [padding], and horizontal
     * or vertical alignment. This is called whenever a child is added, removed, or rescaled.
     */
    abstract fun alignElements()



    /**
     * Calculates either the largest width or the largest height of the children. This is called whenever a child is
     * added, removed, or rescaled.
     */
    abstract fun updateLargestDimension()


}