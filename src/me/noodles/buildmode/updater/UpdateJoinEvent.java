package me.noodles.buildmode.updater;

import me.noodles.buildmode.Settings;
import org.bukkit.event.player.*;

import me.noodles.buildmode.main.MainBuildMode;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class UpdateJoinEvent implements Listener
{

    private MainBuildMode main;
    public UpdateChecker checker;

    public UpdateJoinEvent(MainBuildMode main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	if (p.hasPermission("buildmode.update")) {
    		if (MainBuildMode.getPlugin().getConfig().getBoolean("Update.Enabled") == true){
    		this.checker = new UpdateChecker(MainBuildMode.plugin);
                        if (this.checker.isConnected()) {
                            if (this.checker.hasUpdate()) {
                            	p.sendMessage(ChatColor.GRAY + "=========================");
                                p.sendMessage(ChatColor.RED + "BuildMode is outdated!");
                                p.sendMessage(ChatColor.GREEN + "Newest version: " + this.checker.getLatestVersion());
                                p.sendMessage(ChatColor.RED + "Your version: " + Settings.VERSION);
                                p.sendMessage(ChatColor.GRAY + "=========================");
                            }
                        }               
       }
    }
}
}
    