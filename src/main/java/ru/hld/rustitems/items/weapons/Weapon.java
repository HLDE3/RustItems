package ru.hld.rustitems.items.weapons;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.items.CoolDownedRustItem;

import java.util.Map;

public abstract class Weapon extends CoolDownedRustItem {
    private final float attackRate;
    private final Map<Player, Integer> playerAttackTicks = Maps.newHashMap();
    public Weapon(float heldCoolDown, float attackRate) {
        super(heldCoolDown);
        this.attackRate = attackRate;
        this.addValue("attack rate", attackRate);
    }

    public abstract boolean onAttack(@NotNull ItemStack itemInMainHand, Player player);

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getPlayer().hasCooldown(getMaterial()) && isHeldItem(event.getPlayer())) {
            if(onAttack(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer())) {
                playerAttackTicks.put(event.getPlayer(), Bukkit.getCurrentTick());
                coolDown(event.getPlayer(), getAttackCoolDown(), false);
                event.setCancelled(true);
            }
        }
    }

    public boolean isAttacking(Player player) {
        return playerAttackTicks.containsKey(player) && Bukkit.getCurrentTick() - playerAttackTicks.get(player) < (getAttackCoolDown() + .75) * 20;
    }
    public float getAttackCoolDown() {
        return 1 / (getAttackRate() / 60);
    }
    public float getAttackRate() {
        return attackRate;
    }
}
