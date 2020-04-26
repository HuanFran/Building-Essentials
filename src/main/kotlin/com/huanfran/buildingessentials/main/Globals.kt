package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.graphics.maths.Vector3
import com.huanfran.buildingessentials.item.StaffOfExtension
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import net.minecraft.world.IWorld
import net.minecraft.world.dimension.DimensionType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.math.floor


/*
MAIN
 */



/**
 * The identifier for the [BuildingEssentials] mod.
 */
const val MODID = "building-essentials"

/**
 * The display nme for the [BuildingEssentials] mod.
 */
const val DISPLAY_NAME = "Building Essentials"

/**
 * Where all logging information from [BuildingEssentials] is sent.
 */
val BE_LOGGER: Logger = LogManager.getLogger(MODID)

/**
 * Logs the given string [message] using the [BE_LOGGER].
 */
fun log(message: String) = BE_LOGGER.debug(message)



/*
MINECRAFT INTERNALS - Client-side
 */



fun windowHandle() = Minecraft.getInstance().mainWindow.handle

fun currentScreen() = Minecraft.getInstance().currentScreen

fun minecraft() = Minecraft.getInstance()!!

fun player() = minecraft().player

fun integratedServer() = minecraft().integratedServer!!

fun world() = minecraft().world

fun inventory() = player()?.inventory

fun heldItem() = player()?.heldItemMainhand?.item!!

fun toolbarIndex() = inventory()?.currentItem

fun doClientSided(action: () -> Unit) = world()?.let { if(it.isRemote) action() }

fun doServerSided(action: () -> Unit) = world()?.let { if(!it.isRemote) action() }

fun rayTraceResult(range: Double, partialTicks: Float, interactsWithFluids: Boolean = false) =
        player()?.pick(range, partialTicks, interactsWithFluids) as BlockRayTraceResult

fun rayTraceResult(entity: Entity, range: Double, partialTicks: Float, interactsWithFluids: Boolean = false) =
        entity.pick(range, partialTicks, interactsWithFluids) as BlockRayTraceResult

fun isLogicalServer() = !(world()?.isRemote ?: true)

fun isLogicalClient() = world()?.isRemote ?: true



/*
MINECRAFT INTERNALS - Server-side
 */



fun serverWorld() = Minecraft.getInstance().integratedServer?.getWorld(DimensionType.OVERWORLD)



/*
ITEM GROUPS
 */



/**
 * The main [ItemGroup].
 */
var ITEM_GROUP = BEItemGroup(MODID) { ItemStack(StaffOfExtension) }



/**
 * A [BuildingEssentials] ItemGroup. The [iconSupplier] is necessary to avoid initialisation issues as the items of this
 * mod are not constructed statically.
 */
class BEItemGroup(name: String, val iconSupplier: () -> ItemStack) : ItemGroup(name) {

    override fun createIcon() = iconSupplier()

}



/*
BLOCK POSITIONS
 */



fun BlockPos.toVector3() = Vector3(x.toDouble(), y.toDouble(), z.toDouble())



fun Vector3.toBlockPos() = BlockPos(floor(x).toInt(), floor(y).toInt(), floor(z).toInt())



fun BlockPos.isNextTo(pos: BlockPos) =
        x in pos.x - 1..pos.x + 1 &&
        y in pos.y - 1..pos.y + 1 &&
        z in pos.z - 1..pos.z + 1



fun Vec3d.toVector3() = Vector3(x, y, z)



fun Vector3.toVec3d() = Vec3d(x, y, z)



fun playerEyePos(player: PlayerEntity) = with(player.getEyePosition(0F)) { Vec3i(floor(x), floor(y), floor(z)) }



fun isReplacable(world: IWorld, pos: BlockPos) = world.isAirBlock(pos) || world.getBlockState(pos).fluidState.isSource



/*
MISC
 */



fun pluralChecked(count: Int, singularText: String, pluralText: String): String =
        count.toString() + " " + if (count != 1) pluralText else singularText



/*
DIRECTIOn
 */



private val directionsByYawIndex = arrayListOf(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST)
fun directionFromYaw(yaw: Double) =
        directionsByYawIndex[floor((Math.floorMod(yaw.toInt() + 45, 360) / 90).toDouble()).toInt()]
