package de.maxhenkel.emoji.gui;

import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import de.maxhenkel.emoji.Emoji;
import de.maxhenkel.emoji.interfaces.IFont;
import de.maxhenkel.emoji.interfaces.IFontSet;
import de.maxhenkel.emoji.interfaces.ITextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class EmojiWidget {

    public static final ResourceLocation CLOSE = new ResourceLocation(Emoji.MODID, "textures/close.png");

    private ITextField textField;
    private int rows, cols;
    private int fontSize;
    private int squareSize;
    private int posX, posY;
    private int width, height;
    private Minecraft minecraft;
    private List<String> emojis;
    private HoverArea bar;
    private HoverArea close;
    private int scrollbarWidth;
    private int scrollbarHeight;
    private int offset;
    private boolean grabbed;
    private double grabX, grabY;
    private Runnable onClose;

    public EmojiWidget(ITextField textField, int rows, int cols, Runnable onClose) {
        this.textField = textField;
        this.minecraft = Minecraft.getInstance();
        this.fontSize = minecraft.font.lineHeight;
        this.squareSize = fontSize + 2;
        this.posX = 0;
        this.posY = 0;
        this.rows = rows;
        this.cols = cols;
        this.onClose = onClose;
        this.scrollbarWidth = 2;
        this.scrollbarHeight = squareSize;
        this.width = 1 + cols * (squareSize + 1) + scrollbarWidth;
        this.bar = new HoverArea(0, 0, width, 8);
        this.close = new HoverArea(width - 8, 0, 8, 8);
        this.height = bar.getHeight() + 1 + rows * (squareSize + 1);

        List<GlyphProvider> providers = ((IFontSet) getFontSet()).getProviders();

        emojis = providers
                .stream()
                .flatMap(listContainer -> listContainer.getSupportedGlyphs().stream())
                .filter(integer -> integer > 1_000_000)
                .map(Character::toString).collect(Collectors.toList());
    }

    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        fill(poseStack, posX, posY, width, height, 0.5F);
        fill(poseStack, posX + bar.getPosX(), posY + bar.getPosY(), bar.getWidth(), bar.getHeight(), 0.5F);

        for (int i = 0; i < getVisibleEmojiCount(); i++) {
            String emoji = getVisibleEmoji(i);
            int row = i / cols;
            int col = i % cols;
            int slotX = posX + 1 + col * (squareSize + 1);
            int slotY = posY + bar.getHeight() + 1 + row * (squareSize + 1);
            if (isHovered(slotX, slotY, squareSize, squareSize, mouseX, mouseY)) {
                fill(poseStack, posX + 1 + col * (squareSize + 1), posY + bar.getHeight() + 1 + row * (squareSize + 1), squareSize, squareSize, 1F, 1F, 1F, 0.5F);
            }
            int width = minecraft.font.width(emoji);
            int offsetX = (minecraft.font.lineHeight - width) / 2;
            minecraft.font.draw(poseStack, emoji, posX + 2 + offsetX + col * (squareSize + 1), posY + bar.getHeight() + 2 + row * (squareSize + 1), 0xFFFFFF);
        }

        float percScroll = (float) offset / (float) Math.max(getTotalRowCount() - rows, 1);
        int trackHeight = height - bar.getHeight();
        int maxOffset = trackHeight - scrollbarHeight;
        int offset = (int) ((float) maxOffset * percScroll);
        fill(poseStack, posX + width - scrollbarWidth, posY + bar.getHeight() + offset, scrollbarWidth, scrollbarHeight, 0.5F);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, CLOSE);
        if (close.isHovered(posX, posY, mouseX, mouseY)) {
            Screen.blit(poseStack, posX + close.getPosX(), posY + close.getPosY(), 8, 0, 8, 8, 16, 16);
        } else {
            Screen.blit(poseStack, posX + close.getPosX(), posY + close.getPosY(), 0, 0, 8, 8, 16, 16);
        }
    }

    @Nullable
    public String getVisibleEmoji(int i) {
        if (i < 0 || offset * cols + i >= emojis.size() || i >= rows * cols) {
            return null;
        }
        return emojis.get(offset * cols + i);
    }

    public int getVisibleEmojiCount() {
        return Math.max(Math.min(emojis.size() - offset * cols, rows * cols), 0);
    }

    public int getTotalRowCount() {
        return (emojis.size() / cols) + (emojis.size() % cols == 0 ? 0 : 1);
    }

    public boolean mouseClicked(double x, double y, int button) {
        if (close.isHovered(posX, posY, (int) x, (int) y)) {
            onClose.run();
            return true;
        }

        if (bar.isHovered(posX, posY, (int) x, (int) y)) {
            if (!grabbed) {
                grabbed = true;
                grabX = x - posX;
                grabY = y - posY;
            }
            return true;
        }

        for (int i = 0; i < getVisibleEmojiCount(); i++) {
            String emoji = getVisibleEmoji(i);
            int row = i / cols;
            int col = i % cols;
            int slotX = posX + 1 + col * (squareSize + 1);
            int slotY = posY + bar.getHeight() + 1 + row * (squareSize + 1);
            if (isHovered(slotX, slotY, squareSize, squareSize, (int) x, (int) y)) {
                if (textField.canEdit()) {
                    textField.addText(emoji);
                    return true;
                }
                break;
            }
        }

        return false;
    }

    public boolean mouseReleased(double x, double y, int button) {
        if (grabbed) {
            grabbed = false;
            return true;
        }
        return false;
    }

    public boolean mouseScrolled(double x, double y, double amount) {
        if (amount < 0D) {
            offset = Math.min(offset + 1, Math.max(getTotalRowCount() - rows, 0));
        } else if (amount > 0D) {
            offset = Math.max(offset - 1, 0);
        }
        return false;
    }

    public void mouseMoved(double x, double y) {
        if (grabbed) {
            posX = (int) (x - grabX);
            posY = (int) (y - grabY);
        }
    }

    public boolean isHovered(int posX, int posY, int width, int height, int mouseX, int mouseY) {
        if (mouseX >= posX && mouseX < posX + width) {
            if (mouseY >= posY && mouseY < posY + height) {
                return true;
            }
        }
        return false;
    }

    public FontSet getFontSet() {
        return ((IFont) minecraft.font).get(Style.DEFAULT_FONT);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onClose.run();
            return true;
        }
        return false;
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public static void fill(PoseStack poseStack, int posX, int posY, int width, int height, float alpha) {
        fill(poseStack, posX, posY, width, height, 0F, 0F, 0F, alpha);
    }

    public static void fill(PoseStack poseStack, int posX, int posY, int width, int height, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.vertex(matrix4f, (float) posX, (float) posY + height, 0.0F).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix4f, (float) posX + width, (float) posY + height, 0.0F).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix4f, (float) posX + width, (float) posY, 0.0F).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix4f, (float) posX, (float) posY, 0.0F).color(red, green, blue, alpha).endVertex();
        builder.end();
        BufferUploader.end(builder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static class HoverArea {
        private final int posX, posY;
        private final int width, height;

        public HoverArea(int posX, int posY, int width, int height) {
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public boolean isHovered(int guiLeft, int guiTop, int mouseX, int mouseY) {
            if (mouseX >= guiLeft + posX && mouseX < guiLeft + posX + width) {
                if (mouseY >= guiTop + posY && mouseY < guiTop + posY + height) {
                    return true;
                }
            }
            return false;
        }
    }

}
