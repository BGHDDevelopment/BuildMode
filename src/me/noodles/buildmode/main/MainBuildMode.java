package me.noodles.buildmode.main;

import me.noodles.buildmode.Logger;
import me.noodles.buildmode.MetricsLite;
import me.noodles.buildmode.Settings;
import org.bukkit.plugin.java.*;

import me.noodles.buildmode.Command;
import me.noodles.buildmode.events.Events;
import me.noodles.buildmode.updater.UpdateChecker;
import me.noodles.buildmode.updater.UpdateJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.*;

public class MainBuildMode extends JavaPlugin
{
    public static ArrayList<Player> playerlist;
    public static MainBuildMode plugin;
    private UpdateChecker checker;
    private static MainBuildMode instance;

    static {
        MainBuildMode.playerlist = new ArrayList<>();
    }

    public void onEnable() {
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Initializing BuildMode Version: " + Settings.VERSION);
        Logger.log(Logger.LogLevel.INFO, "Created by: " + Settings.DEVELOPER_NAME);
        Logger.log(Logger.LogLevel.INFO, "Website: " + Settings.DEVELOPER_URL);
        Logger.log(Logger.LogLevel.INFO, "Spigot Link: " + Settings.PLUGIN_URL);
        Logger.log(Logger.LogLevel.INFO, "Support Link: " + Settings.SUPPORT_DISCORD_URL);
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Plugin Loading...");
        Logger.log(Logger.LogLevel.INFO, "Registering Managers...");
        MainBuildMode.plugin = this;
        instance = this;
        MetricsLite metrics = new MetricsLite(this);
        Logger.log(Logger.LogLevel.INFO, "Managers Registered!");
        Logger.log(Logger.LogLevel.INFO, "Registering Listeners...");
        registerListener(
            new Events(this),
            new UpdateJoinEvent(this)
        );
        this.createFiles();
        Logger.log(Logger.LogLevel.INFO, "Listeners Registered!");
        Logger.log(Logger.LogLevel.INFO, "Registering Commands...");
        new Command();
        Logger.log(Logger.LogLevel.INFO, "Commands Registered!");
        Logger.log(Logger.LogLevel.INFO, "Loading Config's...");
        this.createFiles();
        Logger.log(Logger.LogLevel.INFO, "Config's Registered!");
        Logger.log(Logger.LogLevel.SUCCESS, "BuildMode Version: " + Settings.VERSION + " Loaded.");
        this.setEnabled(true);
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Checking for updates...");
        this.setEnabled(true);
        this.checker = new UpdateChecker(this);
        if (this.checker.isConnected()) {
            if (this.checker.hasUpdate()) {
                Logger.log(Logger.LogLevel.OUTLINE,  "********************");
                Logger.log(Logger.LogLevel.WARNING,("BuildMode is outdated!"));
                Logger.log(Logger.LogLevel.WARNING,("Newest version: " + this.checker.getLatestVersion()));
                Logger.log(Logger.LogLevel.WARNING,("Your version: " + Settings.VERSION));
                Logger.log(Logger.LogLevel.WARNING,("Please Update Here: " + Settings.PLUGIN_URL));
                Logger.log(Logger.LogLevel.OUTLINE,  "********************");
            }
            else {
                Logger.log(Logger.LogLevel.SUCCESS, "BuildMode is up to date!");
            }
        }
    }

    public static MainBuildMode getInstance() {
        return instance;
    }
	public static MainBuildMode getPlugin() {
        return (MainBuildMode)getPlugin((Class)MainBuildMode.class);
    }

    private void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> getServer().getPluginManager().registerEvents(l, this));
    }

    private File configf;
    private FileConfiguration config;

    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }  
        config = new YamlConfiguration();
        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}