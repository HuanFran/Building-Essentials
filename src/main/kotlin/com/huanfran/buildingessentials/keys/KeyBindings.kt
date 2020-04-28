package com.huanfran.buildingessentials.keys

import com.huanfran.buildingessentials.main.DISPLAY_NAME
import com.huanfran.buildingessentials.utils.internal.windowHandle
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import org.lwjgl.glfw.GLFW

object KeyBindings {


    /**
     * All [BEKeyBinding]s to be registered for the mod. When a new key is created, it is automatically added to this
     * list.
     */
    val bindings = ArrayList<BEKeyBinding>()

    /**
     * Registers all [BEKeyBinding]s in the [bindings] list.
     */
    fun init() = bindings.forEach { ClientRegistry.registerKeyBinding(it) }


    /*
    KEYS
     */


    private fun keyState(keyCode: Int) = GLFW.glfwGetKey(windowHandle(), keyCode)

    fun isKeyPressed(keyCode: Int) = keyState(keyCode) == GLFW.GLFW_PRESS

    fun isKeyReleased(keyCode: Int) = keyState(keyCode) == GLFW.GLFW_RELEASE



    /*
    MOUSE BUTTONS
     */



    private fun mouseState(mouseCode: Int) = GLFW.glfwGetMouseButton(windowHandle(), mouseCode)

    fun isMousePressed(mouseCode: Int) = mouseState(mouseCode) == GLFW.GLFW_PRESS

    fun isMouseReleased(mouseCode: Int) = mouseState(mouseCode) == GLFW.GLFW_RELEASE



    fun isLeftMousePressed() = isMousePressed(GLFW.GLFW_MOUSE_BUTTON_1)



    /*
    INSTANCES
     */



    val OPEN_MENU = KeyBinding("open menu", GLFW.GLFW_KEY_V, DISPLAY_NAME)



}