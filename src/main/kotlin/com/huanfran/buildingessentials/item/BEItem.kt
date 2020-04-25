package com.huanfran.buildingessentials.item

import com.huanfran.buildingessentials.main.ITEM_GROUP
import net.minecraft.item.Item

abstract class BEItem(val name: String, properties: Properties) : Item(properties.group(ITEM_GROUP))