package com.huanfran.buildingessentials.utils.internal

import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack


/*
This file contains convenience functions related to the client-side player. The functions in this class should only
be called on the client-side.
 */



/**
 * Gets the player instance. Only when in a world. Throws an exception otherwise.
 */
fun player() = Minecraft.getInstance().player ?: throw Exception("Player not initialised.")

/**
 * Gets the item that the [player] is holding in their main hand.
 */
fun heldItem(): ItemStack = player().heldItemMainhand

/**
 * Gets the [player]'s eye position with a default value for [partialTicks].
 */
fun playerEyePos(partialTicks: Float = 0F) = player().getEyePosition(partialTicks)