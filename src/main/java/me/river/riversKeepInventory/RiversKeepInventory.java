package me.river.riversKeepInventory;

import org.bukkit.plugin.java.JavaPlugin;

public final class RiversKeepInventory extends JavaPlugin {
    private static RiversKeepInventory instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();  // Save config if not present
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static RiversKeepInventory getInstance() {
        return instance;
    }
}
