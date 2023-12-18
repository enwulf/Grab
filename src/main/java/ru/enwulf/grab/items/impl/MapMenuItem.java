package ru.enwulf.grab.items.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import ru.enwulf.grab.items.BaseItem;
import ru.enwulf.grab.items.ItemType;
import ru.enwulf.grab.utils.CC;

import java.util.List;


@Getter @Setter
public class MapMenuItem extends BaseItem {

    private final MapView mapView;

    public MapMenuItem(MapView mapView) {
        super(ItemType.MAP_MENU, Material.FILLED_MAP);
        this.mapView = mapView;
        ItemMeta itemMeta = getItemStack().getItemMeta();
        if (itemMeta instanceof MapMeta mapMeta) {
            mapMeta.setMapView(this.mapView);
            mapMeta.setLore(List.of(CC.translate("&8&o[GrabMenu]")));
            applyCommonProperties();
            getItemStack().setItemMeta(mapMeta);
        }
    }
}