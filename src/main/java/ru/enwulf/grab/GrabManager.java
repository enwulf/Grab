package ru.enwulf.grab;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import ru.enwulf.grab.ui.MapProvider;
import ru.enwulf.grab.utils.CC;

import java.util.Map;

public class GrabManager extends AbstractRightClickHandler {

    public GrabManager(Plugin plugin) {
        super(plugin);
    }

    public void start(Player player) {
        cancelTimerIfRunning();
        startTimer(player);
        startSecondaryLoop(player);

        EntityData entityData = Grab.get().getEntityStorage().get(player);

        if (entityData != null) {
            LivingEntity entity = entityData.getEntity();
            CC.sendActionBar(player, "You take a &6" + entity.getName());
        }
    }

    public LivingEntity findTargetEntity(Player player) {
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(), player.getEyeLocation().getDirection(), 100D,
                entity -> (entity instanceof LivingEntity && !entity.equals(player)));

        return result != null ? (LivingEntity) result.getHitEntity() : null;
    }


    public boolean isEntityGrabbed(LivingEntity entity) {
        return entity != null && Grab.get().getEntityStorage().values().stream()
                .anyMatch(value -> value.getEntity() == entity);
    }

    public void launchEntityInPlayerView(Player player, LivingEntity targetEntity, double distance) {
        if (targetEntity == null || !targetEntity.isValid()) {
            return;
        }

        Location centerView = player.getEyeLocation().clone()
                .add(player.getEyeLocation().getDirection().multiply(distance));

        float speed = 0.15F;
        Vector direction = centerView.clone()
                .toVector()
                .subtract(targetEntity.getLocation().toVector())
                .subtract(new Vector(0.0D, targetEntity.getBoundingBox().getHeight() / 2.0D, 0.0D))
                .multiply(0.04F + speed);

        targetEntity.setVelocity(direction);
    }


    public void stopGrabbing(Player player) {
        Grab.get().getEntityStorage().remove(player);
        MapProvider.removeMapFromPlayer(player);
        CC.clearActionBar(player);
    }


    @Override
    protected void executeLoop(Player player) {
        EntityData entityData = Grab.get().getEntityStorage().get(player);

        if (entityData != null) {
            double distance = entityData.getDistance();
            LivingEntity entity = entityData.getEntity();
            launchEntityInPlayerView(player, entity, distance);
        }
    }

    @Override
    protected void cancelLoop(Player player) {
        stopGrabbing(player);
    }

    private void cancelTimerIfRunning() {
        if (getTimerTaskId() != -1) {
            cancelTimer();
        }
    }
}