package ru.hld.rustitems.items;

import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.events.PlayerTickEvent;
import ru.hld.rustitems.events.RustItemEvent;
import ru.hld.rustitems.utils.TextUtil;
import ru.hld.rustitems.utils.Utils;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public abstract class RustItem implements Listener {
    private String name, description;
    private Material material;
    private Category category;
    private final Map<String, Object> values = Maps.newHashMap();
    private final Map<Player, Integer> holdersItem = Maps.newHashMap();

    public void register(Item item) {
        this.name = item.name();
        this.description = item.description();
        this.material = item.material();
    }

    @EventHandler
    public void onTick(PlayerTickEvent.Pre event) {
        RustItems.getInstance().getServer().getPluginManager().callEvent(new RustItemEvent.Tick.Pre(this, event.getPlayer()));
        tick(event.getPlayer());
        RustItems.getInstance().getServer().getPluginManager().callEvent(new RustItemEvent.Tick.Post(this, event.getPlayer()));
    }

    public void tick(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        boolean anotherSlot =  isHeldItem(player) && holdersItem.get(player) != player.getInventory().getHeldItemSlot();
        if(!equals(itemStack) || anotherSlot) {
            if(isHeldItem(player)) heldOff(player);
        } else {
            if(!isHeldItem(player)) heldOn(player);
        }
    }
    public void heldOn(Player player) {
        holdersItem.put(player, player.getInventory().getHeldItemSlot());
    }

    public void heldOff(Player player) {
        holdersItem.remove(player);
    }

    public ItemStack create() {
        return create(1);
    }

    public ItemStack create(int count) {
        return create(getLore(), count);
    }
    public ItemStack create(ArrayList<String> lore, int count) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(getDisplayName());
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        itemStack.setAmount(count);

        return itemStack;
    }
    public void addValue(String name, Object value) {
        this.values.put(name, value);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private String getValueString(ArrayList<String> lore, String valueName) {
        String result = null;
        for (String val : lore) {
            if(!val.contains(": ")) continue;
            if(TextUtil.removeColorCodes(val).split(": ")[0].equals(valueName)) result = val;
        }
        return result;
    }

    public String valueToStringFormat(String name, Object value) {
        return ChatColor.of(new Color(0x018A4C)) + name + ": " + ChatColor.of(new Color(-1)) + value;
    }

    public Object getValue(ItemStack itemStack, String valueName) {
        Object result = null;
        String valueString = getValueString(new ArrayList<>(Objects.requireNonNull(itemStack.getItemMeta().getLore())), valueName);
        if(valueString != null) {
            String[] split = TextUtil.removeColorCodes(valueString).split(": ");
            if (valueName.equalsIgnoreCase(split[0])) result = Utils.convertString(split[1]);
        }
        return result;
    }

    public ItemStack setValue(ItemStack itemStackIn, String valueName, Object value) {
        ItemStack itemStack = new ItemStack(itemStackIn);

        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(meta.getLore());

        String valueString = getValueString(new ArrayList<>(Objects.requireNonNull(lore)), valueName);

        if(valueString != null) {
            lore.set(lore.indexOf(valueString), valueToStringFormat(valueName, value));
        } else {
            lore.add(valueToStringFormat(valueName, value));
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        values.keySet().forEach(v -> lore.add(valueToStringFormat(v, values.get(v))));
        return lore;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    public String getDisplayName() {
        return ChatColor.of(new Color(-1)) + getName();
    }

    public Material getMaterial() {
        return material;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isHeldItem(Player player) {
        return holdersItem.containsKey(player);
    }

    public boolean equals(String name) {
        return getName().equalsIgnoreCase(name) || getDisplayName().equalsIgnoreCase(name);
    }
    public boolean equals(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null && equals(itemStack.getItemMeta().getDisplayName());
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Item {
        Material material() default Material.POPPED_CHORUS_FRUIT;
        String name() default "Unknown Item";
        String description() default "Rust item";
    }

    public static enum Category {
        WEAPONS,
        CONSTRUCTION,
        ITEMS,
        RESOURCES,
        CLOTHING,
        TOOLS,
        MEDICALS,
        FOOD,
        AMMUNITION,
        TRAPS,
        MISC,
        COMPONENTS,
        FESTIVE_ITEMS,
        OBSOLETE_ITEMS,
        UNRELEASED_ITEMS
    }
}