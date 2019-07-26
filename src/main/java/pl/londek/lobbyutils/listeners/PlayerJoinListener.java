package pl.londek.lobbyutils.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import pl.londek.lobbyutils.LobbyUtils;
import pl.londek.lobbyutils.constants.Items;

public class PlayerJoinListener implements Listener {

    public LobbyUtils plugin;

    public PlayerJoinListener(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        PlayerInventory playerInventory = playerJoinEvent.getPlayer().getInventory();

        for(Player p : plugin.vanishPlayers) {
            playerJoinEvent.getPlayer().hidePlayer(plugin,p);
        }
        for(Player p : plugin.vanishAnotherPlayers) {
            p.hidePlayer(plugin,playerJoinEvent.getPlayer());
        }

        playerInventory.clear();
        playerInventory.setItem(0, Items.COMPASS);
        playerInventory.setItem(2, Items.ENABLE_FLY);
        playerInventory.setItem(3, Items.ENABLE_HIDEPLAYERS);
        playerInventory.setItem(4, Items.ENABLE_VANISH);
    }
}
