package ru.hld.rustitems.menu.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class Menu {
    private final int size;
    private final String title;
    public Menu(String title, int height) {
        this.size = height * 9;
        this.title = title;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new InventoryHolder() {
            @Override
            public @NotNull Inventory getInventory() {
                return Bukkit.createInventory(null, getSize());
            }
        }, getSize());
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }
}
