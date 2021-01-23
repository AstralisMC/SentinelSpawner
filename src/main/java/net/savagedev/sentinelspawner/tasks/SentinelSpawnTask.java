package net.savagedev.sentinelspawner.tasks;

import net.savagedev.sentinelspawner.SentinelSpawner;
import net.savagedev.sentinelspawner.utils.SentinelUtils;
import org.bukkit.entity.Player;

public class SentinelSpawnTask implements Runnable {
    private final SentinelSpawner sentinelSpawner;

    public SentinelSpawnTask(SentinelSpawner sentinelSpawner) {
        this.sentinelSpawner = sentinelSpawner;
    }

    @Override
    public void run() {
        for (final Player player : this.sentinelSpawner.getServer().getOnlinePlayers()) {
            SentinelUtils.spawnSentinel(player.getLocation());
        }
    }
}
