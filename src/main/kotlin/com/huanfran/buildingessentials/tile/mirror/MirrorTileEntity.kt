package com.huanfran.buildingessentials.tile.mirror

import com.huanfran.buildingessentials.block.MirrorBlock
import com.huanfran.buildingessentials.tile.BETileEntityTypes
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.extensions.IForgeTileEntity

/**
 * These tile entities contain [MirrorController]s and are present in [MirrorBlock]s. These are present in pairs in
 * [MirrorController]s, however only one entity in that pair will contain the [controller]. This is to prevent
 * the mirror's rendering and action from occuring multiple times.
 */
class MirrorTileEntity : TileEntity(BETileEntityTypes.MIRROR) {


    /**
     * Denotes a pair of mirror entities. Specifically, the one to which this one belongs.
     */
    var controller: MirrorController? = null



    /**
     * The mirror's position and world is not set immediately upon construction, so initialisation must not be done in
     * an init block, but rather in this overridden function. This calls [Mirrors.handleMirrorCreation] for the given
     * [pos].
     */
    override fun setWorldAndPos(world: World, pos: BlockPos) {
        super.setWorldAndPos(world, pos)

       // controller = Mirrors.handleMirrorCreation(world, pos)
    }



    /**
     * Called when the [MirrorBlock] that contains this tile entity is removed. This calls [Mirrors.handleMirrorRemoval]
     * for said block's position.
     */
    override fun remove() {
        super.remove()

        //world?.let { Mirrors.handleMirrorRemoval(it, this.pos) }
    }



    /**
     * This lets the [MirrorRenderer] render this entity's mirror (if it contains a [controller]) even when the player
     * is not looking directly at the [MirrorBlock] in which this entity is housed.
     */
    override fun getRenderBoundingBox(): AxisAlignedBB = IForgeTileEntity.INFINITE_EXTENT_AABB



    override fun toString() = "MirrorTileEntity: $pos"


}