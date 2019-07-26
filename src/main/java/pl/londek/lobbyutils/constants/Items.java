package pl.londek.lobbyutils.constants;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.londek.lobbyutils.itembuilder.ItemBuilder;
import pl.londek.lobbyutils.utils.LogUtils;

public class Items {

    public final static ItemStack COMPASS;

    public final static ItemStack ENABLE_FLY;
    public final static ItemStack DISABLE_FLY;

    public final static ItemStack ENABLE_HIDEPLAYERS;
    public final static ItemStack DISABLE_HIDEPLAYERS;

    public final static ItemStack ENABLE_VANISH;
    public final static ItemStack DISABLE_VANISH;

    static {
        COMPASS = new ItemBuilder(Material.COMPASS).displayname(LogUtils.fixColor("&8UNKNOWN COMPASS ITEM NAME")).lore("&8UNKNOWN COMPASS ITEM LORE").build();

        ENABLE_FLY = new ItemBuilder(Material.FEATHER).displayname(LogUtils.fixColor("&8UNKNOWN ENABLEFLY ITEM NAME")).lore("&8UNKNOWN ENABLEFLY ITEM LORE").build();
        DISABLE_FLY = new ItemBuilder(Material.FEATHER).displayname(LogUtils.fixColor("&8UNKNOWN DISABLEFLY ITEM NAME")).lore("&8UNKNOWN DISABLEFLY ITEM LORE").build();

        ENABLE_HIDEPLAYERS = new ItemBuilder(Material.INK_SACK).durability(DyeColor.LIME.getDyeData()).displayname(LogUtils.fixColor("&8UNKNOWN ENABLEHIDEPLAYERSFLY ITEM NAME")).lore("&8UNKNOWN ENABLEHIDEPLAYERSFLY ITEM LORE").build();
        DISABLE_HIDEPLAYERS = new ItemBuilder(Material.INK_SACK).durability(DyeColor.RED.getDyeData()).displayname(LogUtils.fixColor("&8UNKNOWN DISABLEHIDEPLAYERSFLY ITEM NAME")).lore("&8UNKNOWN DISABLEHIDEPLAYERSFLY ITEM LORE").build();

        ENABLE_VANISH = new ItemBuilder(Material.EYE_OF_ENDER).displayname(LogUtils.fixColor("&8UNKNOWN ENABLEVANISH ITEM NAME")).lore("&8UNKNOWN ENABLEVANISH ITEM LORE").build();
        DISABLE_VANISH = new ItemBuilder(Material.ENDER_PEARL).displayname(LogUtils.fixColor("&8UNKNOWN DISABLEVANISH ITEM NAME")).lore("&8UNKNOWN DISABLEVANISH ITEM LORE").build();
    }
}
