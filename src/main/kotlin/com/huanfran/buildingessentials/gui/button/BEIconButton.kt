package com.huanfran.buildingessentials.gui.button

import com.huanfran.buildingessentials.utils.BETextureLocation

class BEIconButton(width: Int, height: Int, tooltipText: String, res: BETextureLocation, val onPress: (BEIconButton) -> Unit) : BETextlessButton(width, height, tooltipText, res) {


    constructor(tooltipText: String, res: BETextureLocation, onPress: (BEIconButton) -> Unit) :
            this(32, 32, tooltipText, res, onPress)



    override fun onPress() = onPress(this)


}