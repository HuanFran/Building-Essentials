package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.item.BEItemGroup
import com.huanfran.buildingessentials.item.StaffOfExtension
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
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
 * The name of the mirror save data location.
 */
const val MIRROR_SAVE_DATA_NAME = "${MODID}_MirrorData"