package com.huanfran.buildingessentials.gui.component

import com.huanfran.buildingessentials.gui.component.box.GUIDimension

/**
 * No primary constructor as the variables need custom setters.
 */
abstract class BEComponent {


    /*
    Variables
     */



    var x: Int = 0
        set(value) { field = value; updateOffset()}



    var y: Int = 0
        set(value) { field = value; updateOffset()}



    var totalX = x
        private set



    var totalY = y
        private set



    var width: Int = 0
        set(value) { field = value; parent?.onChildRescaled(this) }



    var height: Int = 0
        set(value) { field = value; parent?.onChildRescaled(this) }



    /**
     * The min, pref, and max widths of this component. This is initialised to zero, but should be modified to its
     * appropriate values before use in a GUI.
     */
    var widths = GUIDimension(0)



    /**
     * The min, pref, and max heights of this component. This is initialised to zero, but should be modified to its
     * appropriate values before use in a GUI.
     */
    var heights = GUIDimension(0)



    val children = ObservableArrayList(::onAdd, ::onRemove)



    var parent: BEComponent? = null
        set(value) { field = value; updateOffset() }



    /**
     * The minimum distances between this component's borders and any child component's borders.
     */
    var padding = BufferArea(0)


    /**
     * The minimum distances between this component's borders and any adjacent component's borders.
     */
    var margin = BufferArea(0)



    /*
    Secondary constructors
     */



    constructor(x: Int, y: Int, width: Int, height: Int, parent: BEComponent? = null) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        this.parent = parent

        updateOffset()
    }



    constructor()



    /*
    Functions
     */



    protected open fun updateOffset() {
        totalX = x
        totalY = y

        parent?.let { totalX += it.x; totalY += it.y }

        children.forEach { it.updateOffset() }
    }



    protected open fun onAdd(child: BEComponent) {
        child.updateOffset()
        child.parent = this
    }



    protected open fun onRemove(child: BEComponent) {
        child.updateOffset()
        child.parent = null
    }



    protected open fun onChildRescaled(child: BEComponent) {}


}