package net.savagedev.sentinelspawner.trait;

import net.citizensnpcs.api.persistence.Persist;
import org.mcmonkey.sentinel.SentinelTrait;

public class NaturallySpawnedSentinelTrait extends SentinelTrait {
    @Persist
    private final long timeSpawned = System.currentTimeMillis();

    public NaturallySpawnedSentinelTrait() {
    }

    public long getTimeSpawned() {
        return this.timeSpawned;
    }
}
