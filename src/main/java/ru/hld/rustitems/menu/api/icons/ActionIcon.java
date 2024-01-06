package ru.hld.rustitems.menu.api.icons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class ActionIcon extends Icon {
    public ActionIcon(ItemStack itemStack, int slot) {
        super(itemStack, slot);
    }

    public ActionIcon(ItemStack itemStack, int x, int y) {
        super(itemStack, x, y);
    }

    public ActionIcon(Material material, String name, ArrayList<String> lore, int x, int y) {
        super(material, name, lore, x, y);
    }

    public abstract boolean action(Player player, InventoryAction action);
}
