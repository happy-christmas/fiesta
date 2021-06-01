package xyz.hkdev.mc.fiesta.mixin;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hkdev.mc.fiesta.event.ClientStartEvent;
import xyz.hkdev.mc.fiesta.event.EventManager;

import java.lang.reflect.InvocationTargetException;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow
    private static Logger LOGGER;

    @Shadow
    private boolean fullscreen;

    @Inject(method = "run", at = @At("HEAD"))
    private void run(CallbackInfo ci) throws InvocationTargetException, IllegalAccessException {
        EventManager.invokeEvent(new ClientStartEvent());
    }

    @Overwrite
    private void createDisplay() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("Fiesta in HK Makers");
        try {
            Display.create((new PixelFormat()).withDepthBits(24));
        } catch (LWJGLException lwjglexception) {
            LOGGER.error("Couldn't set pixel format", lwjglexception);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
            if (this.fullscreen) {
                this.updateDisplayMode();
            }
            Display.create();
        }
    }

    @Shadow
    private void updateDisplayMode() {
    }
}
