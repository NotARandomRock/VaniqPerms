package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Events.PlayerTags;
import net.vaniq.vaniqperms.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetChat
        implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        Player p = (Player)sender;

        if (sender.hasPermission("vaniq.setchat")) {

            if (args.length == 0) {
                p.sendMessage("§cUsage: /setchat (Rank) (Color)");
                return true;
            }

            String rank = args[0].toUpperCase();

            if (args.length == 1) {
                p.sendMessage("§cUsage: /setchat (Rank) (Color)");
                return true;
            }


            try {

                PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
                statement1.setString(1, rank);
                ResultSet results = statement1.executeQuery();
                results.next();

                    PreparedStatement statement = Main.plugin.getConnection().prepareStatement("UPDATE " + Main.tablerank + " SET chat=? WHERE RANKS=?");
                    statement.setString(1, args[1]);
                    statement.setString(2, String.valueOf(rank).toUpperCase());
                    statement.executeUpdate();

                    p.sendMessage("§7You have set the rank §d" + rank.toUpperCase() + " §7chat-color to §f" + args[1]);

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