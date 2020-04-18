package net.vaniq.vaniqperms;

import org.bukkit.ChatColor;

public class ChatUtil
{
    public static String format(String message) { return ChatColor.translateAlternateColorCodes('&', message); }
}
