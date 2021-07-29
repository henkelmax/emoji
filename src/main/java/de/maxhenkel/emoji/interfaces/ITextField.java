package de.maxhenkel.emoji.interfaces;

public interface ITextField {

    boolean canEdit();

    void addText(String text);

    int getPosX();

    int getPosY();

    int getWidth();

    int getHeight();

}
