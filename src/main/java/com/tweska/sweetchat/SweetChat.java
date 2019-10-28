package com.tweska.sweetchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tweska.sweetchat.chats.GlobalChat;
import com.tweska.sweetchat.chats.LocalChat;
import com.tweska.sweetchat.commands.GlobalChatCommand;
import com.tweska.sweetchat.commands.LocalChatCommand;
import com.tweska.sweetchat.commands.NickCommand;
import com.tweska.sweetchat.listeners.ChatListener;
import com.tweska.sweetchat.util.ChatMode;

import com.tweska.sweetchat.util.Nickname;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/** Entry point of the plugin. */
public class SweetChat extends JavaPlugin {
    /* GsonBuilder will build pretty JSON files. */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /* File where the nicknames will be stored. */
    private File nicknameConfigFile;

    /* Grab the instances of the available singleton chat modes. */
    private GlobalChat globalChat = GlobalChat.getInstance();
    private LocalChat localChat = LocalChat.getInstance();

    /* The current chat modes of each players are stored in a  hash map. */
    private HashMap<Player, ChatMode> playerChatMode = new HashMap<Player, ChatMode>();

    /** Gets triggered when the plugin is enabled. */
    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        /* Grab the plugin manager. */
        PluginManager pluginManager = getServer().getPluginManager();

        /* Get the file containing the nicknames or create it if it doesn't exist. */
        nicknameConfigFile = new File(getDataFolder(), "nicknames.json");
        if (!nicknameConfigFile.exists()) {
            getLogger().info("File 'nicknames.json' does not exist. Creating it for you...");
            saveResource(nicknameConfigFile.getName(), false);
        }

        /* Load the nicknames. */
        try {
            Nickname.nicknameMap = gson.fromJson(new FileReader(nicknameConfigFile), new HashMap<String, String>().getClass());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            pluginManager.disablePlugin(this);
            return;
        }

        /* Do some actions for each online player. */
        for(Player player : getServer().getOnlinePlayers()) {
            /* Update the nickname of the player. */
            Nickname.updateNickname(player);
        }

        /* Register all event listeners of this plugin. */
        pluginManager.registerEvents(new ChatListener(this, playerChatMode), this);
        pluginManager.registerEvents(globalChat, this);
        pluginManager.registerEvents(localChat, this);

        /* Register all commands of this plugin. */
        this.getCommand("lc").setExecutor(new LocalChatCommand(playerChatMode));
        this.getCommand("gc").setExecutor(new GlobalChatCommand(playerChatMode));
        this.getCommand("nick").setExecutor(new NickCommand());
    }

    @Override
    public void onDisable() {
        /* Convert the map of nicknames into JSON and save the file. */
        final String json = gson.toJson(Nickname.nicknameMap);
        nicknameConfigFile.delete();
        try {
            Files.write(nicknameConfigFile.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
