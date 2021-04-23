package me.noodles.buildmode.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.noodles.buildmode.main.MainBuildMode;
import org.bukkit.entity.Player;

public class checkRegion {

    public static boolean checkRegion(Player p) {
        if (MainBuildMode.getInstance().getConfig().getBoolean("WorldGuardAPISupport")) {
            Location loc = BukkitAdapter.adapt(p.getLocation());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(loc);
            com.sk89q.worldguard.LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
            if (set.testState(localPlayer, Flags.BLOCK_BREAK)) {
                return true;
            }
        }
        return false;
    }

}
