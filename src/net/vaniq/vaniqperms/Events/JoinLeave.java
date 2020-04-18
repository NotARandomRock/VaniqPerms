package net.vaniq.vaniqperms.Events;

import net.vaniq.vaniqperms.FileManager.Permissions;
import net.vaniq.vaniqperms.FileManager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class JoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        PlayerData.PlayerRegister(String.valueOf(p.getUniqueId()), p.getPlayer());

        PlayerTags.NT(p);

        Permissions.setUPPlayer(p);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        Permissions.permsplayer.remove(p.getUniqueId());

        PlayerTags.NT(p);

    }

}

