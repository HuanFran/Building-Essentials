package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.item.BEItemGroup
import com.huanfran.buildingessentials.item.StaffOfExtension
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.world.IWorld
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger



/*
This file contains information about the Building Essentials mod and holds some important variables associated with it.
 */



/**
 * The forge identifier for the [BuildingEssentials] mod.
 */
const val MODID = "building-essentials"

/**
 * The in-game display name for the [BuildingEssentials] mod.
 */
const val DISPLAY_NAME = "Building Essentials"

/**
 * Where all logging information from [BuildingEssentials] is sent.
 */
val BE_LOGGER: Logger = LogManager.getLogger(MODID)

/**
 * Logs the given string [message] using the [BE_LOGGER].
 */
fun log(message: String) = BE_LOGGER.debug(message)

/**
 * Logs the given object using the [BE_LOGGER].
 */
fun log(any: Any?) = log(any.toString())

/**
 * The main [ItemGroup] for Building Essentials.
 */
var ITEM_GROUP = BEItemGroup(MODID) { ItemStack(StaffOfExtension) }

/**
 * Gets the path of the world data directory saved by Building Essentials for the given [world].
 */
fun dataPath(world: IWorld) = "saves/${world.worldInfo.worldName}/Building Essentials/"

/**
 * Gets the path of the mirrors data directory saved by Building Essentials for the given [world].
 */
fun mirrorsDirectoryPath(world: IWorld) = dataPath(world) + "mirrors/"

/**
 * Gets the path of the saved mirrors of the given [world] according to its dimension.
 */
fun mirrorsPath(world: IWorld) = mirrorsDirectoryPath(world) + "${world.dimension.type.registryName}.txt"