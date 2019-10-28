package com.tweska.sweetchat.commands;

import com.tweska.sweetchat.util.Nickname;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Change your own or other players nick names. */
public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        /* This command is only intended for players and it needs the correct amount of arguments. */
        if(!(sender instanceof Player) || args.length < 1 || args.length > 2) {
            return false;
        }

        /* Define the two variables we need to know to set the nickname. */
        Player player;
        String newNickname;

        /* Determine the player instance and the new nickname. */
        if(args.length == 1 && sender.hasPermission("sweetchat.nick.self")) {
            player = (Player) sender;
            newNickname = args[0];
        } else if (args.length == 2 && sender.hasPermission("sweetchat.nick.others")) {
            player = Bukkit.getPlayer(args[0]);
            newNickname = args[1];
        } else {
            return false;
        }

        /* If no player was found a nickname can't be given. */
        if(player == null) {
            return false;
        }

        /* Set the new nickname of the player. */
        Nickname.setNickname(player, newNickname);

        /* Notify the player of the change. */
        player.sendMessage(String.format("You are now known as: %s", player.getDisplayName()));
        return true;
    }
}
