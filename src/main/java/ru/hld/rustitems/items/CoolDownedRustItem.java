package ru.hld.rustitems.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.hld.rustitems.RustItems;

import java.util.ArrayList;

public class CoolDownedRustItem extends RustItem {

    private final float heldCoolDown;
    public CoolDownedRustItem(float heldCoolDown) {
        this.heldCoolDown = heldCoolDown;
    }

    public void coolDown(Player player, float sec, boolean ignoreLast) {
        int ticks = (int) (sec * 20);
        if(ticks > player.getCooldown(getMaterial()) || ignoreLast) player.setCooldown(getMaterial(), ticks);
    }

    public void heldOn(Player player) {
        super.heldOn(player);
        coolDown(player, getHeldCoolDown(), true);
    }

    public void heldOff(Player player) {
        super.heldOff(player);
        if(!RustItems.getInstance().getRustItemManager().isRustItem(player.getInventory().getItemInMainHand())) coolDown(player, 0, true);
    }

    public float getHeldCoolDown() {
        return heldCoolDown;
    }
}
