package pl.londek.lobbyutils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.londek.lobbyutils.LobbyUtils;
import pl.londek.lobbyutils.constants.Items;

public class PlayerInteractListener implements Listener {

    public LobbyUtils plugin;

    public PlayerInteractListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack c = e.getItem();
        Player executor = e.getPlayer();

        if(c==null) {
            return;
        }

        if(c.isSimilar(Items.COMPASS)) {
            e.setCancelled(true);
            executor.openInventory(plugin.getConfigManager().compassInventory.getBase());
        } else if(c.isSimilar(Items.ENABLE_FLY)) {
            e.setCancelled(true);
            if(!executor.hasPermission(plugin.getConfigManager().PERMISSION_PREMIUM_FLY)) {
                executor.sendMessage(plugin.getConfigManager().MESSAGES_NO_PERMISSION.replaceAll("%permission%",plugin.getConfigManager().PERMISSION_PREMIUM_FLY));
                return;
            }

            executor.setVelocity(new Vector(0F,3F,0F));
            executor.setAllowFlight(true);
            executor.setFlying(true);
            executor.getInventory().setItem(2, Items.DISABLE_FLY);
        } else if (c.isSimilar(Items.DISABLE_FLY)) {
            e.setCancelled(true);

            executor.setVelocity(new Vector(0F,-1F,0F));
            executor.setAllowFlight(false);
            executor.setFlying(false);
            executor.getInventory().setItem(2, Items.ENABLE_FLY);
        } else if(c.isSimilar(Items.ENABLE_HIDEPLAYERS)) {
            e.setCancelled(true);
            if(!executor.hasPermission(plugin.getConfigManager().PERMISSION_PREMIUM_HIDEPLAYERS)) {
                executor.sendMessage(plugin.getConfigManager().MESSAGES_NO_PERMISSION.replaceAll("%permission%",plugin.getConfigManager().PERMISSION_PREMIUM_HIDEPLAYERS));
                return;
            }

            executor.sendMessage(plugin.getConfigManager().MESSAGES_ENABLEHIDEPLAYERS);

            for(Player player : Bukkit.getOnlinePlayers()) {
                executor.hidePlayer(plugin,player);
            }
            plugin.vanishAnotherPlayers.add(executor);

            executor.getInventory().setItem(3, Items.DISABLE_HIDEPLAYERS);
        } else if(c.isSimilar(Items.DISABLE_HIDEPLAYERS)) {
            e.setCancelled(true);
            executor.sendMessage(plugin.getConfigManager().MESSAGES_DISABLEHIDEPLAYERS);

            for(Player player : Bukkit.getOnlinePlayers()) {
                executor.showPlayer(plugin,player);
            }
            plugin.vanishAnotherPlayers.remove(executor);

            executor.getInventory().setItem(3, Items.ENABLE_HIDEPLAYERS);
        } else if(c.isSimilar(Items.ENABLE_VANISH)) {
            e.setCancelled(true);
            if(!executor.hasPermission(plugin.getConfigManager().PERMISSION_PREMIUM_VANISH)) {
                executor.sendMessage(plugin.getConfigManager().MESSAGES_NO_PERMISSION.replaceAll("%permission%",plugin.getConfigManager().PERMISSION_PREMIUM_VANISH));
                return;
            }

            if(plugin.getConfigManager().SETTING_ENABLE_VANISH_COOLDOWN) {
                if(!plugin.getVanishCooldown().hasCooldown(executor.getUniqueId())) {
                    plugin.getVanishCooldown().addCooldown(executor.getUniqueId(),plugin.getConfigManager().SETTING_VANISH_COOLDOWN);
                } else {
                    executor.sendMessage(plugin.getConfigManager().MESSAGES_COOLDOWN.replaceAll("%time%",""+plugin.getVanishCooldown().getCooldown(executor.getUniqueId())));
                    return;
                }
            }

            executor.sendMessage(plugin.getConfigManager().MESSAGES_ENABLEVANISH);
            executor.getInventory().setItem(4, Items.DISABLE_VANISH);

            if(plugin.getConfigManager().SETTING_ENABLE_VANISH_PARTICLES) {
                executor.getWorld().spawnParticle(Particle.END_ROD, executor.getLocation().add(0, 1, 0), 50);
            }

            for(Player player : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(plugin,executor);
            }
            plugin.vanishPlayers.add(executor);
        } else if(c.isSimilar(Items.DISABLE_VANISH)) {
            e.setCancelled(true);

            if(plugin.getConfigManager().SETTING_ENABLE_VANISH_COOLDOWN) {
                if(!plugin.getVanishCooldown().hasCooldown(executor.getUniqueId())) {
                    plugin.getVanishCooldown().addCooldown(executor.getUniqueId(),plugin.getConfigManager().SETTING_VANISH_COOLDOWN);
                } else {
                    executor.sendMessage(plugin.getConfigManager().MESSAGES_COOLDOWN.replaceAll("%time%",""+plugin.getVanishCooldown().getCooldown(executor.getUniqueId())));
                    return;
                }
            }

            executor.sendMessage(plugin.getConfigManager().MESSAGES_DISABLEVANISH);

            if(plugin.getConfigManager().SETTING_ENABLE_VANISH_PARTICLES) {
                executor.getWorld().spawnParticle(Particle.FLAME,executor.getLocation().add(0,1,0),50);
            }

            for(Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(plugin,executor);
            }
            plugin.vanishPlayers.remove(executor);

            executor.getInventory().setItem(4, Items.ENABLE_VANISH);
        }
    }
}
