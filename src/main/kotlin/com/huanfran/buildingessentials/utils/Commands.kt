package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.main.integratedServer
import com.huanfran.buildingessentials.main.player
import net.minecraft.command.CommandSource

object Commands {


    fun writeToChat(message: String) = player()!!.sendChatMessage(message)



    fun executeCommand(source: CommandSource, command: String) =
            integratedServer().commandManager.handleCommand(source, command)



    fun executeCommand(command: String) = executeCommand(integratedServer().commandSource, command)



    fun executePlayerCommand(command: String) = executeCommand(player()!!.commandSource, command)


}