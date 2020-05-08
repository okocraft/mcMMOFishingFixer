package com.github.okocraft.mcmmofishingfixer;

import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingEvent;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerMagicHunterEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.elsiff.morefish.MoreFish;

class MoreFishListener implements Listener {
    
    void start(McMMOFishingFixer plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    private void onMcMMOFishOnFishContest(McMMOPlayerFishingEvent event) {
        if (MoreFish.getInstance().getContestManager().hasStarted()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onMcMMOFishOnFishContest(McMMOPlayerMagicHunterEvent event) {
        if (MoreFish.getInstance().getContestManager().hasStarted()) {
            event.setCancelled(true);
        }
    }
}