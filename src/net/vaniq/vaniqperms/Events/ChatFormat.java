package net.vaniq.vaniqperms.Events;


import net.vaniq.vaniqperms.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatFormat implements Listener {

    @EventHandler
    public void playerformat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

                try {
                    PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet resultset = statement.executeQuery();
                    resultset.next();

                    String RANK = resultset.getString("ROLE");


                    PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
                    statement1.setString(1, RANK);
                    ResultSet results = statement1.executeQuery();
                    results.next();

                    String PREFIX = results.getString("PREFIX");

                    String CHAT = results.getString("CHAT");


                    if (PREFIX == "") {
                        event.setFormat("§8" + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "§" + CHAT + event.getMessage());

                    } else {
                        event.setFormat(ChatColor.translateAlternateColorCodes('&', PREFIX) + " §f" + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "§" + CHAT + event.getMessage());
                    }


                    if (p.hasPermission("strix.color")) {
                        if (PREFIX == "") {
                            event.setFormat("§8" + p.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', "§" + CHAT + event.getMessage()));
                        } else {
                            event.setFormat(ChatColor.translateAlternateColorCodes('&', PREFIX) + " §f" + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', "§" + CHAT + event.getMessage()));
                        }
                    }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            }

    }

