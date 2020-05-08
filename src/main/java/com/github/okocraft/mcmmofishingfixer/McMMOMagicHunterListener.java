package com.github.okocraft.mcmmofishingfixer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.gmail.nossr50.events.skills.fishing.McMMOPlayerMagicHunterEvent;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

class McMMOMagicHunterListener implements Listener {

    void start(McMMOFishingFixer plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    private void onMagicHunter(McMMOPlayerMagicHunterEvent event) {
        removeConflicting(event.getEnchantments());
    }

    private static void removeConflicting(Map<Enchantment, Integer> enchants) {
        enchants.keySet().removeAll(getConflicting(enchants));
    }

    private static Set<Enchantment> getConflicting(Map<Enchantment, Integer> enchants) {
        Set<Enchantment> conflicts = new HashSet<>();

        enchants.forEach((enchant, level) -> {
            // Skip enchants that is already collected.
            if (conflicts.contains(enchant)) {
                return;
            }

            Set<Enchantment> tempConflicts = new HashSet<>();

            // Collect conflicting Enchantment with enchant.
            for (Enchantment comparing : enchants.keySet()) {
                if (enchant != comparing && !conflicts.contains(comparing) && enchant.conflictsWith(comparing)) {
                    tempConflicts.add(comparing);
                }
            }
            if (tempConflicts.isEmpty()) {
                return;
            }
            tempConflicts.add(enchant);

            // Exclude strongest enchantment.
            double maxStrength = 0;
            Enchantment strongest = null;
            for (Enchantment conflicting : tempConflicts) {
                double strength = (double) enchants.get(conflicting) / (double) conflicting.getMaxLevel();
                if (maxStrength < strength) {
                    maxStrength = strength;
                    strongest = conflicting;
                }
            }
            if (maxStrength == 0 || strongest == null) {
                return;
            }
            tempConflicts.remove(strongest);

            // Collect finally.
            conflicts.addAll(tempConflicts);
        });

        return conflicts;
    }
}