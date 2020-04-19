package com.huanfran.buildingessentials.gui.component

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



    var width: Int = 0
        set(value) { field = value; parent?.onChildRescaled(this) }



    var height: Int = 0
        set(value) { field = value; parent?.onChildRescaled(this) }



    var totalX = x
        private set



    var totalY = y
        private set



    val children = ObservableArrayList(::onAdd, ::onRemove)



    var parent: BEComponent? = null
        set(value) { field = value; updateOffset() }



    /**
     * The space between the border and the left-most, right-most, top-most, and bottom-most children.
     */
    var padding = BufferArea(0)

    /**
     * The space between the border and adjacent components.
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