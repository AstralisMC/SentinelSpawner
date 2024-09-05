package net.savagedev.sentinelspawner.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.savagedev.sentinelspawner.SentinelSpawnerPlugin;
import net.savagedev.sentinelspawner.trait.NaturalSentinel;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final HashMap<UUID, Chunk> playerLastChunks = new HashMap<>();

    private final SentinelSpawnerPlugin plugin;

    public PlayerListener(SentinelSpawnerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        this.playerLastChunks.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void on(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        final Location locTo = event.getTo();
        final Location locFrom = event.getFrom();

        if (locTo == null) {
            return;
        }

        // Check if player has moved to a new chunk
        final Chunk chunkTo = locTo.getChunk();
        final Chunk chunkFrom = locFrom.getChunk();

        if (chunkTo.equals(chunkFrom)) {
            return; // Same chunk, no need to check for spawning
        }

        // Check if the player has moved to a new chunk since last check
        final UUID uuid = player.getUniqueId();
        final Chunk lastChunk = this.playerLastChunks.get(uuid);

        if (lastChunk == null || !lastChunk.equals(chunkTo)) {
            this.playerLastChunks.put(uuid, chunkTo); // Update player's current chunk
            attemptMobSpawn(chunkTo);
        }
    }

    private void attemptMobSpawn(Chunk chunk) {
        final int spawnChance = this.plugin.getConfig().getInt("sentinel-spawn-chance");

        if (random.nextInt(100) <= spawnChance) {
            // Limit the number of mobs per chunk
            final int maxMobs = this.plugin.getConfig().getInt("max-mobs-per-chunk");
            final long mobCount = Arrays.stream(chunk.getEntities())
                    .filter(entity -> entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON)
                    .count();

            if (mobCount < maxMobs) {
                // Spawn a mob at a random location within the chunk
                final int x = chunk.getX() * 16 + random.nextInt(16);
                final int z = chunk.getZ() * 16 + random.nextInt(16);
                final int y = chunk.getWorld().getHighestBlockYAt(x, z) + 1;

                final Location spawnLocation = new Location(chunk.getWorld(), x, y, z);

                if (spawnLocation.getBlock().getType() == Material.AIR) {
                    final NPC sentinel = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER,
                            this.plugin.getConfig().getString("natural-sentinel-name"));

                    sentinel.addTrait(NaturalSentinel.class);
                    sentinel.spawn(spawnLocation);
                }
            }
        }
    }
}
