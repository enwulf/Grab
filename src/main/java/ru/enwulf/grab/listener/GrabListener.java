package ru.enwulf.grab.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import ru.enwulf.grab.EntityData;
import ru.enwulf.grab.Grab;
import ru.enwulf.grab.items.BaseItem;
import ru.enwulf.grab.items.ItemType;
import ru.enwulf.grab.ui.MapProvider;
import ru.enwulf.grab.utils.CC;

public class GrabListener implements Listener {

    @EventHandler
    public void onHold(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack grabbingItem = BaseItem.get(ItemType.GRABBING).getItemStack();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

        if (mainHandItem.equals(grabbingItem)) {
            ItemStack inventorySlotItem = player.getInventory().getItem(0);
            if (inventorySlotItem == null || !inventorySlotItem.equals(grabbingItem)) {
                player.sendMessage(CC.translate("&cThe must be on the first slot!"));
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (!Grab.get().getEntityStorage().containsKey(player)) {
                    LivingEntity target = Grab.get().getGrabManager().findTargetEntity(player);

                    if (target != null) {
                        Grab.get().getEntityStorage().put(player, new EntityData(target, target.getEyeLocation().distance(player.getEyeLocation())));
                    }
                }

                MapProvider.giveMenu(player);
                Grab.get().getGrabManager().start(player);
            }
        }
    }


    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack offhandItem = player.getInventory().getItemInOffHand();
        if (BaseItem.matches(ItemType.MAP_MENU, offhandItem)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (BaseItem.matches(ItemType.MAP_MENU, event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        if (Grab.get().getGrabManager().isEntityGrabbed((LivingEntity) e.getEntity()))
            e.setCancelled(true);
    }
}