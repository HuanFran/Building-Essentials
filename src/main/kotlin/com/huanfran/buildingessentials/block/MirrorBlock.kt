package com.huanfran.buildingessentials.block

import com.huanfran.buildingessentials.tile.BETileEntityTypes
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.world.IBlockReader

object MirrorBlock : BEBlock("mirror_node", Properties.create(Material.ROCK)) {


    override fun hasTileEntity(state: BlockState) = true

    override fun createTileEntity(state: BlockState, world: IBlockReader) = BETileEntityTypes.MIRROR.create()


}