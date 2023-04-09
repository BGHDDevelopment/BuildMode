package com.bghddevelopment.buildmode.events;

import com.bghddevelopment.buildmode.BuildMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class Events implements Listener {

    private List<String> worldsList = BuildMode.getInstance().getConfig().getStringList("WorldsList");

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceSurvival(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakSurvival(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onArmourStandBreak(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            if (e.getEntityType().equals(EntityType.ARMOR_STAND)) {
                if (!p.getGameMode().name().equals("CREATIVE") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
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
                if (!p.getGameMode().name().equals("SURVIVAL") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBukkitPlace(PlayerBucketEmptyEvent e) {
        final Player p = e.getPlayer();

        if (!p.getGameMode().name().equals("CREATIVE") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBukkitPlaceSurvival(PlayerBucketEmptyEvent e) {
        final Player p = e.getPlayer();

        if (!p.getGameMode().name().equals("SURVIVAL") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBukkitFill(PlayerBucketFillEvent e) {
        final Player p = e.getPlayer();

        if (!p.getGameMode().name().equals("CREATIVE") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBukkitFillSurvival(PlayerBucketFillEvent e) {
        final Player p = e.getPlayer();

        if (!p.getGameMode().name().equals("SURVIVAL") || BuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (BuildMode.playerlist.contains(p)) {
            BuildMode.playerlist.remove(p);
        }
    }

    private Boolean checkWorld(Player p) {
        if (BuildMode.getInstance().getConfig().getBoolean("blacklistedWorlds")) {
            if (worldsList.contains(p.getWorld().getName())) {
                return false;
            } else {
                return true;
            }
        } else if (!BuildMode.getInstance().getConfig().getBoolean("blacklistedWorlds")) {
            if (worldsList.contains(p.getWorld().getName())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
