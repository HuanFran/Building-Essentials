package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.main.MODID
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.function.Supplier

object BEPacketHandler {


    val PROTOCOL_VERSION = "1"

    private var id = 0



   /*val HANDLER: SimpleChannel = NetworkRegistry.newSimpleChannel(
            ResourceLocation(MODID, "main"),
            { PROTOCOL_VERSION },
            { it == PROTOCOL_VERSION },
            { it == PROTOCOL_VERSION })*/

    val HANDLER: SimpleChannel = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation(MODID, "main"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion { PROTOCOL_VERSION }.simpleChannel()



    fun register() {
        registerMessage(ClearSurfaceBlocksPacket::class.java, ClearSurfaceBlocksPacket.encoder, ClearSurfaceBlocksPacket.decoder, ClearSurfaceBlocksPacket.handler)
        registerMessage(UndoRedoPacket::class.java, UndoRedoPacket.encoder, UndoRedoPacket.decoder, UndoRedoPacket.handler)
    }



    fun<T> registerMessage(type: Class<T>,
                           encoder: (T, PacketBuffer) -> Unit,
                           decoder: (PacketBuffer) -> T,
                           consumer: (T, Supplier<NetworkEvent.Context>) -> Unit) {
        HANDLER.messageBuilder(type, id++).encoder(encoder).decoder(decoder).consumer(consumer).add()
    }


}