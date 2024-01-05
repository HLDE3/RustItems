package ru.hld.rustitems.items.ammunition;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.awt.*;

public class BulletAmmo extends Ammunition {
    public void tryDamage(Bullet bullet) {
        for(LivingEntity entity : bullet.getWorld().getNearbyLivingEntities(bullet.getLocation(), .1f)) {
            if(entity.equals(bullet.getPlayer())) continue;
            entity.setNoDamageTicks(0);
            boolean head = bullet.getLocation().getY() > entity.getLocation().getY() + entity.getHeight() * .75;
            Sound sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
            float damage = bullet.getMixedDamage();
            if(head) {
                sound = Sound.ENTITY_PLAYER_ATTACK_CRIT;
                damage *= 1.3f;
            }
            bullet.getPlayer().playSound(bullet.getPlayer().getLocation(), sound, 1, 1);
            double oldHp = entity.getHealth();
            entity.damage(damage, bullet.getPlayer());
            bullet.getPlayer().sendTitle("", ChatColor.of(head ? new Color(150, 0, 0) : Color.WHITE) + "-" + (int) ((oldHp - entity.getHealth()) / entity.getMaxHealth() * 100), 1, 1, 10);
            bullet.setDamage(0);
        }

        if(bullet.getWorld().rayTraceBlocks(bullet.getLocation(), bullet.getLocation().toVector(), 1, FluidCollisionMode.NEVER) != null) {
            bullet.setDamage(bullet.getDamage() - bullet.getWorld().getBlockAt(bullet.getLocation()).getType().getHardness() * 2f);
            bullet.getPlayer().playSound(bullet.getLocation(), bullet.getWorld().getBlockAt(bullet.getLocation()).getSoundGroup().getBreakSound(), .5f, 1f);
        }
    }
}
