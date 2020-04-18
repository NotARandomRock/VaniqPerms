package net.vaniq.vaniqperms.Commands;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.FileManager.Permissions;
import net.vaniq.vaniqperms.FileManager.PlayerData;
import net.vaniq.vaniqperms.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AddRank implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

        if (sender.hasPermission("vaniq.addrank")) {

            if (args.length == 0) {
                sender.sendMessage("§cUsage: /addrank (Rank)");
                return true;
            }
                PlayerData.RankRegister(args[0].toUpperCase(), (Player) sender);

            ArrayList<String> list = (ArrayList<String>) Main.config.getStringList("Ranks.");
            list.add(args[0].toUpperCase());


        } else {

            sender.sendMessage(ChatUtil.format(Main.config.getString("NoPerm")));
        }

        return false;
    }
}