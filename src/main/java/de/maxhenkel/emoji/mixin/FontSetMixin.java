package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.font.GlyphProvider;
import de.maxhenkel.emoji.interfaces.IFontSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FontSet.class)
public abstract class FontSetMixin implements IFontSet {

    @Shadow
    @Final
    private List<GlyphProvider> providers;
    @Shadow
    @Final
    private Int2ObjectMap<BakedGlyph> glyphs;

    @Override
    public List<GlyphProvider> getProviders() {
        return providers;
    }

    @Override
    public Int2ObjectMap<BakedGlyph> getBakedGlyphs() {
        return glyphs;
    }
}
