package com.huanfran.buildingessentials.tile.mirror

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World

/**
 * Contains methods and variables associated with mirroring.
 */
object Mirrors {


    /*
    Mirroring
     */



    /**
     * Handles all [MirrorController]s that have been created on the logical client side.
     */
    val clientMirrorHandler = MirrorHandler()

    /**
     * Handles all [MirrorController]s that have been created on the logical server side.
     */
    val serverMirrorHandler = MirrorHandler()



    /**
     * Called when a [MirrorTileEntity] is created and its position set. Handles the creation of [MirrorController]s
     * by using either the [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun handleMirrorCreation(world: World, pos: BlockPos) = if(world.isRemote)
        clientMirrorHandler.handleMirrorNodeCreation(pos)
    else
        serverMirrorHandler.handleMirrorNodeCreation(pos)



    /**
     * Called when a block is placed in the [world]. Handles the mirroring of block placement by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkPlaceBlock(world: IWorld, pos: BlockPos, state: BlockState) = if(world.isRemote)
        clientMirrorHandler.checkPlaceBlock(world, pos, state)
    else
        serverMirrorHandler.checkPlaceBlock(world, pos, state)



    /**
     * Called when a block is broken in the [world]. Handles the mirroring of block breaking by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkBreakBlock(world: IWorld, pos: BlockPos) = if(world.isRemote)
        clientMirrorHandler.checkBreakBlock(world, pos)
    else
        serverMirrorHandler.checkBreakBlock(world, pos)



    /**
     * Called when a [MirrorTileEntity] is removed from the [world]. Handles the removal of any related mirror nodes
     * and the removal of any associated [MirrorController]s from either the [clientMirrorHandler] or the
     * [serverMirrorHandler] according to the current logical side.
     */
    fun handleMirrorRemoval(world: IWorld, pos: BlockPos) = if(world.isRemote)
        clientMirrorHandler.handleMirrorRemoval(world, pos)
    else
        serverMirrorHandler.handleMirrorRemoval(world, pos)



    /**
     * Gets the number of controllers that are active. Gets this from the [clientMirrorHandler] as both this and the
     * [serverMirrorHandler] should have the same number of controllers.
     */
    fun activeMirrorCount() = clientMirrorHandler.controllers.size



    /*
    Configuration
     */



    /**
     * Half the default with for mirrors.
     */
    var defaultWidth2 = 20.0

    /**
     * Half the default length for mirrors.
     */
    var defaultLength2 = 40.0

    /**
     * Half the default height for mirrors.
     */
    var defaultHeight2 = 20.0

    /**
     * Whether the action of mirrors is enabled or not.
     */
    var mirroringEnabled = true

    /**
     * Whether the rendering of mirrors is disabled or not.
     */
    var mirrorRenderingEnabled = true


}