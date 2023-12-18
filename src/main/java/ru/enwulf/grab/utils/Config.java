package ru.enwulf.grab.utils;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Config {

    private Map<Integer, String> slotActions = new HashMap<>();
    private List<String> texts = new ArrayList<>();
    private String menuTitle;
    private String fontName;

    public Config(Plugin plugin) {
        init(plugin);
    }

    private void init(Plugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
            configuration = YamlConfiguration.loadConfiguration(configFile);
        }

        try {
            menuTitle = configuration.getString("menu.title");
            texts = configuration.getStringList("menu.lines");
            fontName = configuration.getString("menu.font");

            ConfigurationSection slotsSection = configuration.getConfigurationSection("custom-slots");
            slotActions = readSlotActions(slotsSection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, String> readSlotActions(ConfigurationSection slotsSection) {
        if (slotsSection == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> values = slotsSection.getValues(false);
        return values.entrySet().stream()
                .collect(Collectors.toMap(entry -> Integer.parseInt(entry.getKey()), entry -> (String) entry.getValue()));
    }
}