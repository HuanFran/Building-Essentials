package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.tile.BETileEntityTypes
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.extensions.IForgeTileEntity

class MirrorTileEntity : TileEntity(BETileEntityTypes.MIRROR) {


    /**
     * Denotes a pair of mirror entities. Specifically, the one to which this one belongs.
     */
    var controller: MirrorController? = null



    override fun setWorldAndPos(world: World, pos: BlockPos) {
        super.setWorldAndPos(world, pos)

        controller = Mirrors.handleMirrorCreation(world, pos)
    }



    override fun remove() {
        super.remove()

        world?.let { Mirrors.handleMirrorRemoval(it, this.pos) }
    }



    override fun getRenderBoundingBox(): AxisAlignedBB = IForgeTileEntity.INFINITE_EXTENT_AABB



    override fun toString() = "MirrorTileEntity: $pos"


}