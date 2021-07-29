package de.maxhenkel.emoji;

import de.maxhenkel.emoji.config.ClientConfig;
import de.maxhenkel.emoji.config.ConfigBuilder;
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
        ConfigBuilder.create(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("emoji.properties"), builder -> CLIENT_CONFIG = new ClientConfig(builder));
    }
}
