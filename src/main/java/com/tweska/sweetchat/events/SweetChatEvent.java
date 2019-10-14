package com.tweska.sweetchat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SweetChatEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Player sender;
    private String message;

    public SweetChatEvent(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getSender() {
        return sender;
    }

    public String getMessage() {
        return  message;
    }
}
