package pl.londek.lobbyutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.londek.lobbyutils.LobbyUtils;

public class PlayerDestroyBlockListener implements Listener {

    public LobbyUtils plugin;

    public PlayerDestroyBlockListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!event.getPlayer().hasPermission(plugin.getConfigManager().PERMISSION_BYPASS_DESTROY)) {
            event.getPlayer().sendMessage(plugin.getConfigManager().MESSAGES_CANTDESTROY);
            event.setCancelled(true);
        }
    }
}
