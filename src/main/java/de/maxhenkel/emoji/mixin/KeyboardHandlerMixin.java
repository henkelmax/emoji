package de.maxhenkel.emoji.mixin;

import de.maxhenkel.emoji.interfaces.IScreen;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V"), cancellable = true)
    private void keyPress(long window, int key, int scanCode, int repeat, int modifiers, CallbackInfo ci) {
        IScreen screen = ((IScreen) Minecraft.getInstance().screen);

        if (repeat == 1) {
            if (screen.preKeyPressed(key, scanCode, modifiers)) {
                ci.cancel();
            }
        } else if (repeat == 0) {
            if (screen.preKeyReleased(key, scanCode, modifiers)) {
                ci.cancel();
            }
        }
    }

}
