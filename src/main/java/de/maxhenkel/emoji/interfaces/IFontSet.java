package de.maxhenkel.emoji.interfaces;

import com.mojang.blaze3d.font.GlyphProvider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;

import java.util.List;

public interface IFontSet {

    List<GlyphProvider> getProviders();

    Int2ObjectMap<BakedGlyph> getBakedGlyphs();

}
