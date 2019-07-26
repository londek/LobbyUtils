package pl.londek.lobbyutils.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.meta.ItemMeta;
import pl.londek.lobbyutils.LobbyUtils;
import pl.londek.lobbyutils.constants.Items;
import pl.londek.lobbyutils.models.CompassEQ;
import pl.londek.lobbyutils.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LobbyConfig {

    private LobbyUtils pluginInstance;
    private Logger logger;
    private YamlConfiguration configuration;

    private File directory;
    private File configurationFile;

    //Props:
    public boolean SETTING_ENABLE_VANISH_COOLDOWN = false;
    public int SETTING_VANISH_COOLDOWN = 0;

    public boolean SETTING_ENABLE_VANISH_PARTICLES = false;

    public String PERMISSION_BYPASS_DESTROY = "lobbyutils.bypass.destroy";
    public String PERMISSION_BYPASS_PLACING = "lobbyutils.bypass.place";
    public String PERMISSION_BYPASS_COMPLETELY_HIT = "lobbyutils.bypass.completely_hit";
    public String PERMISSION_BYPASS_DROP = "lobbyutils.bypass.drop";

    public String PERMISSION_PREMIUM_FLY = "lobbyutils.premium.fly";
    public String PERMISSION_PREMIUM_HIT = "lobbyutils.premium.hit";
    public String PERMISSION_PREMIUM_VANISH = "lobbyutils.premium.vanish";
    public String PERMISSION_PREMIUM_HIDEPLAYERS = "lobbyutils.premium.hideplayers";
    public String PERMISSION_PREMIUM_SIT = "lobbyutils.premium.sit";

    public String MESSAGES_CONNECTING = "none, null, undefined, unknown, in brief, try to contact with administrator! [connecting message]";
    public String MESSAGES_NO_PERMISSION = "none, null, undefined, unknown, in brief, try to contact with administrator! [no_permission message]";
    public String MESSAGES_COOLDOWN = "none, null, undefined, unknown, in brief, try to contact with administrator! [cooldown message]";

    public String MESSAGES_CANTDESTROY = "none, null, undefined, unknown, in brief, try to contact with administrator! [cantdestroy message]";
    public String MESSAGES_CANTPLACE = "none, null, undefined, unknown, in brief, try to contact with administrator! [cantplace message]";
    public String MESSAGES_CANTDROP = "none, null, undefined, unknown, in brief, try to contact with administrator! [cantdrop message]";
    public String MESSAGES_CANTHIT = "none, null, undefined, unknown, in brief, try to contact with administrator! [canthit message]";

    public String MESSAGES_ENABLEHIDEPLAYERS = "none, null, undefined, unknown, in brief, try to contact with administrator! [enablehideplayers message]";
    public String MESSAGES_DISABLEHIDEPLAYERS = "none, null, undefined, unknown, in brief, try to contact with administrator! [disablehideplayers message]";

    public String MESSAGES_ENABLEVANISH = "none, null, undefined, unknown, in brief, try to contact with administrator! [enablevanish enable]";
    public String MESSAGES_DISABLEVANISH = "none, null, undefined, unknown, in brief, try to contact with administrator! [disablevanish message]";

    public CompassEQ compassInventory;

    public LobbyConfig(LobbyUtils instance) {
        this.pluginInstance = instance;
        this.logger = pluginInstance.getLogger();
        this.directory = pluginInstance.getDataFolder();
        this.configurationFile = new File(pluginInstance.getDataFolder(),"config.yml");
    }

    public void check() {
        logger.info("Checking config files!");
        if(!directory.exists()) {
            logger.warning("Creating plugin folder!");
            directory.mkdirs();
        }
        if(!configurationFile.exists()) {
            logger.warning("Creating plugin config file!");
            try {
                directory.createNewFile();
                pluginInstance.saveDefaultConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("Checked config files!");
    }

    public void load() {
        logger.info("Loading config!!");

        configuration = configuration.loadConfiguration(configurationFile);

        PERMISSION_BYPASS_DESTROY = configuration.getString("permission_bypass.destroy").equalsIgnoreCase("default") ? PERMISSION_BYPASS_DESTROY : configuration.getString("permission_bypass.destroy");
        PERMISSION_BYPASS_PLACING = configuration.getString("permission_bypass.place").equalsIgnoreCase("default") ? PERMISSION_BYPASS_PLACING : configuration.getString("permission_bypass.place");
        PERMISSION_BYPASS_COMPLETELY_HIT = configuration.getString("permission_bypass.completely_hit").equalsIgnoreCase("default") ? PERMISSION_BYPASS_COMPLETELY_HIT : configuration.getString("permission_bypass.completely_hit");
        PERMISSION_BYPASS_DROP = configuration.getString("permission_bypass.drop").equalsIgnoreCase("default") ? PERMISSION_BYPASS_DROP : configuration.getString("permission_bypass.drop");

        PERMISSION_PREMIUM_FLY = configuration.getString("permission_premium.fly").equalsIgnoreCase("default") ? PERMISSION_PREMIUM_FLY : configuration.getString("permission_premium.fly");
        PERMISSION_PREMIUM_HIT = configuration.getString("permission_premium.hit").equalsIgnoreCase("default") ? PERMISSION_PREMIUM_HIT : configuration.getString("permission_premium.hit");
        PERMISSION_PREMIUM_VANISH = configuration.getString("permission_premium.vanish").equalsIgnoreCase("default") ? PERMISSION_PREMIUM_VANISH : configuration.getString("permission_premium.vsnish");
        PERMISSION_PREMIUM_HIDEPLAYERS = configuration.getString("permission_premium.hideplayers").equalsIgnoreCase("default") ? PERMISSION_PREMIUM_HIDEPLAYERS : configuration.getString("permission_premium.hideplayers");
        PERMISSION_PREMIUM_SIT = configuration.getString("permission_premium.sit").equalsIgnoreCase("default") ? PERMISSION_PREMIUM_SIT : configuration.getString("permission_premium.sit");

        loadMessages();
        loadSettings();

        compassInventory = new CompassEQ(configuration.getString("compassEQ.name"),configuration.getInt("compassEQ.size"),pluginInstance);
        compassInventory.loadFromConfiguration(configuration);

        ItemMeta compassMeta = Items.COMPASS.getItemMeta();
        compassMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.compass.name")));
        List<String> compassLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.compass.lore")) {
            compassLore.add(LogUtils.fixColor(s));
        }
        compassMeta.setLore(compassLore);
        Items.COMPASS.setItemMeta(compassMeta);

        ItemMeta enableFlyMeta = Items.ENABLE_FLY.getItemMeta();
        enableFlyMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.enablefly.name")));
        List<String> enableFlyLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.enablefly.lore")) {
            enableFlyLore.add(LogUtils.fixColor(s));
        }
        enableFlyMeta.setLore(enableFlyLore);
        Items.ENABLE_FLY.setItemMeta(enableFlyMeta);

        ItemMeta disableFlyMeta = Items.DISABLE_FLY.getItemMeta();
        disableFlyMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.disablefly.name")));
        List<String> disableFlyLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.disablefly.lore")) {
            disableFlyLore.add(LogUtils.fixColor(s));
        }
        disableFlyMeta.setLore(disableFlyLore);
        Items.DISABLE_FLY.setItemMeta(disableFlyMeta);

        ItemMeta enableHidePlayersMeta = Items.ENABLE_HIDEPLAYERS.getItemMeta();
        enableHidePlayersMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.enablehideplayers.name")));
        List<String> enableHidePlayersLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.enablehideplayers.lore")) {
            enableHidePlayersLore.add(LogUtils.fixColor(s));
        }
        enableHidePlayersMeta.setLore(enableHidePlayersLore);
        Items.ENABLE_HIDEPLAYERS.setItemMeta(enableHidePlayersMeta);

        ItemMeta disableHidePlayersMeta = Items.DISABLE_HIDEPLAYERS.getItemMeta();
        disableHidePlayersMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.disablehideplayers.name")));
        List<String> disableHidePlayersLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.disablehideplayers.lore")) {
            disableHidePlayersLore.add(LogUtils.fixColor(s));
        }
        disableHidePlayersMeta.setLore(disableHidePlayersLore);
        Items.DISABLE_HIDEPLAYERS.setItemMeta(disableHidePlayersMeta);

        ItemMeta enableVanishMeta = Items.ENABLE_VANISH.getItemMeta();
        enableVanishMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.enablevanish.name")));
        List<String> enableVanishLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.enablevanish.lore")) {
            enableVanishLore.add(LogUtils.fixColor(s));
        }
        enableVanishMeta.setLore(enableVanishLore);
        Items.ENABLE_VANISH.setItemMeta(enableVanishMeta);

        ItemMeta disableVanishMeta = Items.DISABLE_VANISH.getItemMeta();
        disableVanishMeta.setDisplayName(LogUtils.fixColor(configuration.getString("items.disablevanish.name")));
        List<String> disableVanishLore = new ArrayList<>();
        for(String s : configuration.getStringList("items.disablevanish.lore")) {
            disableVanishLore.add(LogUtils.fixColor(s));
        }
        disableVanishMeta.setLore(disableVanishLore);
        Items.DISABLE_VANISH.setItemMeta(disableVanishMeta);

        logger.info("Config loaded!");
    }

    private void loadMessages() {
        ConfigurationSection messages = configuration.getConfigurationSection("messages");

        MESSAGES_CONNECTING = LogUtils.fixColor(messages.getString("connecting"));
        MESSAGES_NO_PERMISSION = LogUtils.fixColor(messages.getString("no_permission"));
        MESSAGES_COOLDOWN = LogUtils.fixColor(messages.getString("cooldown"));

        MESSAGES_CANTDESTROY = LogUtils.fixColor(messages.getString("cantdestroy"));
        MESSAGES_CANTPLACE = LogUtils.fixColor(messages.getString("cantplace"));
        MESSAGES_CANTDROP = LogUtils.fixColor(messages.getString("cantdrop"));
        MESSAGES_CANTHIT = LogUtils.fixColor(messages.getString("canthit"));

        MESSAGES_ENABLEHIDEPLAYERS = LogUtils.fixColor(messages.getString("enablehideplayers"));
        MESSAGES_DISABLEHIDEPLAYERS = LogUtils.fixColor(messages.getString("disablehideplayers"));

        MESSAGES_ENABLEVANISH = LogUtils.fixColor(messages.getString("enablevanish"));
        MESSAGES_DISABLEVANISH = LogUtils.fixColor(messages.getString("disablevanish"));
    }

    private void loadSettings() {
        SETTING_ENABLE_VANISH_COOLDOWN = configuration.getBoolean("enableVanishCooldown");
        SETTING_VANISH_COOLDOWN = configuration.getInt("vanishCooldown");
        SETTING_ENABLE_VANISH_PARTICLES = configuration.getBoolean("enableVanishParticles");
    }
}
