package de.maxhenkel.emoji.interfaces;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IScreen {

    void postRender(PoseStack poseStack, int mouseX, int mouseY, float delta);

    boolean preKeyPressed(int key, int scanCode, int modifiers);

    boolean preKeyReleased(int key, int scanCode, int modifiers);

    boolean preMouseClicked(double x, double y, int button);

    boolean preMouseReleased(double x, double y, int button);

    boolean preMouseScrolled(double x, double y, double amount);

}
