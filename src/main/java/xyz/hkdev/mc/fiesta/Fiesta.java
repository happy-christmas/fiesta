package xyz.hkdev.mc.fiesta;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.hkdev.mc.fiesta.event.ClientStartEvent;
import xyz.hkdev.mc.fiesta.event.EventHandler;
import xyz.hkdev.mc.fiesta.event.EventManager;

@Mod(modid = Fiesta.MOD_ID)
public class Fiesta {
    public static final String MOD_ID = "fiesta";

    public Fiesta() {
        EventManager.registerListener(this);

    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        System.out.println("Hello world!");
    }

    @EventHandler
    public void onStart(ClientStartEvent event) {
        System.out.println("Start event invoked~!");
    }
}
