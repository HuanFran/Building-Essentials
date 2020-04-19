package com.huanfran.buildingessentials.main

import com.huanfran.buildingessentials.keys.KeyBindings
import com.huanfran.buildingessentials.networking.BEPacketHandler
import com.huanfran.buildingessentials.tile.BETileEntityTypes
import com.huanfran.buildingessentials.tile.mirror.MirrorRenderer
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(MODID)
object BuildingEssentials {


    init {
        FORGE_BUS.register(this)
        MOD_BUS.register(this)

        KeyBindings.init()

        ClientRegistry.bindTileEntityRenderer(BETileEntityTypes.MIRROR) { MirrorRenderer() }

        BEPacketHandler.register()
    }


}

//First = FMLCommonSetupEvent
//Second = FMLClientSetupEvent / FMLDedicatedServerSetupEvent
//Third = InterModEnqueueEvent
//Fourth = InterModProcessEvent

/*
/**
 * Parent type to all ModLifecycle events. This is based on Forge EventBus. They fire through the
 * ModContainer's eventbus instance.
 */
public class ModLifecycleEvent extends Event
{
    private final ModContainer container;

    public ModLifecycleEvent(ModContainer container)
    {
        this.container = container;
    }

    public final String description()
    {
       String cn = getClass().getName();
       return cn.substring(cn.lastIndexOf('.')+1);
    }

    public Stream<InterModComms.IMCMessage> getIMCStream() {
        return InterModComms.getMessages(this.container.getModId());
    }

    public Stream<InterModComms.IMCMessage> getIMCStream(Predicate<String> methodFilter) {
        return InterModComms.getMessages(this.container.getModId(), methodFilter);
    }

    @Override
    public String toString() {
        return description();
    }
}
 */