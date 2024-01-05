package ru.hld.rustitems.menu.api.icons;

import org.bukkit.Material;
import ru.hld.rustitems.menu.api.icons.Icon;

import java.util.ArrayList;

public class StaticIcon extends Icon {

    public StaticIcon(Material material, String name, ArrayList<String> lore, int x, int y) {
        super(material, name, lore, x, y);
    }
}
