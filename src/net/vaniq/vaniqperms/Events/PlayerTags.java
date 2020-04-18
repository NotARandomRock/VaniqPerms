package net.vaniq.vaniqperms.Events;

import net.vaniq.vaniqperms.ChatUtil;
import net.vaniq.vaniqperms.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerTags {

    public static void NT(Player player) {



        try {

            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultset = statement.executeQuery();
            resultset.next();

            String RANK = resultset.getString("ROLE");

            PreparedStatement statement1 = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
            statement1.setString(1, RANK);
            ResultSet results = statement1.executeQuery();
            results.next();

            String TAG = results.getString("TAG");



            if (TAG == "") {
                player.setPlayerListName("§8" + player.getDisplayName());
                NT.playertagset(player, "§8", NTAction.CREATE);
            } else {
                NT.playertagset(player, ChatUtil.format(TAG + " §f"), NTAction.CREATE);
                player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', TAG) + " §f" + player.getDisplayName());
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }

}