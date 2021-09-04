package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.emoji.gui.EditBoxTextField;
import de.maxhenkel.emoji.gui.EmojiWidget;
import de.maxhenkel.emoji.gui.SignTextField;
import de.maxhenkel.emoji.interfaces.IScreen;
import de.maxhenkel.emoji.interfaces.ITextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler implements Widget, IScreen {

    @Shadow
    @Final
    private List<GuiEventListener> children;

    @Shadow
    @org.jetbrains.annotations.Nullable
    protected Minecraft minecraft;
    private EmojiWidget widget;

    @Inject(at = @At("TAIL"), method = "init(Lnet/minecraft/client/Minecraft;II)V")
    private void init(Minecraft minecraft, int width, int height, CallbackInfo info) {
        widget = null;
    }

    @Override
    public void postRender(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (widget != null) {
            poseStack.pushPose();
            poseStack.translate(0D, 0D, 1000D);
            widget.render(poseStack, mouseX, mouseY, delta);
            poseStack.popPose();
        }
    }

    @Override
    public boolean preKeyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_PERIOD && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0) {
            ITextField textField = getActiveTextField();
            if (textField != null) {
                openWidget(textField);
                return true;
            }
        }
        if (widget != null && widget.keyPressed(key, scanCode, modifiers)) {
            return true;
        }
        return false;
    }

    public void openWidget(ITextField textField) {
        widget = new EmojiWidget(textField, () -> widget = null);
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int x = textField.getPosX() + textField.getWidth();
        int y = textField.getPosY() + textField.getHeight();

        if (x + widget.getWidth() > screenWidth) {
            x = screenWidth - widget.getWidth();
        }

        if (y + widget.getHeight() > screenHeight) {
            y = screenHeight - widget.getHeight();
        }

        widget.setPos(x, y);
    }

    @Nullable
    public ITextField getActiveTextField() {
        if ((Object) this instanceof SignEditScreen sign) {
            return new SignTextField(sign);
        }

        return children
                .stream()
                .filter(EditBox.class::isInstance)
                .map(EditBox.class::cast)
                .filter(AbstractWidget::isFocused)
                .findFirst().map(EditBoxTextField::new).orElse(null);
    }

    @Override
    public boolean preKeyReleased(int key, int scanCode, int modifiers) {
        if (widget != null && widget.keyReleased(key, scanCode, modifiers)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean preMouseClicked(double x, double y, int button) {
        if (widget != null && widget.mouseClicked(x, y, button)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean preMouseReleased(double x, double y, int button) {
        if (widget != null && widget.mouseReleased(x, y, button)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean preMouseScrolled(double x, double y, double amount) {
        if (widget != null && widget.mouseScrolled(x, y, amount)) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseMoved(double x, double y) {
        if (widget != null) {
            widget.mouseMoved(x, y);
        }
        super.mouseMoved(x, y);
    }
}
