package pl.londek.lobbyutils.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import pl.londek.lobbyutils.LobbyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerInventoryClickListener implements Listener {

    private List<InventoryAction> dropActions = new ArrayList<>();
    public LobbyUtils plugin;

    public PlayerInventoryClickListener(LobbyUtils pluginInstance) {
        dropActions.add(InventoryAction.DROP_ALL_CURSOR);
        dropActions.add(InventoryAction.DROP_ALL_SLOT);
        dropActions.add(InventoryAction.DROP_ONE_CURSOR);
        dropActions.add(InventoryAction.DROP_ONE_SLOT);
        dropActions.add(InventoryAction.CLONE_STACK);
        dropActions.add(InventoryAction.HOTBAR_MOVE_AND_READD);
        dropActions.add(InventoryAction.MOVE_TO_OTHER_INVENTORY);

        this.plugin = pluginInstance;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack c = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        if(!e.getWhoClicked().hasPermission(plugin.getConfigManager().PERMISSION_BYPASS_DROP) && Arrays.asList(InventoryAction.values()).contains(e.getAction())) {
            e.setCancelled(true);
        }

        if(e.getInventory().equals(plugin.getConfigManager().compassInventory.getBase())) {
            e.setCancelled(true);
            plugin.getConfigManager().compassInventory.searchForTeleportItem(c,p);
        }
    }
}
