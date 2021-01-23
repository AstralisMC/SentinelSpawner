package net.savagedev.sentinelspawner;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;
import net.savagedev.sentinelspawner.listeners.ChunkListener;
import net.savagedev.sentinelspawner.tasks.SentinelSpawnTask;
import net.savagedev.sentinelspawner.trait.NaturallySpawnedSentinelTrait;
import net.savagedev.sentinelspawner.utils.SentinelUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class SentinelSpawner extends JavaPlugin {
    @Override
    public void onEnable() {
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(NaturallySpawnedSentinelTrait.class).withName("natural-sentinel"));
        this.getServer().getScheduler().runTaskTimer(this, new SentinelSpawnTask(this), 0L, 1200L);
        this.loadAllNaturallySpawnedSentinels();
        this.getServer().getPluginManager().registerEvents(new ChunkListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    private void loadAllNaturallySpawnedSentinels() {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (npc.hasTrait(NaturallySpawnedSentinelTrait.class)) {
                SentinelUtils.addSpawnedSentinel(npc.getId());
            }
        }
    }
}
