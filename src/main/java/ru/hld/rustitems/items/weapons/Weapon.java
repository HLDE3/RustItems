package ru.hld.rustitems.items.weapons;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.items.CoolDownedRustItem;

public abstract class Weapon extends CoolDownedRustItem {
    private final float attackRate;
    public Weapon(float heldCoolDown, float attackRate) {
        super(heldCoolDown);
        this.attackRate = attackRate;
    }

    public abstract boolean onAttack(@NotNull ItemStack itemInMainHand, Player player);

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getPlayer().hasCooldown(getMaterial()) && isHeldItem(event.getPlayer())) {
            if(onAttack(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer())) {
                coolDown(event.getPlayer(), getAttackCoolDown());
                event.setCancelled(true);
            }
        }
    }

    public float getAttackCoolDown() {
        return 1 / (getAttackRate() / 60);
    }
    public float getAttackRate() {
        return attackRate;
    }
}
