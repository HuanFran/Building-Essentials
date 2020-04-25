package com.huanfran.buildingessentials.gui.component.box

import com.huanfran.buildingessentials.gui.component.BEGUIException

class GUIDimension(val min: Int, val pref: Int, val max: Int) {



    init { if(pref < min || pref > max) throw BEGUIException("min: $min, pref: $pref, max: $max. Bounds violated.") }



    constructor(all: Int) : this(all, all, all)



    companion object {

        /**
         * An arbitrarily high integer that shows that a GUI component can be as large as it needs to be. The min
         * parameter should never be set to this.
         */
        val MAX = 100000

        /**
         * Representative of a GUI component being as small as it needs to be. The max parameter should never be set
         * to this. There should never be GUIs of no width or no height.
         */
        val MIN = 0

    }


}