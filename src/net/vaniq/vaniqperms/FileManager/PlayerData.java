package net.vaniq.vaniqperms.FileManager;

import net.vaniq.vaniqperms.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerData
        implements Listener
{


    public static boolean playerExists(String uuid) {
        try {
            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return results.getString("UUID") != null;
            }
            Bukkit.getConsoleSender().sendMessage("No data found for " + uuid);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean RankExists(String rank) {
        try {
            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
            statement.setString(1, rank.toUpperCase());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
            Bukkit.getConsoleSender().sendMessage("Rank already exists " + rank.toUpperCase());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void RankRegister(String rank, Player p) {
        try {
            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tablerank + " WHERE RANKS=?");
            statement.setString(1, rank.toUpperCase());
            ResultSet results = statement.executeQuery();
            results.next();
            if (RankExists(rank) != true) {
                PreparedStatement insert = Main.plugin.getConnection().prepareStatement("INSERT INTO " + Main.tablerank + " (RANKS, PREFIX, TAG, CHAT) VALUES (?,?,?,?)");
                insert.setString(1, rank.toUpperCase());
                insert.setString(2, "");
                insert.setString(3, "");
                insert.setString(4, "f");
                insert.executeUpdate();

                p.sendMessage("§7You have created the rank §e" + rank.toUpperCase());

                Bukkit.getConsoleSender().sendMessage("Generated new rank data " + rank.toUpperCase());
            } else {
                p.sendMessage("§7This rank already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void PlayerRegister(String uuid, OfflinePlayer player) {
        try {
            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();
            results.next();
            if (playerExists(uuid) != true) {
                PreparedStatement insert = Main.plugin.getConnection().prepareStatement("INSERT INTO " + Main.tabledata + " (IGN, UUID, ROLE) VALUES (?,?,?)");
                insert.setString(1, player.getName());
                insert.setString(2, uuid);
                insert.setString(3, Main.config.getString("Default-Rank"));
                insert.executeUpdate();

                Bukkit.getConsoleSender().sendMessage("Generated new player data for " + player.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
