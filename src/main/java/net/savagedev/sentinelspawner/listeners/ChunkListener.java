package net.savagedev.sentinelspawner.listeners;

import net.savagedev.sentinelspawner.SentinelSpawner;
import net.savagedev.sentinelspawner.utils.SentinelUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {
    private final SentinelSpawner sentinelSpawner;

    public ChunkListener(SentinelSpawner sentinelSpawner) {
        this.sentinelSpawner = sentinelSpawner;
    }

    @EventHandler
    public void on(ChunkUnloadEvent e) {
        SentinelUtils.despawnSentinelsInChunk(e.getChunk());
    }
}
