package ru.enwulf.grab;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.enwulf.grab.items.impl.GrabbingItem;
import ru.enwulf.grab.listener.GrabListener;
import ru.enwulf.grab.listener.SlotClickListener;
import ru.enwulf.grab.ui.MapProvider;
import ru.enwulf.grab.utils.Config;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class Grab extends JavaPlugin {
    private final Map<Player, EntityData> entityStorage = new HashMap<>();
    public Config cfg;
    public GrabManager grabManager;


    @Override
    public void onEnable() {
        loadConfig();
        initializeMap();
        initializeManagers();
        registerEvents();
        registerCommands();
    }

    public void initializeMap() {
        MapProvider.initializeMap();
    }

    private void initializeManagers() {
        grabManager = new GrabManager(this);
        new GrabbingItem();
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new GrabListener(), this);
        pluginManager.registerEvents(new SlotClickListener(), this);
    }

    private void registerCommands() {
        getCommand("grab").setExecutor(new CommandListener());
        getCommand("grab").setTabCompleter(new CommandListener());
    }


    public void loadConfig() {
        cfg = new Config(this);
    }

    @Override
    public void onDisable() {
        entityStorage.clear();
    }

    public static Grab get(){
        return getPlugin(Grab.class);
    }
}

