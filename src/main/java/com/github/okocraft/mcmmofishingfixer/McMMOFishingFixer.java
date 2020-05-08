package com.github.okocraft.mcmmofishingfixer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class McMMOFishingFixer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new McMMOMagicHunterListener().start(this);

        if (Bukkit.getPluginManager().isPluginEnabled("MoreFish")) {
            new MoreFishListener().start(this);
        }

        getLogger().info(getName() + " have been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(getName() + " have been disabled!");
    }
}
