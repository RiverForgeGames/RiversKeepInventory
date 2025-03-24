package me.river.keepinventorytweaks;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class DeathListener implements Listener {
    private final ConfigManager configManager;

    public DeathListener(KeepInventoryTweaks plugin) {
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
        configManager.reloadConfig(event.getEntity().getServer().getPluginManager().getPlugin("KeepInventoryTweaks"));

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


        Iterator<ItemStack> iterator = event.getDrops().iterator();

        while (iterator.hasNext()) {
            ItemStack item = iterator.next();

            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            boolean isHotbar = isInHotbar(player, item);
            boolean isArmor = isInArmorSlot(player, item);

            Bukkit.getLogger().info("[KeepInventoryTweaks] Checking item: " + item.getType());
            Bukkit.getLogger().info("[KeepInventoryTweaks] - Is Hotbar: " + isHotbar);
            Bukkit.getLogger().info("[KeepInventoryTweaks] - Is Armor Slot: " + isArmor);

            if ((keepHotbar && isHotbar) ||
                    (keepArmor && isArmor) ||
                    (keepInventory && !isHotbar && !isArmor)) {
                iterator.remove();
                Bukkit.getLogger().info("[KeepInventoryTweaks] -> Keeping item: " + item.getType());
            } else {
                Bukkit.getLogger().info("[KeepInventoryTweaks] -> Dropping item: " + item.getType());
            }
        }

        // Don't drop everything, only the things we manually decided to drop
        event.setKeepInventory(true);

        if (!keepArmor)
        {
            player.dropItem(EquipmentSlot.HEAD);
        }
    }

    private boolean isInHotbar(Player player, ItemStack item) {
        for (int i = 0; i < 9; i++) {
            if (item.equals(player.getInventory().getItem(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isInArmorSlot(Player player, ItemStack item) {
        return item.equals(player.getInventory().getItem(EquipmentSlot.HEAD)) ||
                item.equals(player.getInventory().getItem(EquipmentSlot.CHEST)) ||
                item.equals(player.getInventory().getItem(EquipmentSlot.LEGS)) ||
                item.equals(player.getInventory().getItem(EquipmentSlot.FEET)) ||
                item.equals(player.getInventory().getItem(EquipmentSlot.OFF_HAND));
    }
}
