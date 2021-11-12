package de.maxhenkel.emoji.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

public class ClientConfig {

    public final ConfigEntry<List<Integer>> recentEmojis;
    public final ConfigEntry<Boolean> recent;
    public final ConfigEntry<Integer> rows;
    public final ConfigEntry<Integer> columns;
    public final ConfigEntry<Integer> historySize;

    public ClientConfig(ConfigBuilder builder) {
        recentEmojis = builder.integerListEntry("recent_emojis", new ArrayList<>());
        recent = builder.booleanEntry("recent", false);
        rows = builder.integerEntry("rows", 5, 3, 64);
        columns = builder.integerEntry("columns", 9, 3, 64);
        historySize = builder.integerEntry("history_size", 128, 8, 512);
    }

}
