package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Events.PlayerTags;
import net.vaniq.vaniqperms.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SetRank
        implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String lebal, String[] args) {
        if (sender.hasPermission("vaniq.setrank")) {

            if (args.length == 0) {
                sender.sendMessage("§cUsage: /setrank (Player) (Rank)");
                return true;
            }

            String player = args[0];

            OfflinePlayer target = Bukkit.getOfflinePlayer(player);
            UUID uuid = target.getUniqueId();

            if (args.length == 1) {
                sender.sendMessage("§cUsage: /setrank (Player) (Rank)");
                return true;
            }

                    try {
                        PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
                        statement1.setString(1, args[1].toUpperCase());
                        ResultSet results = statement1.executeQuery();
                        results.next();
                        if (results.getString("RANKS").contains(args[1].toUpperCase())) {

                            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("UPDATE " + Main.tabledata + " SET ROLE=? WHERE UUID=?");
                            statement.setString(1, args[1].toUpperCase());
                            statement.setString(2, uuid.toString());
                            statement.executeUpdate();

                            sender.sendMessage("§7You have set the player §d" + target.getName() + " §7rank to §e" + ChatColor.translateAlternateColorCodes('&', args[1].toUpperCase()));

                            for (Player p : Main.plugin.getServer().getOnlinePlayers()) {
                                PlayerTags.NT(p);
                            }
                        }

                        } catch(SQLException e){
                        sender.sendMessage("§7That rank doesn't exist. Try again.");
                        }

        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }
        return false;
    }
}