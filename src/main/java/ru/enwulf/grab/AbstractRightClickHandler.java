package ru.enwulf.grab;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class AbstractRightClickHandler {

    private final Plugin plugin;

    @Getter private int timerTaskId = -1;

    private int remainingTicks = 6;
    private int secondaryLoopTaskId = -1;
    private boolean isSecondaryLoopActive = false;

    public AbstractRightClickHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startTimer(Player player) {
        this.timerTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            if (this.remainingTicks > 0) {
                this.remainingTicks--;
            } else {
                cancelTimer();
                cancelSecondaryLoop();
                cancelLoop(player);
            }
        },0L, 1L);
    }

    public void cancelTimer() {
        if (this.timerTaskId != -1) {
            Bukkit.getScheduler().cancelTask(this.timerTaskId);
            this.timerTaskId = -1;
            this.remainingTicks = 6;
        }
    }

    public void startSecondaryLoop(Player player) {
        this.isSecondaryLoopActive = true;
        this.secondaryLoopTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            if (!this.isSecondaryLoopActive) {
                cancelSecondaryLoop();
                return;
            }
            executeLoop(player);
        },0L, 1L);
    }

    private void cancelSecondaryLoop() {
        this.isSecondaryLoopActive = false;
        if (this.secondaryLoopTaskId != -1) {
            Bukkit.getScheduler().cancelTask(this.secondaryLoopTaskId);
            this.secondaryLoopTaskId = -1;
        }
    }

    protected abstract void executeLoop(Player player);

    protected abstract void cancelLoop(Player player);
}


