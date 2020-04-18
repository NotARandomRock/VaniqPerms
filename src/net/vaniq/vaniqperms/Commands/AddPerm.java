package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPerm implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        if (sender.hasPermission("vaniq.addperm")) {



            if (args.length == 0) {
                sender.sendMessage("§cUsage: /addperm (Rank) (Permission)");
                return true;
            }

            if (args.length == 1) {
                sender.sendMessage("§cUsage: /setprefix (Rank) (Permission)");
                return true;
            }

            try {

                PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
                statement1.setString(1, args[0].toUpperCase());
                ResultSet results = statement1.executeQuery();
                results.next();

                    sender.sendMessage("§7You have added the rank §e" + args[0].toUpperCase() + " §7The permission §b" + args[1]);


                } catch (SQLException e) {
                sender.sendMessage("§7That rank doesn't exist. Try again.");
                }


        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }

        return false;
    }
}