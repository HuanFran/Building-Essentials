package com.huanfran.buildingessentials.utils.extensions

import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockRayTraceResult


/**
 * Produces a [BlockRayTraceResult] for this [Entity] with some default values.
 */
fun Entity.rayTrace(range: Double, partialTicks: Float = 0F, interactsWithFluids: Boolean = false) =
        pick(range, partialTicks, interactsWithFluids) as BlockRayTraceResult