package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.main.MODID
import com.huanfran.mirror.MirrorController
import com.huanfran.mirror.MirrorHandler
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.function.Supplier

/**
 * Handles sending information between the logical client to the logical server. Minecraft's system is confusing to
 * work with and I don't like it.
 */
object BEPacketHandler {


    val PROTOCOL_VERSION = "1"

    private var id = 0



    val HANDLER: SimpleChannel = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation(MODID, "main"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion { PROTOCOL_VERSION }.simpleChannel()



    fun register() {
        registerMessage(ClearSurfaceBlocksPacket::class.java, ClearSurfaceBlocksPacket.encoder, ClearSurfaceBlocksPacket.decoder, ClearSurfaceBlocksPacket.handler)
        registerMessage(UndoRedoPacket::class.java, UndoRedoPacket.encoder, UndoRedoPacket.decoder, UndoRedoPacket.handler)
        registerMessage(MirrorCreationPacket::class.java, MirrorCreationPacket.encoder, MirrorCreationPacket.decoder, MirrorCreationPacket.handler)
        registerMessage(MirrorRemovalPacket::class.java, MirrorRemovalPacket.encoder, MirrorRemovalPacket.decoder, MirrorRemovalPacket.handler)
        registerMessage(InformationDemandPacket::class.java, InformationDemandPacket.encoder, InformationDemandPacket.decoder, InformationDemandPacket.handler)
        registerMessage(MirrorsPacket::class.java, MirrorsPacket.encoder, MirrorsPacket.decoder, MirrorsPacket.handler)
    }



    fun<T> registerMessage(type: Class<T>,
                           encoder: (T, PacketBuffer) -> Unit,
                           decoder: (PacketBuffer) -> T,
                           consumer: (T, Supplier<NetworkEvent.Context>) -> Unit) {
        HANDLER.messageBuilder(type, id++).encoder(encoder).decoder(decoder).consumer(consumer).add()
    }



    /*
    Custom Read methods
     */
    
    
    
    fun readMirror(b: PacketBuffer) = MirrorController(
            Vector3(b.readDouble(), b.readDouble(), b.readDouble()), 
            Vector3(b.readDouble(), b.readDouble(), b.readDouble()),
            b.readDouble(),
            b.readDouble(),
            b.readDouble())
    
    
    
    fun readMirrors(b: PacketBuffer, number: Int) =  ArrayList<MirrorController>().apply {
        repeat(number) { add(readMirror(b)) }
    }



    fun readMirrorHandler(b: PacketBuffer) =
            MirrorHandler(b.readString(), readMirrors(b, b.readInt()))



    fun readMirrorHandlers(b: PacketBuffer, number: Int) = ArrayList<MirrorHandler>().apply {
        repeat(number) { add(readMirrorHandler(b)) }
    }



    /*
    Custom write methods
     */
    
    
    
    fun writeMirror(b: PacketBuffer, c: MirrorController) {
        b.writeDouble(c.v0.x)
        b.writeDouble(c.v0.y)
        b.writeDouble(c.v0.z)

        b.writeDouble(c.v1.x)
        b.writeDouble(c.v1.y)
        b.writeDouble(c.v1.z)

        b.writeDouble(c.width2)
        b.writeDouble(c.length2)
        b.writeDouble(c.height2)
    }



    fun writeMirrorHandler(b: PacketBuffer, h: MirrorHandler) = b.let {
        it.writeString(h.dimension)
        it.writeInt(h.controllers.size)
        for(c in h.controllers) writeMirror(b, c)
    }



    /*
    Misc. methods
     */



    fun isRemote(context: NetworkEvent.Context) = context.direction == NetworkDirection.PLAY_TO_CLIENT



}