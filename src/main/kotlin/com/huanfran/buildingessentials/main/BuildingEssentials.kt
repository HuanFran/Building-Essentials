package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.networking.BEPacketHandler
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(MODID)
object BuildingEssentials {


    init {
        FORGE_BUS.register(this)
        MOD_BUS.register(this)

        KeyBindings.init()

        BEPacketHandler.register()
    }


}



/*
ISSUES:

SOLVED Staff of extension undo doesn't work with mirrors. Undo in general doesn't work with mirrors
SOLVED Mirror rendering getting mixed with block rendering - graphical issue
SOLVED Mirrors are only rendered on one side when it is raining.
SOLVED Tooltips are rendered behind buttons

SOLVED Slabs placed on slabs don't work with mirrors.
SOLVED Orientation is preserved during mirroring - should be flipped
SOLVED Anything with multiple blocks (e.g. doors) doesn't mirror properly. Only the bottom half gets placed.


Mirror transparency is a bit odd when two mirrors intersect. This isn't a big deal but it would be nice to solve

Global mirrors aren't affected by fog - Not that big of an issue.

Staff of extension causes world lag when used too often. A better way to set blocks?

Mirrors don't work well between opening and closing worlds

Equipping an item should remove the selected feature from the opposite toolbar

Equipping an item should not replace the currently selected item in the normal toolbar.

Mirrors go between worlds - static objects are not good apparently. This may affect quite a few other things.

Placing a slab underneath an upper slab in a mirror will remove the upper slab on the mirrored side.

STATIC VARIABLES ARE NOT GOOD: THEY DON'T WORK BETWEEN WORLDS.
 */