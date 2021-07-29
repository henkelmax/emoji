package de.maxhenkel.emoji.mixin;

import de.maxhenkel.emoji.interfaces.IFont;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Font.class)
public abstract class FontMixin implements IFont {

    @Override
    public FontSet get(ResourceLocation resourceLocation) {
        return getFontSet(resourceLocation);
    }

    @Shadow
    public abstract FontSet getFontSet(ResourceLocation resourceLocation);

}
