package com.tweska.sweetchat.chats;

import com.tweska.sweetchat.events.GlobalChatEvent;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.ArrayList;

public class GlobalChat extends LocationIndependentChat implements Listener {

    /** Singleton class needs to have a reference to the instance of this class. */
    private static GlobalChat globalChat = new GlobalChat();

    /** Singleton class should not be instantiated somewhere else. */
    private GlobalChat() {
        super();
    }

    /** Get the instance of this singleton class. */
    public static GlobalChat getInstance() {
        return globalChat;
    }

    /** When the plugin is enabled the current list of online players is set as the list of recipients of this chat. */
    @EventHandler
    public void onPluginEnableEvent(PluginEnableEvent event) {
        Server server = event.getPlugin().getServer();
        setRecipients(new ArrayList<Player>(server.getOnlinePlayers()));
    }

    /** The join event is used to detect players joining the chat. This chat is the global chat so all online players
      * should be a recipient in this chat. */
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        /* Cancel the join message. */
        event.setJoinMessage("");

        /* Get the player that just joined. */
        Player player = event.getPlayer();

        /* Add the player as a recipients of this chat. */
        addRecipient(player);

        /* Create a new join message and send it in this chat. */
        String joinMessage = String.format("%s+%s %s", ChatColor.GREEN, ChatColor.RESET, player.getDisplayName());
        super.sendMessage(joinMessage);
    }

    /** the quit event is used to detect players leaving the chat. This chat is the global chat so only online players
      * should be a recipient in this chat. */
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        /* Cancel the quit message. */
        event.setQuitMessage("");

        /* Get the player that just quit. */
        Player player = event.getPlayer();

        /* Remove the player from the recipients of this chat. */
        removeRecipient(player);

        /* Create a new quit message and send it in this chat. */
        String quitMessage = String.format("%s-%s %s", ChatColor.RED, ChatColor.RESET, player.getDisplayName());
        super.sendMessage(quitMessage);
    }

    /** This chat listens for global chat messages. */
    @EventHandler
    public void onGlobalChatEvent(GlobalChatEvent event) {
        sendMessage(event.getSender(), event.getMessage());
    }
}
