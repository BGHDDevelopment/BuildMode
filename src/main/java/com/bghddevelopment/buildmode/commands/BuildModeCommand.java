package com.bghddevelopment.buildmode.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.bghddevelopment.buildmode.BuildMode;
import com.bghddevelopment.buildmode.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@CommandAlias("buildmode|buildmode")
@Description("Main BuildMode command.")
@CommandPermission("buildmode.toggle")
@Conditions("noconsole")
public class BuildModeCommand extends BaseCommand {

    @Dependency
    private BuildMode plugin;

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (BuildMode.playerlist.contains(player)) {
                BuildMode.playerlist.remove(player);
                player.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("Disabled")));
                Bukkit.broadcast(Color.translate(BuildMode.getInstance().getConfig().getString("DisabledNotification")
                        .replace("%player%", player.getName())), "buildmode.notify");
                return;
            }
            BuildMode.playerlist.add(player);
            player.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("Enabled")));
            Bukkit.broadcast(Color.translate(BuildMode.getInstance().getConfig().getString("EnabledNotification")
                    .replace("%player%", player.getName())), "buildmode.notify");
        } else {
            if (args.length != 1) {
                sender.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("InvalidUsage")));
                return;
            }
            if (!sender.hasPermission("buildmode.others")) {
                return;
            }
            final Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("OfflinePlayer")));
                return;
            }
            if (BuildMode.playerlist.contains(target)) {
                BuildMode.playerlist.remove(target);
                sender.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("DisabledOther").replace("%player%", player.getName())));
                player.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("Disabled")));
                Bukkit.broadcast(Color.translate(BuildMode.getInstance().getConfig().getString("DisabledNotification")
                        .replace("%player%", player.getName())), "buildmode.notify");
                return;
            }
            BuildMode.playerlist.add(target);
            sender.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("EnabledOther").replace("%player%", player.getName())));
            player.sendMessage(Color.translate(BuildMode.getInstance().getConfig().getString("Enabled")));
            Bukkit.broadcast(Color.translate(BuildMode.getInstance().getConfig().getString("EnabledNotification")
                    .replace("%player%", player.getName())), "buildmode.notify");
        }
    }
}
