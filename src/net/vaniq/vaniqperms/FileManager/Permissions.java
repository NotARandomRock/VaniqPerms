package net.vaniq.vaniqperms.FileManager;

import net.vaniq.vaniqperms.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Permissions implements Listener {
    public static YamlConfiguration getConfig() {
        File file = new File(Main.plugin.getDataFolder().getPath(), "Permissions.yml");
        return YamlConfiguration.loadConfiguration(file);
    }


    private static void save(YamlConfiguration config) {
        File file = new File(Main.plugin.getDataFolder().getPath(), "Permissions.yml");
        try {

            config.save(file);
        } catch (IOException e) {
            return;
        }
    }

    public static void edit(String path, Object value) {
        YamlConfiguration config = getConfig();
        config.set(path, value);
        save(config);
    }

    public static HashMap<UUID, PermissionAttachment> permsplayer = new HashMap<>();

    public static void setUPPlayer(Player player) {
        PermissionAttachment attachment = player.addAttachment(Main.plugin);
        permsplayer.put(player.getUniqueId(), attachment);

        try {
            PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultset = statement.executeQuery();
            resultset.next();

            String RANK = resultset.getString("ROLE");

            for (String subrank : getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                for (String permissions : getConfig().getStringList("Ranks." + subrank + ".Permissions")) {
                    if (RANK.contains(subrank)) {
                        setUPUUIDPerms(player.getUniqueId());
                    }

                    if (player.isOp()) {
                        setUPUUIDPerms(player.getUniqueId());
                    }
                }
            }

        } catch (SQLException e) {
            return;
        }
    }

    public static void setUPUUIDPerms(UUID uuid) {
        PermissionAttachment attachment = permsplayer.get(uuid);

        try {

        PreparedStatement statement = Main.plugin.getConnection().prepareStatement("SELECT * FROM " + Main.tabledata + " WHERE UUID=?");
        statement.setString(1, uuid.toString());
        ResultSet resultset = statement.executeQuery();
        resultset.next();

        String RANK = resultset.getString("ROLE");

        for (String subrank : getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            for (String permissions : getConfig().getStringList("Ranks." + subrank + ".Permissions")) {

                if (RANK.contains(subrank)) {
                    attachment.setPermission(permissions, true);
                }


            }
        }


    } catch (SQLException e) {
            return;
    }

}




    public static void mk() {
        File file = new File(Main.plugin.getDataFolder().getPath(), "Permissions.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }

            edit("Ranks.", "Owner" + ".Permissions");

        }
    }
}
