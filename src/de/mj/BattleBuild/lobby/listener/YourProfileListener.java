package de.mj.BattleBuild.lobby.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class YourProfileListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§9§lDein Profil§8\u00AB")) {
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
                p.performCommand("friendsgui");
            }
        } catch (Exception ex) { }
    }
}
