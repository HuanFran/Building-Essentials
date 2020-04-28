package com.huanfran.buildingessentials.networking

import com.huanfran.buildingessentials.utils.Clearing
import com.huanfran.buildingessentials.utils.internal.serverWorld
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.Vec3i
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

/**
 * Tells the server to call functions from the [Clearing] class at the player's location.
 */
class ClearSurfaceBlocksPacket(val clearType: Int, val playerEyePos: Vec3i, val radius: Int, val depth: Int) {


    companion object {


        val encoder: (ClearSurfaceBlocksPacket, PacketBuffer) -> Unit = { p, b ->
            b.writeInt(p.clearType)
            b.writeInt(p.playerEyePos.x)
            b.writeInt(p.playerEyePos.y)
            b.writeInt(p.playerEyePos.z)
            b.writeInt(p.radius)
            b.writeInt(p.depth)
        }



        val decoder: (PacketBuffer) -> ClearSurfaceBlocksPacket = { b -> ClearSurfaceBlocksPacket(
                b.readInt(),
                Vec3i(b.readInt(), b.readInt(), b.readInt()),
                b.readInt(),
                b.readInt())
        }



        val handler: (ClearSurfaceBlocksPacket, Supplier<NetworkEvent.Context>) -> Unit = { p, c ->
            val world = serverWorld(DimensionType.OVERWORLD)

            c.get().enqueueWork {
                when(p.clearType) {
                    1 -> Clearing.clearVegetation(world, p.playerEyePos, p.radius, p.depth)
                    2 -> Clearing.clearSnow(world, p.playerEyePos, p.radius, p.depth)
                }
            }

            c.get().packetHandled = true
        }


    }


}