package net.vaniq.vaniqperms;

import net.vaniq.vaniqperms.Events.ChatFormat;
import net.vaniq.vaniqperms.Events.JoinLeave;
import net.vaniq.vaniqperms.Events.PlayerTags;
import net.vaniq.vaniqperms.FileManager.Permissions;
import net.vaniq.vaniqperms.FileManager.PlayerData;


import net.vaniq.vaniqperms.Commands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main
        extends JavaPlugin
        implements Listener {
    public static FileConfiguration config;
    public static Main plugin;
    private static Connection connection;
    public static String address;
    public static String database;
    public static String username;
    public static String password;
    public static String tabledata;
    public static String tablerank;
    public static int port;

    public void onEnable() {
        getLogger().info("Plugin Enabled | Plugin Developed by Rishon ");

        plugin = this;

        saveDefaultConfig();

        getDataFolder().mkdir();
        getDataFolder().mkdirs();
        Permissions.mk();

        Bukkit.getPluginManager().registerEvents(new ChatFormat(), this);
        Bukkit.getPluginManager().registerEvents(new JoinLeave(), this);

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        getCommand("addrank").setExecutor(new AddRank());
        getCommand("setprefix").setExecutor(new SetPrefix());
        getCommand("settag").setExecutor(new SetTag());
        getCommand("setchat").setExecutor(new SetChat());
        getCommand("ranks").setExecutor(new Ranks());
        getCommand("deleterank").setExecutor(new DeleteRank());
        getCommand("setrank").setExecutor(new SetRank());
        getCommand("addperm").setExecutor(new AddPerm());

        if(config.getBoolean("MySQL") == true) {
            loginmysql();
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                PlayerData.PlayerRegister(String.valueOf(p.getUniqueId()), p);
                PlayerTags.NT(p);

                Permissions.setUPPlayer(p);
            }

        } else if(config.getBoolean("MySQL") != false) {

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                PlayerTags.NT(p);
                getLogger().info("test");
                Permissions.setUPPlayer(p);
            }
        }

    }


    public void loginmysql() {
        address = config.getString("Address");
        database = config.getString("Database");
        username = config.getString("Username");
        password = config.getString("Password");
        port = config.getInt("Port");
        tabledata = config.getString("TableData");
        tablerank = config.getString("TableRank");

        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(
                        DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + database, username, password));

                Bukkit.getConsoleSender().sendMessage("§e--- MySQL has been connected ---");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§c--- MySQL failed to connect! Please check your settings  ---");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() { return connection; }


    public static void setConnection(Connection connection) { Main.connection = connection; }


    public void onDisable() { getLogger().info("Plugin Disabled | Plugin Developed by Rishon "); }
}
