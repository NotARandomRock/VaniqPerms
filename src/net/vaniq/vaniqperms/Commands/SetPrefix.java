package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Events.PlayerTags;
import net.vaniq.vaniqperms.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetPrefix
        implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        Player p = (Player)sender;

        if (sender.hasPermission("vaniq.setprefix")) {

            if (args.length == 0) {
                p.sendMessage("§cUsage: /setprefix (Rank) (Prefix)");
                return true;
            }

            String rank = args[0].toUpperCase();

            if (args.length == 1) {
                p.sendMessage("§cUsage: /setprefix (Rank) (Prefix)");
                return true;
            }

            StringBuilder prefix = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                prefix.append(args[1] + " ");
            }

            try {

                PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
                statement1.setString(1, rank);
                ResultSet results = statement1.executeQuery();
                results.next();

                    PreparedStatement statement = Main.plugin.getConnection().prepareStatement("UPDATE " + Main.tablerank + " SET prefix=? WHERE RANKS=?");
                    statement.setString(1, prefix.toString().trim());
                    statement.setString(2, String.valueOf(rank).toUpperCase());
                    statement.executeUpdate();

                    p.sendMessage("§7You have set the rank §d" + rank.toUpperCase() + " §7prefix to §f" + ChatColor.translateAlternateColorCodes('&', prefix.toString().trim()));

                for (Player all : Main.plugin.getServer().getOnlinePlayers()) {
                    PlayerTags.NT(all);
                }

            }
            catch (SQLException i) {
                SQLException e = null; e.printStackTrace();
            }

        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }

        return false;
    }
}