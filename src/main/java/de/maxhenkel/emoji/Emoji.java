package de.maxhenkel.emoji;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.emoji.config.ClientConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class Emoji implements ClientModInitializer {

    public static final String MODID = "emoji";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static ClientConfig CLIENT_CONFIG;

    @Override
    public void onInitializeClient() {
        Minecraft mc = Minecraft.getInstance();
        CLIENT_CONFIG = ConfigBuilder.build(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("emoji.properties"), ClientConfig::new);
    }

    public static boolean isEmoji(int character) {
        return character > 1_000_000;
    }

}
