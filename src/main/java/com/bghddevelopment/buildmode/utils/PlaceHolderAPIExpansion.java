package com.bghddevelopment.buildmode.utils;

import com.bghddevelopment.buildmode.BuildMode;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "BuildModeBGHD";
    }

    @Override
    public @NotNull String getAuthor() {
        return "BGHDDevelopment";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("status")) {
            if (BuildMode.playerlist.contains(player)) {
                return "enabled";
            } else if (!BuildMode.playerlist.contains(player)) {
                return "disabled";
            }
        }
        return null;
    }

    public Map<String, Object> getDefaults() {
        return null;
    }


}
