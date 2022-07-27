package de.maxhenkel.emoji.mixin;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import de.maxhenkel.emoji.Emoji;
import de.maxhenkel.emoji.interfaces.IFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.StringRenderOutput.class)
public class StringRenderOutputMixin {

    @Shadow
    @Final
    private float a;
    @Shadow
    float x;
    @Shadow
    float y;
    @Shadow
    @Final
    MultiBufferSource bufferSource;
    @Shadow
    @Final
    private Font.DisplayMode mode;
    @Shadow
    @Final
    private Matrix4f pose;
    @Shadow
    @Final
    private int packedLightCoords;

    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void accept(int i, Style style, int character, CallbackInfoReturnable<Boolean> ci) {
        if (!Emoji.isEmoji(character) || style.isObfuscated()) {
            return;
        }
        IFont font = ((IFont) Minecraft.getInstance().font);
        FontSet fontSet = font.get(style.getFont());
        GlyphInfo glyphInfo = fontSet.getGlyphInfo(character, false);
        BakedGlyph bakedGlyph = fontSet.getGlyph(character);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(bakedGlyph.renderType(mode));
        font.renderCharacter(bakedGlyph, false, false, 0F, x, y, pose, vertexConsumer, 1F, 1F, 1F, a, packedLightCoords);

        x += glyphInfo.getAdvance(false);
        ci.setReturnValue(true);
    }

}
