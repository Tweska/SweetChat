package com.tweska.sweetchat.chats;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/** Chat mode for which location doesn't matter. */
public abstract class LocationIndependentChat implements Chat {
    private List<Player> recipients;
    private String prefix;

    /** Constructor without both a prefix and player list specified. */
    public LocationIndependentChat() {
        this("");
    }

    /** Constructor with a prefix but not a player list specified. */
    public LocationIndependentChat(String prefix) {
        this(prefix, new ArrayList<Player>());
    }

    /** Constructor with both a prefix and a player list specified. */
    public LocationIndependentChat(String prefix, List<Player> recipients) {
        this.recipients = recipients;
        this.prefix = prefix;
    }

    /** Send a message to all recipients of this chat. */
    public void sendMessage(String message) {
        for(Player player : recipients) {
            player.sendMessage(String.format("%s%s", prefix, message));
        }
    }

    /** Send a message with a sender to all recipients of this chat. */
    public void sendMessage(Player sender, String message) {
        sendMessage(String.format("%s: %s", sender.getDisplayName(), message));
    }

    /** Add a single recipient to this chat. */
    public void addRecipient(Player player) {
        recipients.add(player);
    }

    /** Add multiple recipients to this chat. */
    public void addRecipients(List<Player> players) {
        recipients.addAll(players);
    }

    /** Replace all recipients with a new list of recipients. */
    public void setRecipients(List<Player> players) {
        clearRecipients();
        addRecipients(players);
    }

    /** Remove a single recipient from this chat. */
    public void removeRecipient(Player player) {
        recipients.remove(player);
    }

    /** Remove all recipients from this chat. */
    public void clearRecipients() {
        recipients.clear();
    }

    /** Get a list of all recipients of this chat. */
    public List<Player> getRecipients() {
        return new ArrayList<Player>(recipients);
    }

    /** Check if a recipient is part of this chat. */
    public boolean hasRecipient(Player player) {
        return recipients.contains(player);
    }

    /** Get the number of recipients in this class. */
    public int getSize() {
        return recipients.size();
    }
}