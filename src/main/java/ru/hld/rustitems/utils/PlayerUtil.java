package ru.hld.rustitems.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.events.PlayerTickEvent;
import ru.hld.rustitems.items.RustItem;
import ru.hld.rustitems.menu.api.Menu;
import ru.hld.rustitems.menu.api.icons.ActionIcon;
import ru.hld.rustitems.menu.api.icons.Icon;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtil implements Listener {
    private final Map<Player, Location> playerLocationMap = new HashMap<>();
    @EventHandler
    public void onTick(PlayerMoveEvent event) {
        playerLocationMap.put(event.getPlayer(), event.getFrom());
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);

        Menu menu = new Menu("Menu", 4);
        int index = 0;
        for(RustItem rustItem : RustItems.getInstance().getRustItemManager().getRustItems()) {
            menu.addIcons(new ActionIcon(rustItem.create(), index) {
                @Override
                public boolean action(Player player, InventoryAction action) {
                    ItemStack itemStack = getItemStack().clone();
                    if(action.equals(InventoryAction.PICKUP_HALF))itemStack.setAmount(itemStack.getMaxStackSize());
                    player.getInventory().addItem(itemStack);
                    return false;
                }
            });
            index++;
        }
        menu.open(event.getPlayer());
    }

    public Location getOldLocation(Player player) {
        return playerLocationMap.get(player);
    }

    public @NotNull Vector getMotion(Player player) {
        return player.getLocation().subtract(getOldLocation(player)).toVector();
    }

}
