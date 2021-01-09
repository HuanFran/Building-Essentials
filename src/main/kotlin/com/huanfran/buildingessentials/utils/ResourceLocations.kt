package com.huanfran.buildingessentials.utils

import com.huanfran.buildingessentials.main.BuildingEssentials

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
    
    
    
    val CLEAR_VEGETATION =  BETextureLocation("$buttons/clear_vegetation_button.png", 64, 64)

    val CLEAR_WEATHER =     BETextureLocation("$buttons/clear_weather_button.png", 64, 64)

    val DAYTIME =           BETextureLocation("$buttons/daytime_button.png", 64, 64)

    val NIGHTTIME =         BETextureLocation("$buttons/nighttime_button.png", 64, 64)

    val CLEAR_SNOW =        BETextureLocation("$buttons/clear_snow_button.png", 64, 64)

    val CHECKBOX =          BETextureLocation("$buttons/building-checkbox.png", 16, 16)

    val CHECKBOX_CHECKED =  BETextureLocation("$buttons/building-checkbox-checked.png", 16, 16)

    val UNDO =              BETextureLocation("$buttons/undo_button.png", 64, 64)

    val REDO =              BETextureLocation("$buttons/redo_button.png", 64, 64)

    val KILL_MOBS =         BETextureLocation("$buttons/no_mobs_button.png", 64, 64)



    /*
    Others
     */



    val INVENTORY_SLOT =            BETextureLocation("$gui/inventory_slot.png", 32, 32)

    val INVENTORY_SLOT_SELECTED =   BETextureLocation("$gui/inventory_slot_selected.png", 32, 32)
    

}