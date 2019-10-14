package com.tweska.sweetchat.events;

import org.bukkit.entity.Player;

public class GlobalChatEvent extends SweetChatEvent {
    public GlobalChatEvent(Player sender, String message) {
        super(sender, message);
    }
}
