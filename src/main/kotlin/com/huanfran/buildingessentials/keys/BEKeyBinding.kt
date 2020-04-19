package com.huanfran.buildingessentials.keys

import com.huanfran.buildingessentials.main.DISPLAY_NAME
import net.minecraft.client.settings.KeyBinding

class BEKeyBinding(name: String, key: Int) : KeyBinding(name, key, DISPLAY_NAME) {


    init {
        KeyBindings.bindings.add(this)
    }


}