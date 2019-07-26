package pl.londek.lobbyutils.listeners;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.londek.lobbyutils.LobbyUtils;

public class PlayerHitListener implements Listener {

    public LobbyUtils plugin;

    public PlayerHitListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if(!player.hasPermission(plugin.getConfigManager().PERMISSION_BYPASS_COMPLETELY_HIT)) {

                event.getEntity().getLocation().getWorld().spawnParticle(Particle.CRIT_MAGIC,event.getEntity().getLocation(),10);

                if(!player.hasPermission(plugin.getConfigManager().PERMISSION_PREMIUM_HIT)) {
                    player.sendMessage(plugin.getConfigManager().MESSAGES_CANTHIT);
                    event.setCancelled(true);
                    return;
                }

                event.setCancelled(false);
                if(event.getEntity() instanceof LivingEntity) {
                    LivingEntity damaged = (LivingEntity)event.getEntity();
                    damaged.setHealth(damaged.getHealth()+event.getFinalDamage());
                } else {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
