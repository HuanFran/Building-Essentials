package com.huanfran.buildingessentials.utils.internal

import net.minecraft.client.Minecraft
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.server.ServerWorld



/*
This file contains some convenience functions for the servers, whether they be integrated or dedicated.
 */



fun integratedServer() = Minecraft.getInstance().integratedServer ?: 
throw Exception("No integrated server found.")



fun serverWorld(dimensionType: DimensionType): ServerWorld = integratedServer().getWorld(dimensionType)