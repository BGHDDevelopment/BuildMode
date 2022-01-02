package me.noodles.buildmode.updater;

import me.noodles.buildmode.utils.Color;
import me.noodles.buildmode.utils.Settings;
import org.bukkit.event.player.*;

import me.noodles.buildmode.main.MainBuildMode;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import sun.applet.Main;

public class UpdateJoinEvent implements Listener
{

    private MainBuildMode plugin;
    public UpdateChecker checker;

    public UpdateJoinEvent(MainBuildMode plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (MainBuildMode.plugin.getConfig().getBoolean("Update.Enabled") == true) {
            if (p.hasPermission("buildmode.update")) {
                new UpdateChecker(plugin, 39103).getLatestVersion(version -> {
                    if (!MainBuildMode.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                        p.sendMessage(Color.translate("&7****************************************************************"));
                        p.sendMessage(Color.translate("&cBuildMode is outdated!"));
                        p.sendMessage(Color.translate("&cNewest version: " + version));
                        p.sendMessage(Color.translate("&cYour version: " + "&c&l" + MainBuildMode.getInstance().getDescription().getVersion()));
                        p.sendMessage(Color.translate("&6Please Update Here: " + "&6&ohttps://spigotmc.org/resources/39103"));
                        p.sendMessage(Color.translate("&7****************************************************************"));                    }
                });
            }
        }
    }


    @EventHandler
    public void onDevJoin(PlayerJoinEvent e) { //THIS EVENT IS USED FOR DEBUG REASONS ONLY!
        Player p = e.getPlayer();
        if (p.getName().equals("Noodles_YT")) {
            p.sendMessage(ChatColor.RED + "BGHDDevelopment Debug Message");
            p.sendMessage(" ");
            p.sendMessage(ChatColor.GREEN + "This server is using " + MainBuildMode.getInstance().getDescription().getName() + " version " + MainBuildMode.getInstance().getDescription().getVersion());
            p.sendMessage(" ");
        }
    }

}
    