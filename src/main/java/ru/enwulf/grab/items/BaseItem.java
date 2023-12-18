package ru.enwulf.grab.items;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;


@Getter
public class BaseItem {
    private final ItemStack itemStack;
    private final ItemType type;

    private static final Map<ItemType, BaseItem> items = new HashMap<>();

    public BaseItem(ItemType type, Material material) {
        this.type = type;
        itemStack = new ItemStack(material);
        items.putIfAbsent(type, this);
    }


    public static BaseItem get(ItemType type) {
        return items.values().stream()
                .filter(entry -> entry.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public static boolean matches(ItemType type, ItemStack otherItem) {
        return items.get(type).getItemStack().isSimilar(otherItem);
    }

    public void applyCommonProperties() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE
            );
        }

        itemStack.setItemMeta(meta);
    }
}