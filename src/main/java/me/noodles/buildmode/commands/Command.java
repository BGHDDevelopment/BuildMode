package me.noodles.buildmode.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import me.noodles.buildmode.main.MainBuildMode;

import org.bukkit.*;

public class Command implements CommandExecutor
{

    private MainBuildMode main;


    public Command() {
        main = MainBuildMode.getInstance();
        main.getCommand("build").setExecutor(this);
    }

    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("build")) {
            return false;
        }
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("InvalidUsage")));
                return true;
            }
            final Player p = (Player)sender;
            if (!p.hasPermission("buildmode.toggle") || !p.hasPermission("buildmode.toggle")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("NoPermission")));
                return true;
            }
            if (MainBuildMode.playerlist.contains(p)) {
                MainBuildMode.playerlist.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("Disabled")));
                return true;
            }
            MainBuildMode.playerlist.add(p);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("Enabled")));
            return true;
        }
        else {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("InvalidUsage")));
                return true;
            }
            if (!sender.hasPermission("buildmode.others") || !sender.hasPermission("buildmode.others")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("NoPermission")));
                return true;
            }
            final Player p = Bukkit.getPlayerExact(args[0]);
            if (p == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("OfflinePlayer")));
                return true;
            }
            if (MainBuildMode.playerlist.contains(p)) {
                MainBuildMode.playerlist.remove(p);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("DisabledOther").replace("%player%", p.getName())));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("Disabled")));
                return true;
            }
            MainBuildMode.playerlist.add(p);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("EnabledOther").replace("%player%", p.getName())));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', MainBuildMode.plugin.getConfig().getString("Enabled")));
            return true;
        }
    }
}
