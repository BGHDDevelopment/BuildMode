package me.noodles.buildmode.events;

import me.noodles.buildmode.main.MainBuildMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class Events implements Listener {

    private MainBuildMode main;
    List<String> worldsList = MainBuildMode.plugin.getConfig().getStringList("WorldsList");
    public Events(MainBuildMode main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceSurvival(BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakSurvival(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onArmourStandBreak(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            if (e.getEntityType().equals(EntityType.ARMOR_STAND)) {
                if (!p.getGameMode().name().equals("CREATIVE") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
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
                if (!p.getGameMode().name().equals("SURVIVAL") || MainBuildMode.playerlist.contains(p) || checkWorld(p)) {
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

    public Boolean checkWorld(Player p) {
        if (MainBuildMode.getInstance().getConfig().getBoolean("blacklistedWorlds") == true) {
            if (worldsList.contains(p.getWorld().getName())) {
                return false;
            } else {
                return true;
            }
        } else if (MainBuildMode.getInstance().getConfig().getBoolean("blacklistedWorlds") == false) {
                if (worldsList.contains(p.getWorld().getName())) {
                    return true;
                } else {
                    return false;
                }
            }
        return false;
        }

    /*public Boolean checkPerm(Player p) {
        if (MainBuildMode.getInstance().getConfig().getBoolean("effectOnlyPerm") == true) {
            if (p.hasPermission("buildmode.nobuild")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }*/
}
