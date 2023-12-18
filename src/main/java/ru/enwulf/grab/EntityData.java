package ru.enwulf.grab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;

@AllArgsConstructor
@Getter
public class EntityData {

    public final LivingEntity entity;
    public double distance;

}
