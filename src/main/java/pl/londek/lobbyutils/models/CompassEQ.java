package pl.londek.lobbyutils.models;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.londek.lobbyutils.LobbyUtils;
import pl.londek.lobbyutils.itembuilder.ItemBuilder;
import pl.londek.lobbyutils.utils.ChannelUtils;
import pl.londek.lobbyutils.utils.LogUtils;

import java.util.*;
import java.util.stream.IntStream;

public class CompassEQ {

    private Inventory base;

    //Itemstack | Teleports To
    private Map<ItemStack,String> teleportItems = new HashMap<>();

    public LobbyUtils plugin;

    public CompassEQ(String name, int size, LobbyUtils pluginInstance) {
        base = Bukkit.createInventory(null, size, LogUtils.fixColor(name));
        this.plugin = pluginInstance;
    }

    public void loadFromConfiguration(Configuration configuration) {

        Objects.requireNonNull(configuration,"Configuration cant be null");
        IntStream.range(0,10).forEach(i -> {});

        if(!configuration.getString("compassEQ.backgroundItem").equalsIgnoreCase("none")) {
            for(int i = 0; i < base.getSize(); i++) {
                ItemBuilder builder = new ItemBuilder(Material.getMaterial(configuration.getString("compassEQ.backgroundItem")));

                List<String> finalLore = new ArrayList<>();

                for(String loreItem : configuration.getStringList("compassEQ.backgroundItemLore")) {
                    finalLore.add(LogUtils.fixColor(loreItem));
                }

                builder
                        .displayname(LogUtils.fixColor(configuration.getString("compassEQ.backgroundItemName")))
                        .lore(finalLore);

                base.setItem(i,builder.build());
            }
        }

        for(String itemName : configuration.getConfigurationSection("compassEQ.teleportItems").getKeys(false)) {

            ConfigurationSection section = configuration.getConfigurationSection("compassEQ.teleportItems."+itemName);

            List<String> finalLore = new ArrayList<>();

            for(String loreItem : section.getStringList("lore")) {
                finalLore.add(LogUtils.fixColor(loreItem));
            }

            ItemBuilder builder = new ItemBuilder(Material.getMaterial(itemName))
                    .displayname(LogUtils.fixColor(section.getString("name")))
                    .lore(finalLore);

            if(!section.getString("enchantment").equalsIgnoreCase("none")) {
                builder.enchant(Enchantment.getByName(section.getString("enchantment")),section.getInt("enchantmentLevel"));
            }

            ItemStack finalItem = builder.build();

            teleportItems.put(finalItem,section.getString("teleports"));
            base.setItem(section.getInt("slot"),finalItem);
        }

        for(String itemName : configuration.getConfigurationSection("compassEQ.otherItems").getKeys(false)) {

            ConfigurationSection section = configuration.getConfigurationSection("compassEQ.otherItems."+itemName);

            List<String> finalLore = new ArrayList<>();

            for(String loreItem : section.getStringList("lore")) {
                finalLore.add(LogUtils.fixColor(loreItem));
            }

            ItemBuilder builder = new ItemBuilder(Material.getMaterial(itemName))
                    .displayname(LogUtils.fixColor(section.getString("name")))
                    .lore(finalLore);

            if(!section.getString("enchantment").equalsIgnoreCase("none")) {
                builder.enchant(Enchantment.getByName(section.getString("enchantment")),section.getInt("enchantmentLevel"));
            }

            ItemStack finalItem = builder.build();

            base.setItem(section.getInt("slot"),finalItem);
        }
    }

    public Inventory getBase() {
        return base;
    }

    public boolean searchForTeleportItem(ItemStack itemStack, Player toTeleport) {
        if(teleportItems.containsKey(itemStack)) {
            toTeleport.sendMessage(plugin.getConfigManager().MESSAGES_CONNECTING.replaceAll("%server%",teleportItems.get(itemStack)));
            plugin.getChannelUtils().sendToBungeeCord(toTeleport,"Connect",teleportItems.get(itemStack));
            return true;
        }
        return false;
    }
}
