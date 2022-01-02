package me.noodles.buildmode.main;

import me.noodles.buildmode.commands.Command;
import me.noodles.buildmode.events.Events;
import me.noodles.buildmode.updater.UpdateChecker;
import me.noodles.buildmode.updater.UpdateJoinEvent;
import me.noodles.buildmode.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        Color.log("&7********************");
        Color.log("&eInitializing BuildMode Version: " + getDescription().getVersion());
        Color.log("&eCreated by: " + getDescription().getAuthors());
        Color.log("&eWebsite: " + getDescription().getWebsite());
        Color.log("&eSpigot Link: " + "https://spigotmc.org/resources/39103");
        Color.log("&eSupport Link: " + "https://bghddevelopment.com/discord");
        Color.log("&7********************");
        Color.log("&ePlugin Loading...");
        Color.log("&eRegistering Managers...");
        MainBuildMode.plugin = this;
        instance = this;
        MetricsLite metrics = new MetricsLite(this);
        Color.log("&eManagers Registered!");
        Color.log("&eRegistering Listeners...");
        registerListener(
            new Events(this),
            new UpdateJoinEvent(this)
        );
        this.createFiles();
        Color.log("&eListeners Registered!");
        Color.log("&eRegistering Commands...");
        new Command();
        Color.log("&eCommands Registered!");
        Color.log("&eLoading Config's...");
        this.createFiles();
        Color.log("&eConfig's Registered!");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Color.log("&eRegistering Placeholders...");
            new PlaceHolderAPIExpansion().register();
            Color.log("&ePlaceholders Registered!");
        }
        Color.log("&eBuildMode Version: " + getDescription().getVersion() + " Loaded.");
        this.setEnabled(true);
        Color.log("&7********************");
        Color.log("&eChecking for updates...");
        this.setEnabled(true);
        new UpdateChecker(this, 39103).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Color.log("&aBuildMode is up to date!");
            } else {
                Color.log("&7*********************************************************************");
                Color.log("&cBuildMode is outdated!");
                Color.log("&cNewest version: " + version);
                Color.log("&cYour version: &c&l" + getDescription().getVersion());
                Color.log("&6Please Update Here: &6&o" + "https://spigotmc.org/resources/39103");
                Color.log("&7*********************************************************************");			}
        });
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