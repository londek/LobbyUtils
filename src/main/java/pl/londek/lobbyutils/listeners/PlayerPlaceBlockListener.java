package pl.londek.lobbyutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.londek.lobbyutils.LobbyUtils;

public class PlayerPlaceBlockListener implements Listener {

    public LobbyUtils plugin;

    public PlayerPlaceBlockListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        if(!event.getPlayer().hasPermission(plugin.getConfigManager().PERMISSION_BYPASS_PLACING)) {
            event.getPlayer().sendMessage(plugin.getConfigManager().MESSAGES_CANTPLACE);
            event.setCancelled(true);
        }
    }
}
