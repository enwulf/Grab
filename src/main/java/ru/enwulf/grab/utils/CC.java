package ru.enwulf.grab.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

@UtilityClass
public class CC {

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(translate(message)).create());
    }

    public void clearActionBar(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("").create());

    }

    public String translate(String line) {
        return ChatColor.translateAlternateColorCodes('&', line);
    }
}