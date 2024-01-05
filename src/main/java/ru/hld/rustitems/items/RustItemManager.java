package ru.hld.rustitems.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.events.RegisterRustItemsEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class RustItemManager {
    private final ArrayList<RustItem> rustItems = new ArrayList<>();
    public RustItemManager() {
        registerItems();
    }

    public void registerItems() {
        RustItems.getInstance().getServer().getPluginManager().callEvent(new RegisterRustItemsEvent.Pre(this));
        rustItems.clear();
        try {
            for(RustItem.Category category : RustItem.Category.values()) {
                String path = this.getClass().getPackage().getName() + "." + category.toString().toLowerCase().replace("_", "");
                Set<Class<? extends RustItem>> reflections = new Reflections(path).getSubTypesOf(RustItem.class);

                for (Class<? extends RustItem> reflection : reflections) {
                    if (reflection.isAnnotationPresent(RustItem.Item.class)) {
                        RustItem i = reflection.newInstance();
                        i.register(i.getClass().getAnnotation(RustItem.Item.class));
                        i.setCategory(category);
                        rustItems.add(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        rustItems.forEach(f -> RustItems.getInstance().getServer().getPluginManager().registerEvents(f, RustItems.getInstance()));
        RustItems.getInstance().getServer().getPluginManager().callEvent(new RegisterRustItemsEvent.Post(this));
    }

    public ArrayList<RustItem> getRustItems() {
        return rustItems;
    }

    public RustItem getRustItemByClassName(String className) {
        return getRustItems().stream().filter(rustItem -> rustItem.getClass().getName().equals(className)).findFirst().orElse(null);
    }

    public RustItem getRustItem(String name) {
        return getRustItems().stream().filter(rustItem -> rustItem.equals(name)).findFirst().orElse(null);
    }

    public RustItem getRustItem(ItemStack itemStack) {
        return itemStack != null ? getRustItem(itemStack.getItemMeta().getDisplayName()) : null;
    }

    public boolean isRustItem(ItemStack itemStack) {
        return getRustItem(itemStack) != null;
    }
}
