package pl.londek.lobbyutils.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.londek.lobbyutils.LobbyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CooldownManager {

    private LobbyUtils plugin;
    private BukkitTask bukkitTask;
    private Map<UUID,Integer> cooldownsMap = new HashMap<>();

    public BukkitRunnable runnable = new BukkitRunnable() {
        @Override
        public void run() {
            if(cooldownsMap.isEmpty()) {
                return;
            }

            for(UUID uuid : cooldownsMap.keySet()) {
                int leftCooldown = getCooldown(uuid);
                cooldownsMap.remove(uuid);
                if((leftCooldown-1)<=0) {
                    return;
                }
                cooldownsMap.put(uuid,leftCooldown-1);
            }
        }
    };

    public CooldownManager(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    public boolean hasCooldown(UUID uuid) {
        Objects.requireNonNull(uuid, "CooldownManager catched uuid that's null");
        if(uuid==null) {
            return false;
        }
        return cooldownsMap.containsKey(uuid);
    }

    public void addCooldown(UUID uuid, int endsIn) {
        Objects.requireNonNull(uuid, "CooldownManager catched adding uuid that's null");
        if(cooldownsMap.containsKey(uuid)) {
            return;
        }
        cooldownsMap.put(uuid,endsIn);
    }

    public int getCooldown(UUID uuid) {
        Objects.requireNonNull(uuid, "CooldownManager catched uuid that's null");
        if(!cooldownsMap.containsKey(uuid)) {
            return -1;
        }
        return cooldownsMap.get(uuid);
    }
    public void runCooldownScheduler() {
         bukkitTask = runnable.runTaskTimer(plugin,0,20);
    }
}
