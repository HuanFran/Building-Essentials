package com.huanfran.buildingessentials.networking

import com.huanfran.mirror.MirrorHandler
import com.huanfran.mirror.Mirrors
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

/**
 * Only from server -> client.
 */
class MirrorsPacket(val handlers: ArrayList<MirrorHandler>) {


    companion object {


        val encoder: (MirrorsPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeInt(p.handlers.size)
            for(h in p.handlers) BEPacketHandler.writeMirrorHandler(b, h)
        }



        val decoder: (PacketBuffer) -> MirrorsPacket = { b -> MirrorsPacket(
                BEPacketHandler.readMirrorHandlers(b, b.readInt()))
        }



        val handler: (MirrorsPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            c.get().enqueueWork {

                if(BEPacketHandler.isRemote(c.get())) Mirrors.setClientHandlers(p.handlers)

            }

            c.get().packetHandled = true
        }


    }


}