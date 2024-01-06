package ru.hld.rustitems.items.ammunition;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.hld.rustitems.items.RustItem;

import java.util.ArrayList;

public class Ammunition extends RustItem {
    private final ArrayList<Bullet> entities = new ArrayList<>();

    public Ammunition(float velocity) {
        this.addValue("velocity", velocity);
    }

    public void shoot(Player player, float recoilX, float recoilY, float damage, float velocity) {
        Location location = player.getLocation().clone().add(0, player.getEyeHeight(), 0).add(player.getLocation().getDirection());
        location.setYaw(location.getYaw() + recoilX);
        location.setPitch(location.getPitch() - recoilY);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, .1f, 4f);
        entities.add(new Bullet(player, location, damage, velocity));
    }

    @EventHandler
    public void onTick(ServerTickEndEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                entities.removeIf(e -> {
                    if (e.damage > 0) flyTick(e);
                    return e.damage <= 0;
                });
            }
        }.run();
    }

    public void flyTick(Bullet bullet) {
        for(float i = 0; i < bullet.getDamage() * 2; i++) {
            createFlyParticles(bullet);
            tryDamage(bullet);
            bullet.getLocation().add(bullet.getLocation().getDirection().multiply(.5f));
            bullet.getLocation().add(0, -bullet.getGravity(), 0);
        }

        bullet.tick++;
    }

    public void tryDamage(Bullet bullet) {

    }

    public void createFlyParticles(Bullet bullet) {
    }

    public float getVelocity(ItemStack itemStack) {
        return (float) getValue(itemStack, "velocity");
    }

    public ItemStack setVelocity(ItemStack itemStack, float set) {
       return setValue(itemStack, "velocity", set);
    }

    public static class Bullet {
        private Location location;
        private float damage, velocity;
        private final Player player;
        public int tick;
        public Bullet(Player player, Location location, float damage, float velocity) {
            this.damage = damage;
            this.location = location;
            this.player = player;
            this.velocity = velocity;
        }

        public float getDamage() {
            return damage;
        }

        public float getMixedDamage() {
            return Math.abs(getDamage() + getGravity() / 10);
        }

        public void setDamage(float damage) {
            this.damage = damage;
        }

        public World getWorld() {
            return location.getWorld();
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Player getPlayer() {
            return player;
        }

        public float getGravity() {
            return (float) tick * getVelocity() * getVelocity() / 20000;
        }

        public void setVelocity(float velocity) {
            this.velocity = velocity;
        }

        public float getVelocity() {
            return velocity;
        }
    }
}
