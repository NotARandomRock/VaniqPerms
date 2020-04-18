package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Events.PlayerTags;
import net.vaniq.vaniqperms.FileManager.Permissions;
import net.vaniq.vaniqperms.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRank implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        if (sender.hasPermission("vaniq.deleterank")) {

            if (args.length == 0) {
                sender.sendMessage("§cUsage: /deleterank (Rank)");
                return true;
            }


            try {

                PreparedStatement insert = Main.getConnection().prepareStatement("DELETE FROM " + Main.tablerank + " WHERE RANKS=?");
                insert.setString(1, args[0].toUpperCase());
                insert.executeUpdate();

                sender.sendMessage("§7You have deleted the rank §c" + args[0].toUpperCase());

                for (Player p : Main.plugin.getServer().getOnlinePlayers()) {
                    PlayerTags.NT(p);
                    Permissions.setUPPlayer(p);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }

        return false;
    }
}