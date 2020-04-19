package com.huanfran.buildingessentials.tile

import com.huanfran.buildingessentials.block.MirrorBlock
import com.huanfran.buildingessentials.tile.mirror.MirrorTileEntity
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

object BETileEntityTypes {


    private fun<T : TileEntity> createTileEntityType(builder: () -> T, vararg blocks: Block): TileEntityType<T> =
            TileEntityType.Builder.create({ builder() } , blocks).build(null)



    /*
    Instances
     */



    val MIRROR = createTileEntityType({ MirrorTileEntity() }, MirrorBlock)


}