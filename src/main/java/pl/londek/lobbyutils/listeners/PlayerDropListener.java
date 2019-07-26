package pl.londek.lobbyutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.londek.lobbyutils.LobbyUtils;

public class PlayerDropListener implements Listener {

    public LobbyUtils plugin;

    public PlayerDropListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!event.getPlayer().hasPermission(plugin.getConfigManager().PERMISSION_BYPASS_DROP)) {
            event.getPlayer().sendMessage(plugin.getConfigManager().MESSAGES_CANTDROP);
            event.setCancelled(true);
        }
    }
}
