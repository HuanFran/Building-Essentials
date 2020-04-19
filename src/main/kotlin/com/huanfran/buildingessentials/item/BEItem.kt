package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.main.ITEM_GROUP
import net.minecraft.item.Item

abstract class BEItem(val name: String) : Item(Properties().group(ITEM_GROUP))