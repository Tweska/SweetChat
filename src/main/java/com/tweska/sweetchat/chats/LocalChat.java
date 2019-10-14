package com.tweska.sweetchat.chats;

import com.tweska.sweetchat.events.LocalChatEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/** Chat mode where messages get only send to nearby players. */
public class LocalChat extends LocationDependentChat implements Listener {
    private final double radius = 200.0;

    /** Singleton class needs to have a reference to the instance of this class. */
    private static LocalChat localChat = new LocalChat(String.format("[%sL%s] ", ChatColor.GOLD, ChatColor.RESET));

    /** Singleton class should not be instantiated somewhere else. */
    private LocalChat(String prefix) {
        super(prefix);
    }

    /** Get the instance of this singleton class. */
    public static LocalChat getInstance() {
        return localChat;
    }

    /** Send a message to all nearby players relative to the the sender. */
    @Override
    public void sendMessage(Player sender, String message) {

        /* Format the message correctly. */
        String formattedMessage = String.format("%s%s: %s", prefix, sender.getDisplayName(), message);

        /* Loop over all players and check if they are nearby. */
        boolean playersNearby = false;
        for(Player player : Bukkit.getOnlinePlayers()) {
            /* The sender doesn't have to be checked. */
            if (player == sender) {
                continue;
            }

            /* Check if the other player is nearby. */
            if(sender.getLocation().distance(player.getLocation()) > radius) {
                continue;
            }

            /* A nearby player is found, send the message. */
            playersNearby = true;
            player.sendMessage(formattedMessage);
        }

        /* Let the player know if he is alone. */
        if (!playersNearby) {
            sender.sendMessage(String.format("%sYou are alone out here...%s", ChatColor.ITALIC, ChatColor.RESET));
            return;
        }

        /* Also send the message to the sender as a confirmation. */
        sender.sendMessage(formattedMessage);
    }

    /** This chat listens for local chat messages. */
    @EventHandler
    public void onLocalChatEvent(LocalChatEvent event) {
        sendMessage(event.getSender(), event.getMessage());
    }
}
