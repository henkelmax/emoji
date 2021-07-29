package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.font.GlyphProvider;
import de.maxhenkel.emoji.interfaces.IFontSet;
import net.minecraft.client.gui.font.FontSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FontSet.class)
public abstract class FontSetMixin implements IFontSet {

    @Shadow
    @Final
    private List<GlyphProvider> providers;

    @Override
    public List<GlyphProvider> getProviders() {
        return providers;
    }

}
