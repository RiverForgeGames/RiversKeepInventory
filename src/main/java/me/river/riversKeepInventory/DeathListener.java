package me.river.riversKeepInventory;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;

public class DeathListener implements Listener {
    private final ConfigManager configManager;

    public DeathListener(RiversKeepInventory plugin) {
        this.configManager = new ConfigManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        HumanEntity player = event.getEntity();
        boolean keepInventoryRule = Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY));

        Bukkit.getLogger().info("[KeepInventoryTweaks] Player " + player.getName() + " died.");
        Bukkit.getLogger().info("[KeepInventoryTweaks] keepInventory rule: " + keepInventoryRule);

        if (keepInventoryRule) {
            Bukkit.getLogger().info("[KeepInventoryTweaks] keepInventory is TRUE, skipping plugin logic.");
            return; // Do nothing if keepInventory is true
        }

        // Reload the config so we get the latest values
        configManager.reloadConfig();

        // Log current config values
        boolean keepHotbar = configManager.keepHotbar();
        boolean keepInventory = configManager.keepInventory();
        boolean keepArmor = configManager.keepArmor();
        boolean keepXP = configManager.keepXP();

        Bukkit.getLogger().info("[KeepInventoryTweaks] Config values:");
        Bukkit.getLogger().info("[KeepInventoryTweaks] - keep-hotbar: " + keepHotbar);
        Bukkit.getLogger().info("[KeepInventoryTweaks] - keep-inventory: " + keepInventory);
        Bukkit.getLogger().info("[KeepInventoryTweaks] - keep-armor: " + keepArmor);
        Bukkit.getLogger().info("[KeepInventoryTweaks] - keep-xp: " + keepXP);

        Bukkit.getLogger().info("[KeepInventoryTweaks] Processing death event for " + player.getName());

        // Handle XP
        if (keepXP) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
            Bukkit.getLogger().info("[KeepInventoryTweaks] XP is being kept.");
        } else {
            Bukkit.getLogger().info("[KeepInventoryTweaks] XP is NOT being kept.");
        }

        // Handle dropping armor
        if (!keepArmor)
        {
            player.dropItem(EquipmentSlot.HEAD);
            player.dropItem(EquipmentSlot.CHEST);
            player.dropItem(EquipmentSlot.LEGS);
            player.dropItem(EquipmentSlot.FEET);
            player.dropItem(EquipmentSlot.OFF_HAND);
            Bukkit.getLogger().info("[KeepInventoryTweaks] Equipment is NOT being kept.");
        }
        else {
            Bukkit.getLogger().info("[KeepInventoryTweaks] Equipment is being kept.");
        }

        // Handle dropping the hotbar
        if (!keepHotbar)
        {
            for (int i = 0; i < 9; i++)
            {
                player.dropItem(i);
            }
            Bukkit.getLogger().info("[KeepInventoryTweaks] Hotbar is NOT being kept.");
        }
        else
        {
            Bukkit.getLogger().info("[KeepInventoryTweaks] Hotbar is being kept.");
        }

        // Handle dropping the regular inventory (not hotbar or equipment)
        if (!keepInventory)
        {
            for (int i = 9; i < 36; i++)
            {
                player.dropItem(i);
            }
            Bukkit.getLogger().info("[KeepInventoryTweaks] Main inventory is NOT being kept.");
        }
        else
        {
            Bukkit.getLogger().info("[KeepInventoryTweaks] Main inventory is being kept.");
        }

        // Don't drop everything, only the things we manually decided to drop
        event.setKeepInventory(true);
    }
}
