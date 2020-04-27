package com.huanfran.buildingessentials.utils

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockRayTraceResult



/**
 * Gets the player instance. Only when in a world. Throws an exception otherwise.
 */
fun player() = Minecraft.getInstance().player ?: throw Exception("Player not initialised.")

/**
 * Produces a [BlockRayTraceResult] for this [Entity] with some default values.
 */
fun Entity.rayTrace(range: Double, partialTicks: Float = 0F, interactsWithFluids: Boolean = false) =
        pick(range, partialTicks, interactsWithFluids) as BlockRayTraceResult

/**
 * Gets the item that the [player] is holding in their active hand.
 */
fun heldItem() = player().getHeldItem(player().activeHand)