package com.huanfran.buildingessentials.utils.internal

import net.minecraft.client.Minecraft


/*
This file contains some convenience methods for use on the client-side.
 */



fun windowHandle() = Minecraft.getInstance().mainWindow.handle

fun currentScreen() = Minecraft.getInstance().currentScreen