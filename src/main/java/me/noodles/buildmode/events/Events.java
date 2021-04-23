package me.noodles.buildmode.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.noodles.buildmode.utils.checkRegion;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.*;

import me.noodles.buildmode.main.MainBuildMode;

import java.util.List;
import java.util.Set;

public class Events implements Listener {

    private MainBuildMode main;
    List<String> disabledWorlds = MainBuildMode.plugin.getConfig().getStringList("DisabledWorlds");
    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    public Events(MainBuildMode main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName()) || checkRegion.checkRegion(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceSurvival(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakSurvival(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onArmourStandBreak(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            if (e.getEntityType().equals(EntityType.ARMOR_STAND)) {
                if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName())) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmourStandBreakSurvival(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            if (e.getEntityType().equals(EntityType.ARMOR_STAND)) {
                if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || disabledWorlds.contains(p.getWorld().getName())) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (MainBuildMode.playerlist.contains(p)) {
            MainBuildMode.playerlist.remove(p);
        }
    }
}
