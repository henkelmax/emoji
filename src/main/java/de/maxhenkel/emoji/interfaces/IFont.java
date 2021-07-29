package de.maxhenkel.emoji.interfaces;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.resources.ResourceLocation;

public interface IFont {

    FontSet get(ResourceLocation resourceLocation);

    void renderCharacter(BakedGlyph bakedGlyph, boolean bl, boolean bl2, float f, float g, float h, Matrix4f matrix4f, VertexConsumer vertexConsumer, float i, float j, float k, float l, int m);

}
