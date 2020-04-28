package com.huanfran.mirror

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

/**
 * Contains methods and variables associated with mirroring.
 */
object Mirrors {


    /*
    Mirroring
     */



    /*println("I am testing this keyboard. The keys are too high. That's my complaint. I liked my old keyboard" +
            "better but they don't sell that one anymore. It was getting worn down. The keys wouldn't press" +
            "properly. At least I can still type quite well with this keyboard, even if it is a bit less " +
            "comfortable. In fact, it may force me to improve my typing posture. I now have to 'hover' my hands" +
            "above the keyboard as there is no hand rest. I think that this is better typing posture. I have" +
            "no idea. I can type well regardless of the keyboard. This keyboard is very smooth. The keys are very" +
            "soft and don't make much noise. I like this keyboard, even if the keys are quite tall and stick out.")*/



    /**
     * Handles all [MirrorController]s that have been created on the logical client side.
     */
    val clientMirrorHandler = MirrorHandler()

    /**
     * Handles all [MirrorController]s that have been created on the logical server side.
     */
    val serverMirrorHandler = MirrorHandler()

    /**
     * Maps dimensions (by their name) with a [MirrorHandler] on the client-side.
     */
    val clientHandlers = HashMap<String, MirrorHandler>()

    /**
     * Maps dimensions (by their name) with a [MirrorHandler] on the server-side.
     */
    val serverHandlers = HashMap<String, MirrorHandler>()



    fun getHandler(world: IWorld) : MirrorHandler? = world.dimension.type.registryName?.let {
        if(world.isRemote)
            clientHandlers[it.namespace]
        else
            serverHandlers[it.namespace]
    }

    fun checkPlaceBlock2(world: IWorld, player: PlayerEntity, pos: BlockPos, state: BlockState) =
            getHandler(world)?.checkPlaceBlock(world, player, pos, state)


    /**
     * Handles the placement of a mirror node at position [v]. Client-side only.
     */
    fun handleMirrorNodeCreation(v: Vector3, isRemote: Boolean) = if(isRemote)
        clientMirrorHandler.handleMirrorNodeCreation(v)
    else
        Unit



    fun handleMirrorCreation(controller: MirrorController, isRemote: Boolean) = if(isRemote)
        clientMirrorHandler.controllers.add(controller)
    else
        serverMirrorHandler.controllers.add(controller)



    /**
     * Called when a block is placed in the [world]. Handles the mirroring of block placement by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkPlaceBlock(world: IWorld, player: PlayerEntity, pos: BlockPos, state: BlockState) = if(world.isRemote)
        clientMirrorHandler.checkPlaceBlock(world, player, pos, state)
    else
        serverMirrorHandler.checkPlaceBlock(world, player, pos, state)



    /**
     * Version of [checkPlaceBlock] that takes [BEActionBuffer] in place of [IWorld]. This allows for undo-redo
     * functionality.
     */
    fun checkPlaceBlock(buffer: BEActionBuffer, player: PlayerEntity, pos: BlockPos, state: BlockState) = if(buffer.world.isRemote)
        clientMirrorHandler.checkPlaceBlock(buffer, player, pos, state)
    else
        serverMirrorHandler.checkPlaceBlock(buffer, player, pos, state)



    /**
     * Called when a block is broken in the [world]. Handles the mirroring of block breaking by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkBreakBlock(world: IWorld, pos: BlockPos) = if(world.isRemote)
        clientMirrorHandler.checkBreakBlock(world, pos)
    else
        serverMirrorHandler.checkBreakBlock(world, pos)



    /**
     * Version of [checkBreakBlock] that takes [BEActionBuffer] in place of [IWorld]. This is used for operations that
     * can be undone and redone.
     */
    fun checkBreakBlock(buffer: BEActionBuffer, pos: BlockPos) = if(buffer.world.isRemote)
        clientMirrorHandler.checkBreakBlock(buffer, pos)
    else
        serverMirrorHandler.checkBreakBlock(buffer, pos)



    /**
     * Removes a [mirrorController] from the controllers list in either the [clientMirrorHandler] or the
     * [serverMirrorHandler] depending on the logical side specified by [isRemote].
     */
    fun handleMirrorRemoval(mirrorController: MirrorController, isRemote: Boolean) = if(isRemote)
        clientMirrorHandler.controllers.remove(mirrorController)
    else
        serverMirrorHandler.controllers.remove(mirrorController)



    /**
     * Gets the number of controllers that are active. Gets this from the [clientMirrorHandler] as both this and the
     * [serverMirrorHandler] should have the same number of controllers.
     */
    fun activeMirrorCount() = clientMirrorHandler.controllers.size



    fun lookingAt(player: PlayerEntity, playerPos: Vector3, isRemote: Boolean) = if(isRemote)
        clientMirrorHandler.lookingAt(player, playerPos)
    else
        serverMirrorHandler.lookingAt(player, playerPos)



    fun handleMirrorRemoval(v0: Vector3, v1: Vector3, isRemote: Boolean) = if(isRemote)
        clientMirrorHandler.handleMirrorRemoval(v0, v1)
    else
        serverMirrorHandler.handleMirrorRemoval(v0,v1)




    /*
    Configuration
     */



    /**
     * Half the default with for mirrors. Default: 40.
     */
    var defaultWidth2 = 40.0

    /**
     * Half the default length for mirrors. Default: 40.
     */
    var defaultLength2 = 40.0

    /**
     * Half the default height for mirrors. Default: 20.
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

    /**
     * Whether mirroring block placement can replace existing blocks or not.
     */
    var mirrorReplacingEnabled = false


}