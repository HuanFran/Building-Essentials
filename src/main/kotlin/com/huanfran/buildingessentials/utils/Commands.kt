package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.utils.internal.integratedServer
import com.huanfran.buildingessentials.utils.internal.player
import net.minecraft.command.CommandSource

/**
 * Contains some convenience functions for running commands.
 */
object Commands {


    fun writeToChat(message: String) = player().sendChatMessage(message)



    fun executeCommand(source: CommandSource, command: String) =
            integratedServer().commandManager.handleCommand(source, command)



    fun executeCommand(command: String) = executeCommand(integratedServer().commandSource, command)


}