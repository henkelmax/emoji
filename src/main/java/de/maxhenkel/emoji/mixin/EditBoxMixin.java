package de.maxhenkel.emoji.mixin;

import de.maxhenkel.emoji.interfaces.IEditBox;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EditBox.class)
public abstract class EditBoxMixin implements IEditBox {

    @Shadow
    private boolean isEditable;

    @Override
    public boolean canEdit() {
        return isEditable;
    }
}
