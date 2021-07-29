package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import de.maxhenkel.emoji.interfaces.IFont;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
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
    abstract FontSet getFontSet(ResourceLocation resourceLocation);

    @Override
    public void renderCharacter(BakedGlyph bakedGlyph, boolean bl, boolean bl2, float f, float g, float h, Matrix4f matrix4f, VertexConsumer vertexConsumer, float i, float j, float k, float l, int m) {
        renderChar(bakedGlyph, bl, bl2, f, g, h, matrix4f, vertexConsumer, i, j, k, l, m);
    }

    @Shadow
    abstract void renderChar(BakedGlyph bakedGlyph, boolean bl, boolean bl2, float f, float g, float h, Matrix4f matrix4f, VertexConsumer vertexConsumer, float i, float j, float k, float l, int m);

}
