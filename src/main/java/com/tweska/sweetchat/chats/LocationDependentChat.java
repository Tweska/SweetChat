package com.tweska.sweetchat.chats;

import org.bukkit.entity.Player;

/** Chat mode where recipients is based on location. */
public abstract class LocationDependentChat implements Chat {
    String prefix;

    /** Constructor without a prefix specified. */
    public LocationDependentChat() {
        this("");
    }

    /** Constructor with a prefix specified. */
    public LocationDependentChat(String prefix) {
        this.prefix = prefix;
    }

    /** Subclasses will have to implement the sendMessage function. */
    public abstract void sendMessage(Player sender, String message);
}
