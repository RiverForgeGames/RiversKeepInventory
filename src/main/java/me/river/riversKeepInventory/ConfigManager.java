package me.river.keepinventorytweaks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager() {
        this.config = KeepInventoryTweaks.getInstance().getConfig();
    }

    public boolean keepHotbar() {
        return config.getBoolean("keep-hotbar", true);
    }

    public boolean keepInventory() {
        return config.getBoolean("keep-inventory", false);
    }

    public boolean keepArmor() {
        return config.getBoolean("keep-armor", true);
    }

    public boolean keepXP() {
        return config.getBoolean("keep-xp", false);
    }

    public void reloadConfig(Plugin plugin) {
        plugin.reloadConfig(); // Reload the configuration file from disk
    }
}
