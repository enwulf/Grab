package ru.enwulf.grab.ui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import ru.enwulf.grab.Grab;
import ru.enwulf.grab.items.BaseItem;
import ru.enwulf.grab.items.ItemType;
import ru.enwulf.grab.items.impl.MapMenuItem;

@Getter
public class MapProvider {
    private static MapView mapView;
    private static MapMenuItem menuItem;

    public static void giveMenu(Player player) {
        giveMapToPlayer(player);
    }

    public static void initializeMap() {
        mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
        ImageRenderer imageRenderer = new ImageRenderer();
        mapView.getRenderers().clear();
        mapView.addRenderer(imageRenderer);
        mapView.setTrackingPosition(false);
        mapView.setScale(Scale.FARTHEST);
        if (menuItem == null)
            menuItem = new MapMenuItem(mapView);
    }


    public static void giveMapToPlayer(Player player) {
        if (Grab.get().getEntityStorage().containsKey(player)) {
            ItemStack offHandItem = player.getInventory().getItemInOffHand();

            if (!offHandItem.getType().equals(Material.AIR) && !BaseItem.matches(ItemType.MAP_MENU, offHandItem)) {
                player.getInventory().addItem(offHandItem);
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            }

            if (mapView != null && !BaseItem.matches(ItemType.MAP_MENU, offHandItem)) {
                player.getInventory().setItemInOffHand(menuItem.getItemStack());
            }
        }
    }


    public static void removeMapFromPlayer(Player player) {
        player.getInventory().setItemInOffHand(null);
    }
}