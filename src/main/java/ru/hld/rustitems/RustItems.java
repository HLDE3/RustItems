package ru.hld.rustitems;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.hld.rustitems.events.PlayerTickEvent;
import ru.hld.rustitems.items.RustItem;
import ru.hld.rustitems.items.RustItemManager;
import ru.hld.rustitems.utils.PlayerUtil;
import ru.hld.rustitems.utils.TextUtil;

public final class RustItems extends JavaPlugin implements Listener {
    private static RustItems instance;
    private RustItemManager rustItemManager;
    private PlayerUtil playerUtil;
    @Override
    public void onEnable() {
        instance = this;
        rustItemManager = new RustItemManager();
        getServer().getPluginManager().registerEvents(getInstance(), getInstance());
        getServer().getPluginManager().registerEvents(playerUtil = new PlayerUtil(), getInstance());
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onTick(ServerTickEndEvent e) {
        for(Player player : getServer().getOnlinePlayers()) {
            getInstance().getServer().getPluginManager().callEvent(new PlayerTickEvent.Post(player));
        }
    }
    @EventHandler
    public void onTick(ServerTickStartEvent e) {
        for(Player player : getServer().getOnlinePlayers()) {
            getInstance().getServer().getPluginManager().callEvent(new PlayerTickEvent.Pre(player));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
    }

    public static RustItems getInstance() {
        return instance;
    }

    public RustItemManager getRustItemManager() {
        return rustItemManager;
    }

    public PlayerUtil getPlayerUtil() {
        return playerUtil;
    }
}
