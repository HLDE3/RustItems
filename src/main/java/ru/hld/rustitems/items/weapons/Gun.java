package ru.hld.rustitems.items.weapons;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.google.common.collect.Maps;
import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec2f;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.RustItems;
import ru.hld.rustitems.items.ammunition.Ammunition;

import java.util.ArrayList;
import java.util.Map;

public class Gun extends Weapon {
    private float reloadingTime;
    private final ArrayList<Ammunition> ammunitionTypes = new ArrayList<>();
    private final ArrayList<Player> reloadingPlayers = new ArrayList<>();
    private final Map<Player, Vector> playerRecoils = Maps.newHashMap();

    public Gun(float attackRate, float heldCoolDown, float reloadingTime, int maxAmmo, float damage) {
        super(heldCoolDown, attackRate);
        this.reloadingTime = reloadingTime;
        addValue("max ammo", maxAmmo);
        addValue("ammo", maxAmmo);
        addValue("damage", damage * 5);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if(equals(itemStack) && !equals(event.getPlayer().getInventory().getItemInMainHand()) && isHeldItem(event.getPlayer())) {
            event.setCancelled(true);
            if(!isReloading(event.getPlayer()) && getAmmo(itemStack) < getMaxAmmo(itemStack)) reload(itemStack, event.getPlayer());
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if(isHeldItem(event.getPlayer())) coolDown(event.getPlayer(), .5f, false);
    }

    @Override
    public boolean onAttack(@NotNull ItemStack itemStack, Player player) {
        int ammo = getAmmo(itemStack);
        if(ammo <= 0) return false;
        shoot(itemStack, player);
        return true;
    }

    public void shoot(ItemStack itemStack, Player player) {
        int ammo = getAmmo(itemStack);

        Vector recoil = getRecoil(player);
        Vector recoilModifier = getNextRecoil(itemStack).multiply(player.isSneaking() ? .75 : 1).multiply(player.isSprinting() ? 1.25f : 1);

        player.getInventory().setItemInMainHand(setAmmo(itemStack, ammo - 1));
        Ammunition ammunition = getCurrentAmmunition(itemStack);
        ammunition.shoot(player, (float) recoil.getX(), (float) recoil.getY(), getDamage(itemStack), ammunition.getVelocity(ammunition.create()));

        recoil.add(recoilModifier);
    }


    public void reload(ItemStack itemStack, Player player) {
        coolDown(player, getReloadingTime(), false);
        reloadingPlayers.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(isReloading(player)) {
                    ItemStack item = itemStack;
                    Ammunition ammunition = getCurrentAmmunition(item);
                    for(int i = getAmmo(item); i < getMaxAmmo(item); i++) {
                        ItemStack first = player.getInventory().getItem(player.getInventory().first(ammunition.getMaterial()));
                        if(first == null || !ammunition.equals(first)) break;
                        first.setAmount(first.getAmount() - 1);
                        player.getInventory().setItemInMainHand(item = setAmmo(item, getAmmo(item) + 1));
                    }
                    reloadingPlayers.remove(player);
                }
            }
        }.runTaskLater(RustItems.getInstance(), (long) (getReloadingTime() * 20));
    }

    public void tick(Player player) {
        super.tick(player);

        getRecoil(player).multiply(isAttacking(player) ? .95 : .85);

        if(isHeldItem(player) && (player.getFallDistance() > 1
                || player.getWorld().getBlockAt(player.getLocation().add(0, player.getEyeHeight(), 0)).isLiquid()
                || player.getWorld().getBlockAt(player.getLocation()).isLiquid() && !player.isOnGround())) coolDown(player, .1f, false);
    }
    public void heldOff(Player player) {
        super.heldOff(player);
        reloadingPlayers.remove(player);
    }

    public int getAmmo(ItemStack itemStack) {
        return (int) getValue(itemStack, "ammo");
    }

    public ItemStack setAmmo(ItemStack itemStack, int set) {
        return setValue(itemStack, "ammo", set);
    }

    public Ammunition getCurrentAmmunition(ItemStack itemStack) {
        return (Ammunition) RustItems.getInstance().getRustItemManager().getRustItem(String.valueOf(getValue(itemStack, "ammunition")));
    }

    public int getMaxAmmo(ItemStack itemStack) {
        return (int) getValue(itemStack, "max ammo");
    }

    public ItemStack setMaxAmmo(ItemStack itemStack, int set) {
        return setValue(itemStack, "max ammo", set);
    }

    public float getDamage(ItemStack itemStack) {
        return (float) getValue(itemStack, "damage") / 5;
    }

    public ItemStack setDamage(ItemStack itemStack, int set) {
        return setValue(itemStack, "damage", set * 5);
    }

    public float getReloadingTime() {
        return reloadingTime;
    }

    public boolean isReloading(Player player) {
        return reloadingPlayers.contains(player);
    }

    public void setReloadingTime(float reloadingTime) {
        this.reloadingTime = reloadingTime;
    }

    public ArrayList<Ammunition> getAmmunitionTypes() {
        return ammunitionTypes;
    }

    public void addAmmunitionType(Ammunition ammunition) {
        addValue("ammunition", ammunition.getName());
        ammunitionTypes.add(ammunition);
    }

    public Vector getNextRecoil(ItemStack itemStack) {
        return new Vector(0, 1, 0);
    }

    public Vector getRecoil(Player player) {
        if(!playerRecoils.containsKey(player)) playerRecoils.put(player, new Vector(0, 0, 0));
        return playerRecoils.get(player);
    }
}
