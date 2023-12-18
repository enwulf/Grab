package ru.enwulf.grab.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ru.enwulf.grab.EntityData;
import ru.enwulf.grab.Grab;

import java.util.Map;
import java.util.Random;

@UtilityClass
public class EntityUtils {

    public void rotateScreen(LivingEntity entity) {
        Random random = new Random();
        int taskId = Bukkit.getScheduler().runTaskTimer(Grab.get(), () -> {
            Location currentLocation = entity.getLocation();

            float yaw = random.nextFloat() * 360;
            float pitch = random.nextFloat() * 180 - 90;

            Location rotatedLocation = new Location(
                    currentLocation.getWorld(), currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), yaw, pitch);


            entity.teleport(rotatedLocation);

        }, 0, 1).getTaskId();

        Bukkit.getScheduler().runTaskLater(Grab.get(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
        }, 10 * 20);
    }


    public void pushEntity(LivingEntity entity, double force) {
        for (Map.Entry<Player, EntityData> data : Grab.get().getEntityStorage().entrySet()) {
            if (data.getValue().getEntity().equals(entity)) {
                Player player = data.getKey();
                Vector direction = player.getLocation().getDirection();
                Grab.get().getGrabManager().stopGrabbing(player);
                entity.setVelocity(direction.multiply(force));
            }
        }
    }
}