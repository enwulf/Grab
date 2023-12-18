package ru.enwulf.grab.items.impl;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import ru.enwulf.grab.items.BaseItem;
import ru.enwulf.grab.items.ItemType;
import ru.enwulf.grab.utils.CC;

public class GrabbingItem extends BaseItem {

    public GrabbingItem() {
        super(ItemType.GRABBING, Material.STICK);

        ItemMeta itemMeta = getItemStack().getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(CC.translate("&dMagic Stick"));
            applyCommonProperties();
            getItemStack().setItemMeta(itemMeta);
        }
    }
}
