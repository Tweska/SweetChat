package com.tweska.sweetchat.commands;

import com.tweska.sweetchat.util.ChatMode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LocalChatCommand implements CommandExecutor {
    HashMap<Player, ChatMode> playerChatMode;

    public LocalChatCommand(HashMap<Player, ChatMode> playerChatMode) {
        this.playerChatMode = playerChatMode;
    }

    /** Switch chat mode for the player when the command is triggered. */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        /* This command is only intended for players. */
        if(!(sender instanceof Player)) {
            return false;
        }

        /* The sender is known to be a player. */
        Player player = (Player) sender;

        /* Set the chat mode for the player. */
        playerChatMode.put(player, ChatMode.LOCAL_CHAT);
        return true;
    }
}
