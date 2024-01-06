package ru.hld.rustitems.menu.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.menu.api.icons.ActionIcon;
import ru.hld.rustitems.menu.api.icons.Icon;
import ru.hld.rustitems.menu.api.icons.StaticIcon;

import java.util.ArrayList;

public class Menu implements Listener {
    private final int size;
    private final String title;
    private final ArrayList<Icon> icons = new ArrayList<>();
    private final Inventory inventory;
    private Player player;
    public Menu(String title, int height) {
        this.size = height * 9;
        this.title = title;
        Bukkit.getPluginManager().registerEvents(this, RustItems.getInstance());
        inventory = Bukkit.createInventory(new InventoryHolder() {
            @Override
            public @NotNull Inventory getInventory() {
                return Bukkit.createInventory(null, getSize());
            }
        }, getSize(), getTitle());
    }

    public void open(Player player) {
        this.player = player;

        icons.forEach(i -> inventory.setItem(i.getSlot(), i.getItemStack()));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onAction(InventoryClickEvent event) {
        if(event.getInventory().equals(getInventory())) {
            icons.forEach(i -> {
                if(i.getSlot() == event.getSlot()) {
                    if (i instanceof ActionIcon) {
                        ActionIcon actionIcon = (ActionIcon) i;
                        if (!actionIcon.action(getPlayer(), event.getAction())) event.setCancelled(true);
                    } else if (i instanceof StaticIcon) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }

            });
        }
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Icon> getIcons() {
        return icons;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Menu addIcons(Icon... icons) {
        for (Icon icon : icons) {
            getIcons().add(icon);
        }
        return this;
    }

    public Player getPlayer() {
        return player;
    }
}
