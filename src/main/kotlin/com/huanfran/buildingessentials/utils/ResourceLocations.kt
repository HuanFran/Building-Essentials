package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.main.BuildingEssentials
import net.minecraft.util.ResourceLocation

/**
 * Contains resource locations for various textures used by the [BuildingEssentials] mod.
 */
object ResourceLocations {


    /*
    Root locations
     */



    private const val root = "building-essentials"

    private const val textures = "$root:textures"

    private const val gui = "$textures/gui"

    private const val buttons = "$gui/buttons"



    /*
    Buttons
     */
    
    
    
    val CLEAR_VEGETATION = ResourceLocation("$buttons/clear_vegetation_button.png")

    val CLEAR_WEATHER = ResourceLocation("$buttons/clear_weather_button.png")

    val DAYTIME = ResourceLocation("$buttons/daytime_button.png")

    val NIGHTTIME = ResourceLocation("$buttons/nighttime_button.png")

    val CLEAR_SNOW = ResourceLocation("$buttons/clear_snow_button.png")

    val CHECKBOX = ResourceLocation("$buttons/building-checkbox.png")

    val CHECKBOX_CHECKED = ResourceLocation("$buttons/building-checkbox-checked.png")

    val UNDO = ResourceLocation("$buttons/undo_button.png")

    val REDO = ResourceLocation("$buttons/redo_button.png")
    
    



}