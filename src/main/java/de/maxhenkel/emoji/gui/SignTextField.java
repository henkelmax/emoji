package de.maxhenkel.emoji.gui;

import de.maxhenkel.emoji.interfaces.ISignEditScreen;
import de.maxhenkel.emoji.interfaces.ITextField;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;

public class SignTextField implements ITextField {

    private SignEditScreen screen;

    public SignTextField(SignEditScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean canEdit() {
        return true;
    }

    @Override
    public void addText(String text) {
        ((ISignEditScreen) screen).getSignField().insertText(text);
    }

    @Override
    public int getPosX() {
        return screen.width / 2 - 25;
    }

    @Override
    public int getPosY() {
        return screen.height / 2 - 25;
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 50;
    }
}
