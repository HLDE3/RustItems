package ru.hld.rustitems.items.weapons;

import com.sun.javafx.geom.Vec2f;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ru.hld.rustitems.events.RegisterRustItemsEvent;
import ru.hld.rustitems.items.RustItem;
import ru.hld.rustitems.items.ammunition.Ammunition;
import ru.hld.rustitems.items.ammunition.PistolAmmo;
import ru.hld.rustitems.items.ammunition.RiffleAmmo;

@RustItem.Item(material = Material.DIAMOND_PICKAXE, name = "Revolver", description = "Eazy gun :)")
public class Revolver extends Gun {
    public Revolver() {
        super(343, .5f, 3.4f, 8, 7);
    }

    @EventHandler
    public void onRegisterItems(RegisterRustItemsEvent.Post event) {
        this.addAmmunitionType((Ammunition) event.getRustItemManager().getRustItemByClassName(PistolAmmo.class.getName()));
    }

    public Vector getNextRecoil(ItemStack itemStack) {
        return new Vector(0f, 5f, 0);
    }
}
