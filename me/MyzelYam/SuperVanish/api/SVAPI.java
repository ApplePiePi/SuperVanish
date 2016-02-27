package me.MyzelYam.SuperVanish.api;

import de.myzelyam.supervanish.SVUtils;
import de.myzelyam.supervanish.SuperVanish;
import de.myzelyam.supervanish.config.SettingsFile;
import de.myzelyam.supervanish.config.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Deprecated
public class SVAPI {

    private static SuperVanish plugin;

    static {
        Plugin bplugin = Bukkit.getPluginManager().getPlugin("SuperVanish");
        if (bplugin == null || !(bplugin instanceof SuperVanish)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[SuperVanish] A plugin will fail to use the api, since SuperVanish isn't loaded!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[SuperVanish] The author should add SuperVanish as a (soft-)dependency to the plugin.yml file");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[SuperVanish] to make sure SuperVanish is loaded when trying to use the api!");
            throw new RuntimeException("API is unavailable!");
        }
        plugin = (SuperVanish) bplugin;
    }

    /**
     * @return A Stringlist of the UUID's of all hidden players
     */
    public static List<String> getInvisiblePlayers() {
        return plugin.playerData.getStringList("InvisiblePlayers");
    }

    /**
     * @param p - the player.
     * @return TRUE if the player is invisible, FALSE otherwise.
     */
    public static boolean isInvisible(Player p) {
        if (p == null)
            return false;
        return plugin.playerData.getStringList("InvisiblePlayers")
                .contains(p.getUniqueId().toString());
    }

    /**
     * Hides a player using SuperVanish
     *
     * @param p - the player.
     */
    public static void hidePlayer(Player p) {
        new SVUtils().hidePlayer(p);
    }

    /**
     * * Shows a player using SuperVanish
     *
     * @param p - the player.
     */
    public static void showPlayer(Player p) {
        new SVUtils().showPlayer(p);
    }

    public static FileConfiguration getConfiguration() {
        return plugin.settings;
    }

    public static FileConfiguration getMessages() {
        return plugin.messages;
    }

    public static FileConfiguration getPlayerData() {
        return plugin.playerData;
    }

    public static void reloadConfig() {
        // messages
        plugin.messagesFile = new MessagesFile();
        plugin.messagesFile.saveDefaultConfig();
        plugin.messages = plugin.messagesFile.getConfig();
        // config
        plugin.settingsFile = new SettingsFile();
        plugin.settingsFile.saveDefaultConfig();
        plugin.settings = plugin.settingsFile.getConfig();
    }
}