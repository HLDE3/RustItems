package ru.hld.rustitems.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.items.RustItem;

public class RustItemEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RustItem rustItem;
    public RustItemEvent(RustItem rustItem) {
        this.rustItem = rustItem;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RustItem getRustItem() {
        return rustItem;
    }

    public static class Tick extends RustItemEvent {
        private final Player player;
        public Tick(RustItem rustItem, Player player) {
            super(rustItem);
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public static class Pre extends Tick {

            public Pre(RustItem rustItem, Player player) {
                super(rustItem, player);
            }
        }

        public static class Post extends Tick {

            public Post(RustItem rustItem, Player player) {
                super(rustItem, player);
            }
        }
    }
}
