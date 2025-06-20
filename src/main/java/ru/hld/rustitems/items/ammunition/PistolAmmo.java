package ru.hld.rustitems.items.ammunition;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import ru.hld.rustitems.items.RustItem;

@RustItem.Item(material = Material.IRON_NUGGET, name = "Pistol ammo")
public class PistolAmmo extends BulletAmmo {
    public PistolAmmo() {
        super(7.46f);
    }

    public void createFlyParticles(Bullet bullet) {
        bullet.getWorld().spawnParticle(Particle.CRIT_MAGIC, bullet.getLocation(), 1, 0, 0, 0, 0);
    }
}
