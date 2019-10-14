package com.tweska.sweetchat;

import com.tweska.sweetchat.chats.GlobalChat;
import com.tweska.sweetchat.chats.LocalChat;
import com.tweska.sweetchat.commands.GlobalChatCommand;
import com.tweska.sweetchat.commands.LocalChatCommand;
import com.tweska.sweetchat.commands.NickCommand;
import com.tweska.sweetchat.listeners.ChatListener;
import com.tweska.sweetchat.util.ChatMode;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/** Entry point of the plugin. */
public class SweetChat extends JavaPlugin {
    /* Grab the instances of the available singleton chat modes. */
    private GlobalChat globalChat = GlobalChat.getInstance();
    private LocalChat localChat = LocalChat.getInstance();

    /* The current chat modes of each players are stored in a  hash map. */
    private HashMap<Player, ChatMode> playerChatMode = new HashMap<Player, ChatMode>();

    /** Gets triggered when the plugin is enabled. */
    @Override
    public void onEnable() {
        /* Grab the plugin manager. */
        PluginManager pluginManager = getServer().getPluginManager();

        /* Register all event listeners of this plugin. */
        pluginManager.registerEvents(new ChatListener(this, playerChatMode), this);
        pluginManager.registerEvents(globalChat, this);
        pluginManager.registerEvents(localChat, this);

        /* Register all commands of this plugin. */
        this.getCommand("lc").setExecutor(new LocalChatCommand(playerChatMode));
        this.getCommand("gc").setExecutor(new GlobalChatCommand(playerChatMode));
        this.getCommand("nick").setExecutor(new NickCommand());
    }
}
