package ru.hld.rustitems.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import ru.hld.rustitems.items.RustItemManager;

public class RegisterRustItemsEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RustItemManager rustItemManager;
    public RegisterRustItemsEvent(RustItemManager rustItemManager) {
        this.rustItemManager = rustItemManager;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RustItemManager getRustItemManager() {
        return rustItemManager;
    }

    public static class Pre extends RegisterRustItemsEvent {

        public Pre(RustItemManager rustItemManager) {
            super(rustItemManager);
        }
    }

    public static class Post extends RegisterRustItemsEvent {

        public Post(RustItemManager rustItemManager) {
            super(rustItemManager);
        }
    }
}
