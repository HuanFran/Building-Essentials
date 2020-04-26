package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.tile.mirror.MirrorController
import com.huanfran.buildingessentials.tile.mirror.Mirrors
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class MirrorCreationPacket(val v0: Vector3, val v1: Vector3, val width2: Double, val length2: Double, val height2: Double) {


    companion object {


        val encoder: (MirrorCreationPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeDouble(p.v0.x)
            b.writeDouble(p.v0.y)
            b.writeDouble(p.v0.z)

            b.writeDouble(p.v1.x)
            b.writeDouble(p.v1.y)
            b.writeDouble(p.v1.z)

            b.writeDouble(p.width2)
            b.writeDouble(p.length2)
            b.writeDouble(p.height2)
        }



        val decoder: (PacketBuffer) -> MirrorCreationPacket = { b -> MirrorCreationPacket(
                Vector3(b.readDouble(), b.readDouble(), b.readDouble()),
                Vector3(b.readDouble(), b.readDouble(), b.readDouble()),

                b.readDouble(),
                b.readDouble(),
                b.readDouble())
        }



        val handler: (MirrorCreationPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            c.get().enqueueWork {
                val controller = MirrorController(p.v0, p.v1, p.width2, p.length2, p.height2)

                val isRemote = c.get().direction == NetworkDirection.PLAY_TO_CLIENT
                Mirrors.handleMirrorCreation(controller, isRemote)

                if(!isRemote) {
                    BEPacketHandler.HANDLER.sendTo(p, c.get().networkManager, NetworkDirection.PLAY_TO_CLIENT)
                }
            }

            c.get().packetHandled = true
        }


    }


}