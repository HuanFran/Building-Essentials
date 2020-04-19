package com.huanfran.buildingessentials.gui.component

class ObservableArrayList<T>(
        private val onAdd: (T) -> Unit,
        private val onRemove: (T) -> Unit) : ArrayList<T>() {


    constructor(onAddedOrRemoved: (T) -> Unit) : this(onAddedOrRemoved, onAddedOrRemoved)



    override fun add(element: T) = super.add(element).apply { onAdd(element) }


    override fun remove(element: T) = super.remove(element).apply { onRemove(element) }


}