package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.emoji.interfaces.IScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"))
    private void render(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float delta) {
        screen.render(poseStack, mouseX, mouseY, delta);
        ((IScreen) screen).postRender(poseStack, mouseX, mouseY, delta);
    }

}
