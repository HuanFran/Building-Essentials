package com.huanfran.buildingessentials.tile

import com.huanfran.buildingessentials.block.MirrorBlock
import com.huanfran.buildingessentials.tile.mirror.MirrorTileEntity
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

/**
 * Contains all [TileEntityType]s that are used in the Building Essentials mod.
 */
object BETileEntityTypes {


    /**
     * A utility function that creates a tile entity type given a [builder] and a list of [blocks] that the tile entity
     * can be placed on.
     */
    private fun<T : TileEntity> createTileEntityType(builder: () -> T, vararg blocks: Block): TileEntityType<T> =
            TileEntityType.Builder.create({ builder() } , blocks).build(null)



    /*
    Instances
     */



    val MIRROR = createTileEntityType({ MirrorTileEntity() }, MirrorBlock)


}