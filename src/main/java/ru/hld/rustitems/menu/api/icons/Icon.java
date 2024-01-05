package ru.hld.rustitems.menu.api.icons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class Icon {
    private int x, y;
    private ItemStack itemStack;

    public Icon(ItemStack itemStack, int x, int y) {
        this.itemStack = itemStack;
        setX(x);
        setY(y);
    }

    public Icon(Material material, String name, ArrayList<String> lore, int x, int y) {
        this(new ItemStack(material), x, y);
        setName(name);
        setLore(lore);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSlot() {
        return getX() + getY() * 9;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ArrayList<String> getLore() {
        return new ArrayList<String>(Objects.requireNonNull(itemStack.getLore()));
    }

    public void setLore(ArrayList<String> lore) {
        this.itemStack.setLore(lore);
    }

    public String getName() {
        return itemStack.getItemMeta().getDisplayName();
    }

    public void setName(String name) {
        this.itemStack.getItemMeta().setDisplayName(name);
    }

    public Material getMaterial() {
        return this.itemStack.getType();
    }

    public void setMaterial(Material material) {
        this.itemStack.setType(material);
    }
}
