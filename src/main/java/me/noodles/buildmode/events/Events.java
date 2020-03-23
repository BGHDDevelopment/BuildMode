package me.noodles.buildmode.events;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;

import me.noodles.buildmode.main.MainBuildMode;

public class Events implements Listener
{

    private MainBuildMode main;

    public Events (MainBuildMode main) {
        this.main = main;
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p)) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlaceSurvival(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p)) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p)) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockBreakSurvival(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p)) {
            return;
        }
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (MainBuildMode.playerlist.contains(p)) {
            MainBuildMode.playerlist.remove(p);
        }
    }
}
