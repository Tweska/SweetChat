package com.tweska.sweetchat.events;

import org.bukkit.entity.Player;

public class LocalChatEvent extends SweetChatEvent {
    public LocalChatEvent(Player sender, String message) {
        super(sender, message);
    }
}
