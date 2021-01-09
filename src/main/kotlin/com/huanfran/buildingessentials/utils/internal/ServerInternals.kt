package com.huanfran.buildingessentials.utils.internal

import com.huanfran.buildingessentials.main.MODID
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.world.IWorld
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.server.ServerWorld
import java.io.File



/*
This file contains some convenience functions for the servers, whether they be integrated or dedicated.
 */



/**
 * Gets the integrated server or throws an exception if it doesn't exist. Only to be used on the logical server side
 * in a single-player game.
 */
fun integratedServer() = Minecraft.getInstance().integratedServer ?: 
throw Exception("No integrated server found.")

/**
 * Gets the [ServerWorld] of the [integratedServer].
 */
fun integratedServerWorld(dimensionType: DimensionType): ServerWorld = integratedServer().getWorld(dimensionType)

/**
 * The server, integrated or dedicated, that this program is running on.
 */
lateinit var server: MinecraftServer

/**
 * The name of the currently loaded save.
 */
var saveName: String = "NULL"
    set(value) { field = value; savePath = "saves/$value/"}

/**
 * The path to the directory of the current save.
 */
var savePath: String = "NULL"
    private set(value) { field = value; saveBEDataPath = "$value/$MODID/"}

/**
 * The path to the directory of data associated with Building Essentials for the current save.
 */
var saveBEDataPath: String = "NULL"
    private set(value) { field = value; saveMirrorsPath = "$value/mirrors"}

/**
 * The path to the directory that holds data about the current save's mirrors.
 */
var saveMirrorsPath: String = "NULL"
    private set

/**
 * The file extension for files that contain mirror data.
 */
const val mirrorsFileExtension = "mir"

/**
 * The path to the text file that holds data about the mirrors of the given [world] in the current save.
 */
fun worldMirrorsPath(world: IWorld) = "$saveMirrorsPath/${world.dimension.type.registryName?.path}.$mirrorsFileExtension"

/**
 * Creates the [saveBEDataPath] and the [saveMirrorsPath] files if they do not already exist.
 */
fun initWorldBEDirectories() {
    File(saveBEDataPath).mkdir()
    File(saveMirrorsPath).mkdir()
}
