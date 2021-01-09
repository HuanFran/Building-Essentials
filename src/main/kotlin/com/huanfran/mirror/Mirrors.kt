package com.huanfran.mirror

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.BE_LOGGER
import com.huanfran.buildingessentials.undo.BEActionBuffer
import com.huanfran.buildingessentials.utils.Side
import com.huanfran.buildingessentials.utils.internal.initWorldBEDirectories
import com.huanfran.buildingessentials.utils.internal.mirrorsFileExtension
import com.huanfran.buildingessentials.utils.internal.saveMirrorsPath
import com.huanfran.buildingessentials.utils.internal.worldMirrorsPath
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import org.apache.logging.log4j.Level
import java.io.*

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
     * Maps dimensions (by their name) with a [MirrorHandler] on the client-side.
     */
    private val clientHandlers = HashMap<String, MirrorHandler>()

    /**
     * Maps dimensions (by their name) with a [MirrorHandler] on the server-side.
     */
    private val serverHandlers = HashMap<String, MirrorHandler>()



    /**
     * Gets the handler associated with the given [world] (according to its dimension and logical side). A [side] can
     * be optionally specified.
     */
    fun getHandler(world: IWorld, side: Side = Side.BOTH) : MirrorHandler? = world.dimension.type.registryName?.let {
        if(world.isRemote && side.client)
            getHandlerClientSide(it.path)
        else if(!world.isRemote && side.server)
            getHandlerServerSide(it.path)
        else
            null //Only returns null if a method is called from the wrong side.
    }



    fun getHandlerClientSide(dimension: String) =
            clientHandlers[dimension] ?: MirrorHandler(dimension).apply { clientHandlers[dimension] = this }



    fun getHandlerServerSide(dimension: String) =
            serverHandlers[dimension] ?: MirrorHandler(dimension).apply { serverHandlers[dimension] = this }



    fun setClientHandlers(handlers: ArrayList<MirrorHandler>) {
        clientHandlers.clear()
        handlers.forEach { clientHandlers[it.dimension] = it }
    }



    fun setServerHandlers(handlers: ArrayList<MirrorHandler>) {
        serverHandlers.clear()
        handlers.forEach { serverHandlers[it.dimension] = it }
    }



    /**
     * Handles the placement of a mirror node at position [v]. Client-side only.
     */
    fun handleMirrorNodeCreation(world: IWorld, v: Vector3) =
            getHandler(world, Side.CLIENT_ONLY)?.handleMirrorNodeCreation(v)



    fun handleMirrorCreation(world: IWorld, controller: MirrorController) =
            getHandler(world)?.controllers?.add(controller)



    /**
     * Called when a block is placed in the [world]. Handles the mirroring of block placement by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkPlaceBlock(world: IWorld, player: PlayerEntity, pos: BlockPos, state: BlockState) =
            getHandler(world)?.checkPlaceBlock(world, player, pos, state)



    /**
     * Version of [checkPlaceBlock] that takes [BEActionBuffer] in place of [IWorld]. This allows for undo-redo
     * functionality.
     */
    fun checkPlaceBlock(buffer: BEActionBuffer, player: PlayerEntity, pos: BlockPos, state: BlockState) =
            getHandler(buffer.world)?.checkPlaceBlock(buffer, player, pos, state)



    /**
     * Called when a block is broken in the [world]. Handles the mirroring of block breaking by using either the
     * [clientMirrorHandler] or the [serverMirrorHandler] according to the current logical side.
     */
    fun checkBreakBlock(world: IWorld, pos: BlockPos) = getHandler(world)?.checkBreakBlock(world, pos)



    /**
     * Version of [checkBreakBlock] that takes [BEActionBuffer] in place of [IWorld]. This is used for operations that
     * can be undone and redone.
     */
    fun checkBreakBlock(buffer: BEActionBuffer, pos: BlockPos) = getHandler(buffer.world)?.checkBreakBlock(buffer, pos)




    fun lookingAt(world: IWorld, player: PlayerEntity, playerPos: Vector3) = getHandler(world)?.lookingAt(player, playerPos)



    fun handleMirrorRemoval(world: IWorld, v0: Vector3, v1: Vector3) = getHandler(world)?.handleMirrorRemoval(v0, v1)



    /**
     * Gets the number of controllers that are active.
     */
    fun activeMirrorCount() = clientHandlers.size



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



    /*
    Saving and loading - Server-side only.
     */



    fun serverHandlerList() = ArrayList<MirrorHandler>().apply {
        serverHandlers.forEach { add(it.value) }
    }



    /**
     * Server-side only
     */
    fun saveMirrors(world: IWorld) {
        val handler = getHandler(world) ?: return

        initWorldBEDirectories()

        BufferedWriter(FileWriter(worldMirrorsPath(world))).use { writer ->
            handler.controllers.forEach {
                with(it) {
                    writer.write("${v0.x}:${v0.y}:${v0.z}:${v1.x}:${v1.y}:${v1.z}:$width2:$length2:$height2\n")
                }
            }
        }
    }



    /**
     * Server-side only
     */
    fun loadMirrors() {
        serverHandlers.clear()

        initWorldBEDirectories()

        val files = File(saveMirrorsPath).listFiles() ?: return

        for(file in files) {
            if(file.extension != mirrorsFileExtension) continue

            val controllers = ArrayList<MirrorController>()

            BufferedReader(FileReader(file)).use { reader ->
                while(reader.ready()) {
                    val parts = reader.readLine().split(":")

                    if(parts.size != 9) return@use

                    try {
                        val v0 = Vector3(parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble())
                        val v1 = Vector3(parts[3].toDouble(), parts[4].toDouble(), parts[5].toDouble())

                        val width2 = parts[6].toDouble()
                        val length2 = parts[7].toDouble()
                        val height2 = parts[8].toDouble()

                        controllers.add(MirrorController(v0, v1, width2, length2, height2))
                    } catch(e: NumberFormatException) {
                        BE_LOGGER.log(Level.ERROR, "Mirror save file corrupted at: ${file.path}")
                    }

                }
            }

            serverHandlers[file.nameWithoutExtension] = MirrorHandler(file.nameWithoutExtension, controllers)
        }
    }


}