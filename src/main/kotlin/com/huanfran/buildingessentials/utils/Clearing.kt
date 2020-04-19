package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.main.pluralChecked
import com.huanfran.buildingessentials.undo.BEActionBuffer
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.BushBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import net.minecraft.world.World

object Clearing {


    /**
     * Clears blocks below the eye-level of the player that satisfy the [remover] predicate. Blocks will not be moved if
     * there is a solid block (not an air block) above them that is also below the player's eye-level, which is given as
     * the parameter [p]. Think of this as an efficient alternative to the /fill command that only targets blocks on the
     * surface.
     */
    private fun clearSurfaceBlocks(world: World, p: Vec3i, radius: Int, depth: Int, checkBlockAbove: Boolean, remover: (Block) -> Boolean) : Int {
        //The number of blocks of vegetation removed.
        var count = 0

        //Using an action buffer to set and remove blocks allows undo and redo functionality.
        val actionBuffer = BEActionBuffer(world)

        //Loop through x and z and then height.
        for(x in (p.x - radius).. (p.x + radius)) {
            for(z in (p.z - radius).. (p.z + radius)) {
                for(y in p.y downTo (p.y - depth)) {
                    val pos = BlockPos(x, y, z)

                    //Ignore air blocks
                    if(world.isAirBlock(pos)) continue

                    //Remove the block if it satisfies the removal predicate.
                    if(remover(world.getBlockState(pos).block)) {
                        actionBuffer.removeBlock(pos)
                        count++

                        //Account for two-block-tall vegetation whose bottom halves were cut off at the first iteration.
                        if(checkBlockAbove) {
                            val above = pos.add(0, 1, 0)

                            //Remove the top half.
                            if(remover(world.getBlockState(above).block)) {
                                actionBuffer.removeBlock(above)
                                count++
                            }
                        }

                        continue
                    }

                    //Any block other than the removable blocks and air has been reached. Go to the next x-z position.
                    break
                }
            }
        }

        return count
    }



    /**
     * Clears nearby blocks if they are [BushBlock]s. I.e. vegetation (that isn't leaves or logs).
     */
    fun clearVegetation(world: World?, playerEyePos: Vec3i, radius: Int, depth: Int) =
        clearSurfaceBlocks(world!!, playerEyePos, radius, depth, true) { b -> b is BushBlock }.let {
            Commands.writeToChat(number(it) + " of vegetation cleared within a square of width " + radius * 2)
        }



    /**
     * Clears nearby blocks if they are snow blocks. I.e. the slab-like surface ones, not the full-sized ones.
     */
    fun clearSnow(world: World?, playerEyePos: Vec3i, radius: Int, depth: Int) =
        clearSurfaceBlocks(world!!, playerEyePos, radius, depth, false) { b -> b == Blocks.SNOW }.let {
            Commands.writeToChat(number(it) + " of snow cleared within a square of width " + radius * 2)
        }



    /**
     * Pluralises the word 'piece' according to the given [count]. Utility function for the methods in this class that
     * clear 'pieces' of vegetation.
     */
    private fun number(count: Int): String = pluralChecked(count, "piece", "pieces")


}