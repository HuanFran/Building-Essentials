package com.huanfran.buildingessentials.item

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class BEStaff(name: String) : BEItem(name, Properties().maxStackSize(1)) {


    override fun canPlayerBreakBlockWhileHolding(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity)
            = false


}