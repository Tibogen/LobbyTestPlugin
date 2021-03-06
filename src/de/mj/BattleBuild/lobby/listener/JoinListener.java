package de.mj.BattleBuild.lobby.listener;

import java.util.ArrayList;

import de.mj.BattleBuild.lobby.MySQL.SettingsAPI;
import de.mj.BattleBuild.lobby.main.Lobby;
import de.mj.BattleBuild.lobby.utils.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        SettingsListener settingsListener = new SettingsListener();
        ActionbarTimer actionbarTimer = new ActionbarTimer();
        ItemCreator itemCreator = new ItemCreator();
        ScoreboardManager scoreboardManager = new ScoreboardManager();
        TabList tabList = new TabList();
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            settingsListener.ridestate.add(p);
            settingsListener.color.put(p, "6");
            settingsListener.sclan.add(p);
            settingsListener.scoins.add(p);
            settingsListener.sfriends.add(p);
            settingsListener.srang.add(p);
            settingsListener.sserver.add(p);
            settingsListener.jumppads.add(p);
        }

        p.setGameMode(GameMode.ADVENTURE);
        actionbarTimer.action.put(p, false);
        e.setJoinMessage(null);
        SettingsAPI settingsAPI = new SettingsAPI();
        try {
            settingsAPI.createPlayer(p);
            settingsAPI.createScorePlayer(p);
            settingsAPI.getColor(p);
            settingsAPI.getSilent(p);
            settingsAPI.getRide(p);
            settingsAPI.getFriends(p);
            settingsAPI.getRang(p);
            settingsAPI.getServer(p);
            settingsAPI.getClan(p);
            settingsAPI.getCoins(p);
            settingsAPI.getRealTime(p);
            settingsAPI.getWeather(p);
            settingsAPI.getDoubleJump(p);
            settingsAPI.getWjump(p);
            settingsAPI.getJumPlate(p);
            settingsAPI.getTime(p);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i > 0) {
                    i--;
                } else {
                    if (settingsListener.sweather.contains(p)) {
                        p.setPlayerWeather(WeatherType.CLEAR);
                    } else {
                        p.setPlayerWeather(WeatherType.DOWNFALL);
                    }
                }
            }
        }.runTaskTimer(Lobby.getPlugin(), 0L, 20L * 5);

        p.getInventory().clear();
        tabList.setPrefix(p);

        p.getInventory().setItem(4,
                itemCreator.CreateItemwithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
        p.getInventory().setItem(1, itemCreator.CreateItemwithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                "§8\u00BB§6§lEinstellungen§8\u00AB", null));
        p.getInventory().setItem(7,
                itemCreator.CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
        p.getInventory().setItem(0, itemCreator.CreateItemwithMaterial(Material.ARMOR_STAND, 0 , 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
        is.setItemMeta(im);
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        sm.setOwner(p.getName());
        is.setItemMeta(sm);
        p.getInventory().setItem(8, is);

        p.teleport(new LocationsUtil().getSpawn());

        scoreboardManager.setBoardLOBBY(p);

        ArrayList<String> friends = new ArrayList<String>();
        PAFPlayer pafp = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
        for (PlayerObject all : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
            for (PAFPlayer fr : pafp.getFriends()) {
                if (fr.getUniqueId().equals(all.getUuid())) {
                    friends.add(all.getName());
                }
            }
        }
        if (friends.size() == 1) {
            IChatBaseComponent icb = ChatSerializer
                    .a("{\"text\":\"§6Derzeit ist folgender deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                            + friends
                            + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
            PacketPlayOutChat packet = new PacketPlayOutChat(icb);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }if (friends.size() > 1) {
            IChatBaseComponent icb = ChatSerializer
                    .a("{\"text\":\"§6Derzeit sind folgende deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                            + friends
                            + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
            PacketPlayOutChat packet = new PacketPlayOutChat(icb);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
