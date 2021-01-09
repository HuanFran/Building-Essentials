package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.mirror.Mirrors
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class MirrorRemovalPacket(val v0: Vector3, val v1: Vector3) {


    companion object {


        val encoder: (MirrorRemovalPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeDouble(p.v0.x)
            b.writeDouble(p.v0.y)
            b.writeDouble(p.v0.z)

            b.writeDouble(p.v1.x)
            b.writeDouble(p.v1.y)
            b.writeDouble(p.v1.z)
        }



        val decoder: (PacketBuffer) -> MirrorRemovalPacket = { b -> MirrorRemovalPacket(
                Vector3(b.readDouble(), b.readDouble(), b.readDouble()),
                Vector3(b.readDouble(), b.readDouble(), b.readDouble()))
        }



        val handler: (MirrorRemovalPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            c.get().enqueueWork {
                val isRemote = c.get().direction == NetworkDirection.PLAY_TO_CLIENT

                //Handle either on the client-side of the server-side.
                if(isRemote)
                    Mirrors.handleMirrorRemoval(Minecraft.getInstance().player!!.world, p.v0, p.v1)
                else
                    Mirrors.handleMirrorRemoval(c.get().sender!!.world, p.v0, p.v1)

                //Tell all clients that a mirror has been removed.
                if(!isRemote) BEPacketHandler.HANDLER.sendTo(p, c.get().networkManager, NetworkDirection.PLAY_TO_CLIENT)
            }

            c.get().packetHandled = true
        }


    }


}