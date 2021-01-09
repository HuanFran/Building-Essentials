package com.huanfran.buildingessentials.rendering

import com.huanfran.buildingessentials.item.StaffOfSelection
import com.huanfran.buildingessentials.utils.extensions.toVector3
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.Minecraft

object SelectionRenderer {


    /*
    NOTE: Can only place a third node if it is not on the same plane as the other two.
     */



    fun renderSelection(stack: MatrixStack, partialTicks: Float) {
        val world = Minecraft.getInstance().player?.world ?: return

        if(StaffOfSelection.selection0 != null) {
            val v0 = StaffOfSelection.selection0?.toVector3() ?: return

            if(StaffOfSelection.selection1 != null) {
                val v1 = StaffOfSelection.selection1?.toVector3() ?: return

                if(StaffOfSelection.selection2 != null) {
                    val v2 = StaffOfSelection.selection2?.toVector3() ?: return
                    //Three nodes - volume
                } else {
                    //Two nodes - area or line

                    val dif = v1 - v0

                    val sameX = dif.x == 0.0
                    val sameY = dif.y == 0.0
                    val sameZ = dif.z == 0.0

                    if(sameX && sameY || sameX && sameZ || sameY && sameZ) {
                        //Line
                    } else {
                        //Area
                    }
                }
            } else {
                //One node - selection in progress.
            }
        }
    }


}