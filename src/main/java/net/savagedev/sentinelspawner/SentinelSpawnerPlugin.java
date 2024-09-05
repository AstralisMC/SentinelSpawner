package net.savagedev.sentinelspawner;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;
import net.savagedev.sentinelspawner.listeners.PlayerListener;
import net.savagedev.sentinelspawner.trait.NaturalSentinel;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class SentinelSpawnerPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo
                .create(NaturalSentinel.class)
                .withName("natural-sentinel"));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.removeAllNaturalNPCs();
    }

    @Override
    public void onDisable() {
        this.removeAllNaturalNPCs();
    }

    private void removeAllNaturalNPCs() {
        this.getNaturallySpawnedNpcs().forEach(NPC::destroy);
    }

    public Set<NPC> getNaturallySpawnedNpcs() {
        final Set<NPC> npcs = new HashSet<>();
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (npc.hasTrait(NaturalSentinel.class)) {
                npcs.add(npc);
            }
        }
        return npcs;
    }
}
