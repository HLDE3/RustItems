package ru.hld.rustitems.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.events.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtil implements Listener {
    private final Map<Player, Location> playerLocationMap = new HashMap<>();
    @EventHandler
    public void onTick(PlayerMoveEvent event) {
        playerLocationMap.put(event.getPlayer(), event.getFrom());
    }

    public Location getOldLocation(Player player) {
        return playerLocationMap.get(player);
    }

    public @NotNull Vector getMotion(Player player) {
        return player.getLocation().subtract(getOldLocation(player)).toVector();
    }
}
