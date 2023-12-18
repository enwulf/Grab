package ru.enwulf.grab.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import ru.enwulf.grab.ActionType;
import ru.enwulf.grab.EntityData;
import ru.enwulf.grab.Grab;
import ru.enwulf.grab.utils.Config;

public class SlotClickListener implements Listener {

    @EventHandler public void onSlotChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        EntityData entityData = Grab.get().getEntityStorage().get(player);


        if (entityData != null) {
            LivingEntity targetEntity = entityData.getEntity();
            int slot = event.getNewSlot() + 1;

            Config config = Grab.get().getCfg();
            String action = config.getSlotActions().get(slot);

            if (action != null) {
                try {
                    ActionType actionType = ActionType.valueOf(action.toUpperCase());
                    actionType.perform(targetEntity);
                    player.getInventory().setHeldItemSlot(0);
                } catch (IllegalArgumentException e) {
                    player.sendMessage("Invalid action type: " + action);
                }
            }
        }
    }
}