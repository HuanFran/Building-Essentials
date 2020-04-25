package com.huanfran.buildingessentials.keys

import com.huanfran.buildingessentials.main.DISPLAY_NAME
import net.minecraft.client.settings.KeyBinding

/**
 * A [KeyBinding] associated with the Building Essentials mod.
 */
class BEKeyBinding(name: String, key: Int) : KeyBinding(name, key, DISPLAY_NAME) {


    init {
        KeyBindings.bindings.add(this)
    }


}