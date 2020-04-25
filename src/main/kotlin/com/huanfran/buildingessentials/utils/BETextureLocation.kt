package com.huanfran.buildingessentials.utils

import net.minecraft.util.ResourceLocation

/**
 * A version of [ResourceLocation] that also contains in the [width] and [height] of a texture.
 */
class BETextureLocation(path: String, val width: Int, val height: Int) : ResourceLocation(path)