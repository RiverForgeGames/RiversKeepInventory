package me.river.riversKeepInventory;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private FileConfiguration config;

    public ConfigManager() {
        reloadConfig();
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

    public void reloadConfig() {
        if (this.config != null)
        {
            this.config = null;
            RiversKeepInventory.getInstance().reloadConfig();
        }
        this.config = RiversKeepInventory.getInstance().getConfig(); // Reload the configuration file from disk
    }
}
