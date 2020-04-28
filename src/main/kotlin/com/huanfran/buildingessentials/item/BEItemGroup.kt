package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.main.BuildingEssentials
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

/**
 * A [BuildingEssentials] ItemGroup. The [iconSupplier] is necessary to avoid initialisation issues as the items of this
 * mod are not constructed statically.
 */
class BEItemGroup(name: String, val iconSupplier: () -> ItemStack) : ItemGroup(name) {

    override fun createIcon() = iconSupplier()

}
