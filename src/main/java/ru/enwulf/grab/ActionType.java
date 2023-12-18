package ru.enwulf.grab;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import ru.enwulf.grab.utils.EntityUtils;

import java.util.function.Consumer;

public enum ActionType {

    BURN(entity -> entity.setFireTicks(100)),
    KILL(entity -> entity.setHealth(0)),
    ROTATE_SCREEN(EntityUtils::rotateScreen),
    PUSH(entity -> EntityUtils.pushEntity(entity, 5)),
    KICK(entity -> {
        if (entity instanceof Player)
            ((Player) entity).kickPlayer("Kicked by admin");
    }),
    CLEAR_INVENTORY(entity -> {
        if (entity instanceof Player)
            ((Player) entity).getInventory().clear();
    });

    private final Consumer<LivingEntity> action;

    ActionType(Consumer<LivingEntity> action) {
        this.action = action;

    }

    public void perform(LivingEntity entity) {
        action.accept(entity);
    }

}