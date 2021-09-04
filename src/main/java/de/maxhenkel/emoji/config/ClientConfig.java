package de.maxhenkel.emoji.config;

import java.util.ArrayList;
import java.util.List;

public class ClientConfig {

    public final ConfigBuilder.ConfigEntry<List<Integer>> recentEmojis;
    public final ConfigBuilder.ConfigEntry<Boolean> recent;
    public final ConfigBuilder.ConfigEntry<Integer> rows;
    public final ConfigBuilder.ConfigEntry<Integer> columns;
    public final ConfigBuilder.ConfigEntry<Integer> historySize;

    public ClientConfig(ConfigBuilder builder) {
        recentEmojis = builder.integerListEntry("recent_emojis", new ArrayList<>());
        recent = builder.booleanEntry("recent", false);
        rows = builder.integerEntry("rows", 5, 3, 64);
        columns = builder.integerEntry("columns", 9, 3, 64);
        historySize = builder.integerEntry("history_size", 128, 8, 512);
    }

}
