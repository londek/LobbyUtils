package pl.londek.lobbyutils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.londek.lobbyutils.config.LobbyConfig;
import pl.londek.lobbyutils.cooldown.CooldownManager;
import pl.londek.lobbyutils.listeners.*;
import pl.londek.lobbyutils.utils.ChannelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LobbyUtils extends JavaPlugin {

    private static LobbyConfig config;
    private ChannelUtils channelUtils;

    private CooldownManager vanishCooldown;

    public List<Player> vanishPlayers = new ArrayList<>();
    public List<Player> vanishAnotherPlayers = new ArrayList<>();

    @Override
    public void onLoad() {
        getLogger().info("---------------------------------------");
        getLogger().info("-LobbyUtils-");
        getLogger().info(" By LonDek");
        getLogger().info("---------------------------------------");
        getLogger().info("Loading...");

        getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this,"BungeeCord",channelUtils = new ChannelUtils(this));
    }

    @Override
    public void onEnable() {
        loadListeners();

        vanishCooldown = new CooldownManager(this);
        vanishCooldown.runCooldownScheduler();

        config = new LobbyConfig(this);
        config.check();
        config.load();

        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("---------------------------------------");
        getLogger().info("-LobbyUtils-");
        getLogger().info(" By LonDek");
        getLogger().info("---------------------------------------");
    }

    public LobbyConfig getConfigManager() {
        return config;
    }

    public CooldownManager getVanishCooldown() {
        return vanishCooldown;
    }

    public ChannelUtils getChannelUtils() {
        Objects.requireNonNull(this.channelUtils,"Plugin not fully loaded but requested channelutils");
        return channelUtils;
    }

    public void loadListeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDestroyBlockListener(this),this);
        pluginManager.registerEvents(new PlayerHitListener(this), this);
        pluginManager.registerEvents(new PlayerPlaceBlockListener(this),this);
        pluginManager.registerEvents(new PlayerJoinListener(this),this);
        pluginManager.registerEvents(new PlayerInteractListener(this),this);
        pluginManager.registerEvents(new PlayerInventoryClickListener(this),this);
        pluginManager.registerEvents(new PlayerDropListener(this),this);
    }


}
