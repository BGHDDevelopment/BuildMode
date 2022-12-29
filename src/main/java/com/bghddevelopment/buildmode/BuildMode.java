package com.bghddevelopment.buildmode;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import com.bghddevelopment.buildmode.commands.BuildModeCommand;
import com.bghddevelopment.buildmode.events.Events;
import com.bghddevelopment.buildmode.utils.Color;
import com.bghddevelopment.buildmode.utils.Metrics;
import com.bghddevelopment.buildmode.utils.PlaceHolderAPIExpansion;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class BuildMode extends JavaPlugin
{
    public static ArrayList<Player> playerlist;
    @Getter
    private static BuildMode instance;

    static {
        BuildMode.playerlist = new ArrayList<>();
    }

    public void onEnable() {
        instance = this;
        Color.log("&eInitializing BuildMode Version: " + getDescription().getVersion());
        new Metrics(this,3303);
        Color.log("&eRegistering Listeners...");
        getServer().getPluginManager().registerEvents(new Events(), this);
        this.createFiles();
        Color.log("&eListeners Registered!");
        Color.log("&eRegistering Commands...");
        loadCommands();
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
        updateCheck(Bukkit.getConsoleSender(), true);
        this.setEnabled(true);
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
    private void loadCommands() {
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.getCommandConditions().addCondition("noconsole", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Console cannot use this command."); //replace with config language
            }
        });
        manager.registerCommand(new BuildModeCommand());
    }

    public void updateCheck(CommandSender sender, boolean console) {
        try {
            String urlString = "https://updatecheck.bghddevelopment.com";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            JsonObject object = new JsonParser().parse(response.toString()).getAsJsonObject();

            if (object.has("plugins")) {
                JsonObject plugins = object.get("plugins").getAsJsonObject();
                JsonObject info = plugins.get("BuildMode").getAsJsonObject();
                String version = info.get("version").getAsString();
                Boolean archived = info.get("archived").getAsBoolean();
                if(archived) {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cThis plugin has been marked as 'Archived' by BGHDDevelopment LLC."));
                    sender.sendMessage(Color.translate("&cThis version will continue to work but will not receive updates or support."));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    return;
                }
                if (version.equals(getDescription().getVersion())) {
                    if (console) {
                        sender.sendMessage(Color.translate("&aBuildMode is on the latest version."));
                    }
                } else {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour BuildMode version is out of date!"));
                    sender.sendMessage(Color.translate("&cWe recommend updating ASAP!"));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour Version: &e" + getDescription().getVersion()));
                    sender.sendMessage(Color.translate("&aNewest Version: &e" + version));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                }
            } else {
                sender.sendMessage(Color.translate("&cWrong response from update API, contact plugin developer!"));
            }
        } catch (
                Exception ex) {
            sender.sendMessage(Color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }
}