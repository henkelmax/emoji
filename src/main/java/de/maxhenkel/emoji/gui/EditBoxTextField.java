package de.maxhenkel.emoji.gui;

import de.maxhenkel.emoji.interfaces.IEditBox;
import de.maxhenkel.emoji.interfaces.ITextField;
import net.minecraft.client.gui.components.EditBox;

public class EditBoxTextField implements ITextField {

    private EditBox editBox;

    public EditBoxTextField(EditBox editBox) {
        this.editBox = editBox;
    }

    @Override
    public boolean canEdit() {
        return ((IEditBox) editBox).canEdit();
    }

    @Override
    public void addText(String text) {
        editBox.insertText(text);
    }

    @Override
    public int getPosX() {
        return editBox.x;
    }

    @Override
    public int getPosY() {
        return editBox.y;
    }

    @Override
    public int getWidth() {
        return editBox.getWidth();
    }

    @Override
    public int getHeight() {
        return editBox.getHeight();
    }
}
