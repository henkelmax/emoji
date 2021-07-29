package de.maxhenkel.emoji.mixin;

import de.maxhenkel.emoji.interfaces.IScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private double xpos;
    @Shadow
    private double ypos;

    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDD)Z"))
    private boolean mouseScrolled(Screen screen, double x, double y, double amount) {
        IScreen s = ((IScreen) screen);

        if (s.preMouseScrolled(x, y, amount)) {
            return true;
        }

        return screen.mouseScrolled(x, y, amount);
    }

    @Inject(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V"), cancellable = true)
    private void keyPress(long window, int key, int scanCode, int modifiers, CallbackInfo ci) {
        IScreen screen = ((IScreen) Minecraft.getInstance().screen);
        double x = xpos * (double) minecraft.getWindow().getGuiScaledWidth() / (double) minecraft.getWindow().getScreenWidth();
        double y = ypos * (double) minecraft.getWindow().getGuiScaledHeight() / (double) minecraft.getWindow().getScreenHeight();
        if (scanCode == 1) {
            if (screen.preMouseClicked(x, y, key)) {
                ci.cancel();
            }
        } else {
            if (screen.preMouseReleased(x, y, key)) {
                ci.cancel();
            }
        }
    }

}
