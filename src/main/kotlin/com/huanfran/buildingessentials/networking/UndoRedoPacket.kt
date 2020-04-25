package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.undo.Undo
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

/**
 * Tells the server to call functions from the [Undo] object.
 */
class UndoRedoPacket(val undo: Boolean) {


    companion object {


        val encoder: (UndoRedoPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeBoolean(p.undo)
        }



        val decoder: (PacketBuffer) -> UndoRedoPacket = { b -> UndoRedoPacket(
                b.readBoolean())
        }



        val handler: (UndoRedoPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            c.get().enqueueWork {
                if(p.undo)
                    Undo.undo()
                else
                    Undo.redo()
            }

            c.get().packetHandled = true
        }


    }


}