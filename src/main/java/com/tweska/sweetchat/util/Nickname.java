package com.tweska.sweetchat.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public abstract class Nickname {
    public static Map<String, String> nicknameMap;

    public static void updateNickname(Player player) {
        if (!player.isOnline() || nicknameMap == null) {
            return;
        }

        /* Get the UUID of this player. */
        UUID id = player.getUniqueId();

        if (!nicknameMap.containsKey(id.toString())) {
            return;
        }

        /* Get the raw nickname and format it. */
        String rawNickname = nicknameMap.get(id.toString());
        String formattedNickname = String.format("%s%s", ChatColor.translateAlternateColorCodes('&', rawNickname), ChatColor.RESET);


        /* Set the nickname as both the new display name as well as the player list name. */
        player.setDisplayName(formattedNickname);
        player.setPlayerListName(formattedNickname);
    }

    public static void setNickname(Player player, String nickname) {
        if (nicknameMap == null) {
            return;
        }

        /* Get the UUID of this player. */
        UUID id = player.getUniqueId();

        /* Add the new nickname of this player to our database. */
        nicknameMap.put(id.toString(), nickname);

        /* Update the nickname for the player. */
        updateNickname(player);
    }
}
