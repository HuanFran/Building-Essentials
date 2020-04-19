package com.huanfran.buildingessentials.block.blockitem

import com.huanfran.buildingessentials.main.ITEM_GROUP
import net.minecraft.block.Block
import net.minecraft.item.BlockItem

abstract class BEBlockItem(block: Block) : BlockItem(block, Properties().group(ITEM_GROUP))