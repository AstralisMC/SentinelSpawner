package net.savagedev.sentinelspawner.utils;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.MobType;
import net.savagedev.sentinelspawner.trait.NaturallySpawnedSentinelTrait;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.HashSet;
import java.util.Set;

public final class SentinelUtils {
    private static final Set<Integer> spawnedSentinels = new HashSet<>();

    private SentinelUtils() {
        throw new UnsupportedOperationException();
    }

    public static void despawnSentinelsInChunk(Chunk chunk) {
        int count = 0;
        for (int id : spawnedSentinels) {
            final NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc != null && npc.getStoredLocation().getChunk().equals(chunk)) {
                spawnedSentinels.remove(id);
                npc.destroy();
                count++;
            }
        }
        if (count > 0) {
            System.out.println("Removed " + count + " NPCs in unloaded chunks.");
        }
    }

    public static void spawnSentinel(Location location) {
        final NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + "Bad Guy");
        npc.getOrAddTrait(MobType.class).setType(EntityType.PLAYER);
        final SentinelTrait trait = npc.getOrAddTrait(NaturallySpawnedSentinelTrait.class);
        trait.setInvincible(false);
        trait.setHealth(20.0D);
        trait.respawnTime = -1;
        npc.spawn(location);
        spawnedSentinels.add(npc.getId());
    }

    public static void addSpawnedSentinel(int id) {
        spawnedSentinels.add(id);
    }
}
