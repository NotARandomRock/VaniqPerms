package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ranks implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        if (sender.hasPermission("vaniq.ranks")) {

            if(Main.config.getBoolean("MySQL") == true) {

                try {
                    Statement statement = Main.plugin.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(" select * from " + Main.tablerank + " ORDER BY (RANKS) ");
                    while (resultSet.next()) {
                        String RANKS = resultSet.getString("RANKS");
                        String PREFIX = resultSet.getString("PREFIX");
                        String TAG = resultSet.getString("TAG");
                        String CHAT = resultSet.getString("CHAT");

                        sender.sendMessage("§6§m---------------\n§e" + RANKS + " §7- " + ChatColor.translateAlternateColorCodes('&', PREFIX) + " §7| " + ChatColor.translateAlternateColorCodes('&', TAG));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if(Main.config.getBoolean("MySQL") == false) {

                sender.sendMessage(Main.config.getString("Ranks."));

            }

        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }
        return false;
    }
}