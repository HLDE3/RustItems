package ru.hld.rustitems.items.weapons;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ru.hld.rustitems.events.RegisterRustItemsEvent;
import ru.hld.rustitems.items.RustItem;
import ru.hld.rustitems.items.ammunition.Ammunition;
import ru.hld.rustitems.items.ammunition.PistolAmmo;
import ru.hld.rustitems.items.ammunition.RiffleAmmo;
import ru.hld.rustitems.utils.MathUtils;

@RustItem.Item(material = Material.DIAMOND_PICKAXE, name = "Assault riffle")
public class AssaultRiffle extends Gun {
    public AssaultRiffle() {
        super(450, 1f, 4.4f, 30, 10);
    }

    @EventHandler
    public void onRegisterItems(RegisterRustItemsEvent.Post event) {
        this.addAmmunitionType((Ammunition) event.getRustItemManager().getRustItemByClassName(RiffleAmmo.class.getName()));
    }

    public Vector getNextRecoil(ItemStack itemStack) {
        return new Vector(3f + MathUtils.random(-1.5f, 1.5f), 5f, 0);
    }
}
