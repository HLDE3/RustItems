package ru.hld.rustitems.items.ammunition;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import ru.hld.rustitems.items.RustItem;

import java.util.ArrayList;

public class Ammunition extends RustItem {
    private final ArrayList<Bullet> entities = new ArrayList<>();

    public void shoot(Player player, float recoilX, float recoilY, float damage) {
        Location location = player.getLocation().clone().add(0, player.getEyeHeight(), 0).add(player.getLocation().getDirection());
        location.setYaw(location.getYaw() + recoilX);
        location.setPitch(location.getPitch() - recoilY);
        entities.add(new Bullet(player, location, damage));
    }

    @EventHandler
    public void onTick(ServerTickEndEvent event) {
        entities.removeIf(e -> {
            if(e.damage > 0) flyTick(e);
            return e.damage <= 0;
        });
    }

    public void flyTick(Bullet bullet) {
        for(float i = 0; i < bullet.getDamage() * 2; i++) {
            createFlyParticles(bullet);
            tryDamage(bullet);
            bullet.getLocation().add(bullet.getLocation().getDirection().multiply(.5));
        }
        bullet.getLocation().add(0, bullet.getVelocity(), 0);

        bullet.tick++;
    }

    public void tryDamage(Bullet bullet) {
        bullet.getPlayer().sendMessage(this + "");
    }

    public void createFlyParticles(Bullet bullet) {
    }

    public static class Bullet {
        private Location location;
        private float damage;
        private final Player player;
        public int tick;
        public Bullet(Player player, Location location, float damage) {
            this.damage = damage;
            this.location = location;
            this.player = player;
        }

        public float getDamage() {
            return damage;
        }

        public float getMixedDamage() {
            return Math.abs(getDamage() + getVelocity() / 10);
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

        public float getVelocity() {
            return (float) -tick / 10;
        }
    }
}
