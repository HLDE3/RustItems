package ru.hld.rustitems.items.ammunition;

import org.bukkit.Material;
import org.bukkit.Particle;
import ru.hld.rustitems.items.RustItem;

@RustItem.Item(material = Material.GOLD_NUGGET, name = "Riffle ammo")
public class RiffleAmmo extends BulletAmmo {
    public RiffleAmmo() {
        super(10.3f);
    }

    public void createFlyParticles(Bullet bullet) {
        bullet.getWorld().spawnParticle(Particle.CRIT, bullet.getLocation(), 1, 0, 0, 0, 0);
    }
}