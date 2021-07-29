package de.maxhenkel.emoji.mixin;

import de.maxhenkel.emoji.interfaces.ISignEditScreen;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin implements ISignEditScreen {

    @Shadow
    private TextFieldHelper signField;

    @Override
    public TextFieldHelper getSignField() {
        return signField;
    }
}
