package com.huanfran.buildingessentials.networking

import com.huanfran.mirror.Mirrors
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.PacketDistributor
import java.util.function.Supplier

class InformationDemandPacket(val code: Int) {


    companion object {


        val encoder: (InformationDemandPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeInt(p.code)
        }



        val decoder: (PacketBuffer) -> InformationDemandPacket = { b -> InformationDemandPacket(
                b.readInt())
        }



        val handler: (InformationDemandPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            c.get().enqueueWork {
                //Server-side only.
                if(BEPacketHandler.isRemote(c.get())) return@enqueueWork

                val sender = c.get().sender ?: return@enqueueWork

                when(p.code) {
                    1 -> BEPacketHandler.HANDLER.send(PacketDistributor.PLAYER.with { sender }, MirrorsPacket(Mirrors.serverHandlerList()))
                }
            }

            c.get().packetHandled = true
        }


    }


}