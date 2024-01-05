package ru.hld.rustitems.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CoolDownedRustItem extends RustItem {

    private final float heldCoolDown;
    public CoolDownedRustItem(float heldCoolDown) {
        this.heldCoolDown = heldCoolDown;
    }

    public void coolDown(Player player, float sec) {
        int ticks = (int) (sec * 20);
        if(ticks > player.getCooldown(getMaterial())) player.setCooldown(getMaterial(), ticks);
    }

    public void heldOn(Player player) {
        super.heldOn(player);
        coolDown(player, getHeldCoolDown());
    }

    public void heldOff(Player player) {
        super.heldOff(player);
        player.setCooldown(getMaterial(), 0);
    }

    public float getHeldCoolDown() {
        return heldCoolDown;
    }
}
