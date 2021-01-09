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
TODO: The building plane and the staff of annihilation are very inefficient. Use HASHSET.
 */



/*
ISSUES:

SOLVED Staff of extension undo doesn't work with mirrors. Undo in general doesn't work with mirrors
SOLVED Mirror rendering getting mixed with block rendering - graphical issue
SOLVED Mirrors are only rendered on one side when it is raining.
SOLVED Tooltips are rendered behind buttons

SOLVED Slabs placed on slabs don't work with mirrors.
SOLVED Orientation is preserved during mirroring - should be flipped
SOLVED Anything with multiple blocks (e.g. doors) doesn't mirror properly. Only the bottom half gets placed.

SOLVED Mirrors don't work well between opening and closing worlds
SOLVED Mirrors go between worlds - static objects are not good apparently. This may affect quite a few other things.
SOLVED Mirrors can be broken in the inventory screen.



Mirror transparency is a bit odd when two mirrors intersect. This isn't a big deal but it would be nice to solve

Global mirrors aren't affected by fog - Not that big of an issue.

Staff of extension causes world lag when used too often. A better way to set blocks?

Equipping an item should remove the selected feature from the opposite toolbar

Equipping an item should not replace the currently selected item in the normal toolbar.

Placing a slab underneath an upper slab in a mirror will remove the upper slab on the mirrored side.

SOLVED (Now no staffs can break blocks). Mirrors can be broken while breaking blocks: This cannot be solved with the current system. This will require an overhaul of how mirrors are broken, perhaps two clicks after a brief period are required.


STATIC VARIABLES ARE NOT GOOD: THEY DON'T WORK BETWEEN WORLDS.
 */



/*
Staff of Selection:

There are 4 modes of selection:
1. Single block (0D)
2. A line (1D)
3. An area (2D)
4. A volume (3D)

The line selection is very similar to the area selection.

Line and area selection methods use 2 points
Area and volume selection methods use 3 points.
 */