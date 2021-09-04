package de.maxhenkel.emoji.config;

import de.maxhenkel.emoji.Emoji;

import java.util.List;
import java.util.stream.Collectors;

public class EmojiHistory {

    public static List<Integer> getRecentlyUsed() {
        return Emoji.CLIENT_CONFIG.recentEmojis.get();
    }

    public static void onTypeEmoji(int emoji) {
        List<Integer> recentEmojis = Emoji.CLIENT_CONFIG.recentEmojis.get();

        recentEmojis.add(0, emoji);

        List<Integer> newRecentEmojis = recentEmojis.stream().distinct().limit(Emoji.CLIENT_CONFIG.historySize.get()).collect(Collectors.toList());
        Emoji.CLIENT_CONFIG.recentEmojis.set(newRecentEmojis);
        Emoji.CLIENT_CONFIG.recentEmojis.save();
    }

}
