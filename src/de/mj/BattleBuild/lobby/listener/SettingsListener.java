package de.mj.BattleBuild.lobby.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import de.mj.BattleBuild.lobby.MySQL.SettingsAPI;
import de.mj.BattleBuild.lobby.Variabeln.Var;
import de.mj.BattleBuild.lobby.main.Lobby;
import de.mj.BattleBuild.lobby.utils.ItemCreator;
import de.mj.BattleBuild.lobby.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SettingsListener implements Listener {

    public static ArrayList<Player> silentstate = new ArrayList<Player>();
    public static ArrayList<Player> ridestate = new ArrayList<Player>();
    public static HashMap<Player, Short> design = new HashMap<Player, Short>();
    public static HashMap<Player, String> color = new HashMap<>();
    public static ArrayList<Player> jumppads = new ArrayList<Player>();
    public static ArrayList<Player> doppelsprung = new ArrayList<Player>();
    public static ArrayList<Player> waterjump = new ArrayList<Player>();

    public static ArrayList<Player> sfriends = new ArrayList<Player>();
    public static ArrayList<Player> srang = new ArrayList<Player>();
    public static ArrayList<Player> sserver = new ArrayList<Player>();
    public static ArrayList<Player> sclan = new ArrayList<Player>();
    public static ArrayList<Player> scoins = new ArrayList<Player>();
    public static ArrayList<Player> szeit = new ArrayList<Player>();
    public static ArrayList<Player> srealtime = new ArrayList<Player>();
    public static ArrayList<Player> sday = new ArrayList<Player>();
    public static ArrayList<Player> sweather = new ArrayList<Player>();

    static HashMap<Player, Inventory> invent1 = new HashMap<Player, Inventory>();
    static HashMap<Player, Inventory> invent2 = new HashMap<Player, Inventory>();
    static HashMap<Player, Inventory> invent3 = new HashMap<Player, Inventory>();
    static HashMap<Player, Inventory> score1 = new HashMap<Player, Inventory>();
    static HashMap<Player, Inventory> score2 = new HashMap<Player, Inventory>();

    String prefix = new Var().getPrefix();
    SettingsAPI sapi = new SettingsAPI();
    ItemCreator itemCreator = new ItemCreator();
    static ScoreboardManager scoreboardManager = new ScoreboardManager();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)
                || e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (p.getItemInHand().getType().equals(Material.REDSTONE_COMPARATOR) && p.getItemInHand().getItemMeta().getDisplayName().equals("§8\u00BB§6§lEinstellungen§8\u00AB")) {
                if (invent1.containsKey(p)) {
                    p.openInventory(invent1.get(p));
                } else {
                    setInv(p);
                    p.openInventory(invent1.get(p));
                }
            } else if (p.getInventory().getName().equals("§8§lMain Menu")) {
                p.getInventory().setItem(31, itemCreator.CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1,
                        "§9§lM§e§li§9§ln§e§li§9§lo§e§ln§9§ls", null));
            }
        }
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (p.getInventory().getTitle().contains("Main")) {
            p.getInventory().setItem(31, itemCreator.CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1,
                    "§9§lM§e§li§9§ln§e§li§9§lo§e§ln§9§ls", null));
        } else {

        }
    }

    public void setInv(Player p) {
        if (invent1.containsKey(p)) {
            invent1.remove(p);
        }
        p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
        Inventory inv = Bukkit.createInventory(null, 54, "§6§lEinstellungen");
        Inventory inv2 = Bukkit.createInventory(null, 54, "§6§lEinstellungen");
        Inventory inv3 = Bukkit.createInventory(null, 54, "§6§lEinstellungen");
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(p)) {
                inv.setItem(i,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv.setItem(i, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }
        ArrayList<String> designlore = new ArrayList<String>();
        designlore.add("§7Ändere das Design der Inventare und des Scoreboards in der Lobby");
        inv.setItem(9, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 0, 1, "§9§lDesign", designlore));
        if (design.containsKey(p)) {
            inv.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, design.get(p), 1, null, null));
        } else {
            inv.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 0, 1, null, null));
        }
        ArrayList<String> silentlore = new ArrayList<String>();
        silentlore.add("§7Mache dich anderen Spielern gegenüber unsichtbar und");
        silentlore.add("§7mache andere Spieler für dich unsichtbar");
        silentlore.add("§cDERZEIT NICHT VERFÜGBAR!");
        inv.setItem(18, itemCreator.CreateItemwithMaterial(Material.ARROW, 0, 1, "§2§lSilent-Lobby", silentlore));
        if (silentstate.contains(p)) {
            inv.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }
        ArrayList<String> hidelore = new ArrayList<String>();
        hidelore.add("§7Verstecke bestimmte Spielergruppen");
        inv.setItem(27, itemCreator.CreateItemwithMaterial(Material.BLAZE_ROD, 0, 1, "§6§lVerstecken", hidelore));
        ArrayList<String> ridelore = new ArrayList<String>();
        ridelore.add("§7Lasse Spieler auf dir reiten");
        inv.setItem(27, itemCreator.CreateItemwithMaterial(Material.TRIPWIRE_HOOK, 0, 1, "§3§lRide on me", ridelore));
        if (ridestate.contains(p)) {
            inv.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowRM = ArrowR.getItemMeta();
        ArrowRM.setDisplayName("§6Nächste Seite");
        ArrowR.setItemMeta(ArrowRM);
        SkullMeta SM = (SkullMeta) ArrowR.getItemMeta();
        SM.setOwner("MHF_ArrowRight");
        ArrowR.setItemMeta(SM);
        inv.setItem(44, ArrowR);
        inv.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l2", null));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(p)) {
                inv.setItem(a,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv.setItem(a, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }
        invent1.put(p, inv);

        // inv2
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(p)) {
                inv2.setItem(i,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv2.setItem(i, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        ArrayList<String> scorelore = new ArrayList<String>();
        scorelore.add("§7§ndere das Aussehen deines Scoreboards");
        inv2.setItem(9, itemCreator.CreateItemwithMaterial(Material.SIGN, 0, 1, "§5§lScorboard", scorelore));

        ArrayList<String> realtimelore = new ArrayList<String>();
        realtimelore.add("§7Wechsle zwischen Echtzeit, Tag oder Nacht in der Lobby");
        inv2.setItem(18, itemCreator.CreateItemwithMaterial(Material.WATCH, 0, 1, "§6§lEcht§9§lzeit", realtimelore));
        if (srealtime.contains(p)) {
            inv2.setItem(26,
                    itemCreator.CreateItemwithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit", null));
        } else if (sday.contains(p)) {
            inv2.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 4, 1, "§6Tag", null));
        } else {
            inv2.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 11, 1, "§9Nacht", null));
        }

        ArrayList<String> weatherlore = new ArrayList<String>();
        weatherlore.add("§7Wechsle zwichen Sonne oder Regen/Schnee");
        inv2.setItem(27, itemCreator.CreateItemwithMaterial(Material.WATER_BUCKET, 0, 1, "§b§lWetter", weatherlore));
        if (sweather.contains(p)) {
            inv2.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 4, 1, "§6Sonne", null));
        } else {
            inv2.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 11, 1, "§9Regen/Schnee", null));
        }

        inv2.setItem(44, ArrowR);
        inv.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l3", null));
        ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowLM = ArrowL.getItemMeta();
        ArrowLM.setDisplayName("§6Vorherige Seite");
        ArrowL.setItemMeta(ArrowLM);
        SkullMeta SLM = (SkullMeta) ArrowL.getItemMeta();
        SLM.setOwner("MHF_ArrowLeft");
        ArrowL.setItemMeta(SLM);
        inv2.setItem(36, ArrowL);
        inv3.setItem(36, ArrowL);
        inv2.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l2§6§l/§a§l3", null));
        inv3.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l3§6§l/§a§l3", null));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(p)) {
                inv2.setItem(a,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv2.setItem(a, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }
        invent2.put(p, inv2);

        // inv3

        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(p)) {
                inv3.setItem(i,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv3.setItem(i, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        ArrayList<String> jumppadlore = new ArrayList<String>();
        jumppadlore.add("§7Schalte JumpPads an oder aus");
        inv3.setItem(9, itemCreator.CreateItemwithMaterial(Material.GOLD_PLATE, 0, 1, "§5§lJumpPad", jumppadlore));
        if (jumppads.contains(p)) {
            inv3.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv3.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        ArrayList<String> doublejumplore = new ArrayList<String>();
        doublejumplore.add("§7Schalte DoubleJump an oder aus");
        inv3.setItem(18, itemCreator.CreateItemwithMaterial(Material.FEATHER, 0, 1, "§f§lDoubleJump", doublejumplore));
        if (doppelsprung.contains(p)) {
            inv3.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv3.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        ArrayList<String> liquidbouncelore = new ArrayList<String>();
        inv3.setItem(27,
                itemCreator.CreateItemwithMaterial(Material.LAVA_BUCKET, 0, 1, "§2§lJump on Liquid", liquidbouncelore));
        if (waterjump.contains(p)) {
            inv3.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv3.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(p)) {
                inv3.setItem(a,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv3.setItem(a, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }
        invent3.put(p, inv3);

    }

    @EventHandler
    public void SettingsMenue(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        scoreboardManager.setBoardLOBBY(p);
        try {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lSilent-Lobby")
                    && e.getCurrentItem().getType().equals(Material.ARROW)) {
                if (p.hasPermission("lobby.silent")) {
                    if (!silentstate.contains(p)) {
                        silentstate.add(p);
                        sapi.setSilent(p, true);
                        Inventory inv = invent1.get(p);
                        inv.setItem(26,
                                itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                        p.updateInventory();
                        p.sendMessage(prefix + "§aDu hast die §2Silent-Lobby §abetreten!");
                    } else {
                        sapi.setSilent(p, false);
                        silentstate.remove(p);
                        Inventory inv = invent1.get(p);
                        inv.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1,
                                "§cDEAKTIVIERT", null));
                        p.updateInventory();
                        p.sendMessage(prefix + "§cDu hast die §4Silent-Lobby §cverlassen!");
                    }
                } else {
                    p.sendMessage(prefix
                            + "§cDu benötigst mindestens den Rang VIP+, um dieses Feature nutzen zu können!");
                }
            } else if (e.getCursor().getType().equals(Material.BLAZE_ROD)
                    || e.getCurrentItem().getType().equals(Material.BLAZE_ROD) && !e.getCurrentItem().getItemMeta().getDisplayName().contains("Fun")) {
                try {
                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
                    p.performCommand("hide");
                } catch (Exception ex) {

                }
            } else if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS)) {
                p.openInventory(Design());
            } else if (e.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§3§lRide on me")) {
                if (ridestate.contains(p)) {
                    p.sendMessage(prefix + "§cDu hast das §4Ride on me §cFeature deaktiviert!");
                    ridestate.remove(p);
                    invent1.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setRide(p, false);
                    p.updateInventory();
                } else {
                    p.sendMessage(prefix + "§aDu hast das §2Ride on me §aFeature aktiviert!");
                    ridestate.add(p);
                    invent1.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setRide(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.WATCH)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lEcht§9§lzeit")) {
                if (p.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§6Echt§9zeit")) {
                    srealtime.remove(p);
                    sday.add(p);
                    invent2.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 4, 1, "§6Tag", null));
                    sapi.setRealTime(p, false, true);
                    p.updateInventory();
                } else if (p.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§6Tag")) {
                    sday.remove(p);
                    invent2.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 11, 1, "§9Nacht", null));
                    sapi.setRealTime(p, false, false);
                    p.updateInventory();
                } else if (p.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§9Nacht")){
                    srealtime.add(p);
                    invent2.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit", null));
                    sapi.setRealTime(p, true, false);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Nächste Seite")) {
                if (p.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l2§6§l/§a§l3")) {
                    p.openInventory(invent3.get(p));
                } else {
                    p.openInventory(invent2.get(p));
                }
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Vorherige Seite")) {
                if (p.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l3§6§l/§a§l3")) {
                    p.openInventory(invent2.get(p));
                } else {
                    p.openInventory(invent1.get(p));
                }
            } else if (e.getCurrentItem().getType().equals(Material.WATER_BUCKET)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lWetter")) {
                if (sweather.contains(p)) {
                    sweather.remove(p);
                    invent2.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 11, 1, "§9Regen/Schnee", null));
                    p.setPlayerWeather(WeatherType.DOWNFALL);
                    sapi.setWeather(p, false);
                    p.updateInventory();
                } else {
                    sweather.add(p);
                    invent2.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 4, 1, "§6Sonne", null));
                    p.setPlayerWeather(WeatherType.CLEAR);
                    sapi.setWeather(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.LAVA_BUCKET)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lJump on Liquid")) {
                if (waterjump.contains(p)) {
                    waterjump.remove(p);
                    invent3.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setWJUMP(p, false);
                    p.updateInventory();
                } else {
                    waterjump.add(p);
                    invent3.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setWJUMP(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.FEATHER)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lDoubleJump")) {
                if (doppelsprung.contains(p)) {
                    doppelsprung.remove(p);
                    invent3.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setDJUMP(p, false);
                    p.updateInventory();
                } else {
                    doppelsprung.add(p);
                    invent3.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setDJUMP(p, true);
                    p.updateInventory();
                }
            }
            if (p.getInventory().getName().equals("§8§lMain Menu")) {
                p.getInventory().setItem(31, itemCreator.CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1,
                        "§9§lM§e§li§9§ln§e§li§9§lo§e§ln§9§ls", null));
            }
        } catch (NullPointerException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void DesingMenue(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        try {
            if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS)
                    && !e.getCurrentItem().getItemMeta().getDisplayName().contains("Design")
                    && !e.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn")) {
                short i = e.getCurrentItem().getDurability();
                design.put(p, i);
                sapi.setColor(p, i);
                ItemColToString(p);
                p.closeInventory();
                new BukkitRunnable() {
                    int a = 1;

                    @Override
                    public void run() {
                        if (a > 0) {
                            setInv(p);
                            a--;
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Lobby.getPlugin(), 0L, 10L);
                setInv(p);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onScore(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        try {
            if (e.getCurrentItem().getTypeId() == 323
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lScorboard")) {
                if (score1.containsKey(p)) {
                    p.openInventory(score1.get(p));
                } else {
                    setScore(p);
                    p.openInventory(score1.get(p));
                }
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§aNächste Seite")) {
                p.openInventory(score2.get(p));
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§aVorherige Seite")) {
                p.openInventory(score1.get(p));
            } else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lFreunde")) {
                if (sfriends.contains(p)) {
                    sfriends.remove(p);
                    score1.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setFriends(p, false);
                    p.updateInventory();
                } else {
                    sfriends.add(p);
                    score1.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setFriends(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.GOLD_INGOT)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lCoins")) {
                if (scoins.contains(p)) {
                    scoins.remove(p);
                    score1.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setCoins(p, false);
                    p.updateInventory();
                } else {
                    scoins.add(p);
                    score1.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setCoins(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.REDSTONE)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRang")) {
                if (srang.contains(p)) {
                    srang.remove(p);
                    score1.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setRang(p, false);
                    p.updateInventory();
                } else {
                    srang.add(p);
                    score1.get(p).setItem(35,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setRang(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.ENDER_CHEST)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lClan")) {
                if (sclan.contains(p)) {
                    sclan.remove(p);
                    score2.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setClan(p, false);
                    p.updateInventory();
                } else {
                    sclan.add(p);
                    score2.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setClan(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.NETHER_STAR)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lServer")) {
                if (sserver.contains(p)) {
                    sserver.remove(p);
                    score2.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setServer(p, false);
                    p.updateInventory();
                } else {
                    sserver.add(p);
                    score2.get(p).setItem(26,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setServer(p, true);
                    p.updateInventory();
                }
            } else if (e.getCurrentItem().getType().equals(Material.GOLD_PLATE)
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lJumpPad")) {
                if (jumppads.contains(p)) {
                    jumppads.remove(p);
                    invent3.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setPJUMP(p, false);

                } else {
                    jumppads.add(p);
                    invent3.get(p).setItem(17,
                            itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setPJUMP(p, true);
                }
            } else if (e.getCurrentItem().getType().equals(Material.WATCH) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Online-Zeit")) {
                if (szeit.contains(p)) {
                    szeit.remove(p);
                    score2.get(p).setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
                    sapi.setTime(p, false);
                } else {
                    szeit.add(p);
                    score2.get(p).setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
                    sapi.setTime(p, true);
                }
            }
        } catch (NullPointerException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setScore(Player p) {
        // Score 1

        Inventory inv1 = Bukkit.createInventory(null, 54, "§9§lScoreboard");

        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(p)) {
                inv1.setItem(i,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv1.setItem(i, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        ItemStack player = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta playerm = player.getItemMeta();
        playerm.setDisplayName("§9§lFreunde");
        player.setItemMeta(playerm);
        SkullMeta playerSM = (SkullMeta) player.getItemMeta();
        playerSM.setOwner(p.getName());
        player.setItemMeta(playerSM);
        inv1.setItem(9, player);

        if (sfriends.contains(p)) {
            inv1.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv1.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        inv1.setItem(18, itemCreator.CreateItemwithMaterial(Material.GOLD_INGOT, 0, 1, "§6§lCoins", null));
        if (scoins.contains(p)) {
            inv1.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv1.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        inv1.setItem(27, itemCreator.CreateItemwithMaterial(Material.REDSTONE, 0, 1, "§c§lRang", null));
        if (srang.contains(p)) {
            inv1.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv1.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowRM = ArrowR.getItemMeta();
        ArrowRM.setDisplayName("§aNächste Seite");
        ArrowR.setItemMeta(ArrowRM);
        SkullMeta SM = (SkullMeta) ArrowR.getItemMeta();
        SM.setOwner("MHF_ArrowRight");
        ArrowR.setItemMeta(SM);
        inv1.setItem(44, ArrowR);
        inv1.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l2", null));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(p)) {
                inv1.setItem(a,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv1.setItem(a, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        score1.put(p, inv1);

        // Score2
        Inventory inv2 = Bukkit.createInventory(null, 54, "§9§lScoreboard");
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(p)) {
                inv2.setItem(i,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv2.setItem(i, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        inv2.setItem(9, itemCreator.CreateItemwithMaterial(Material.ENDER_CHEST, 0, 1, "§2§lClan", null));
        if (sclan.contains(p)) {
            inv2.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv2.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        inv2.setItem(18, itemCreator.CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§f§lServer", null));
        if (sserver.contains(p)) {
            inv2.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv2.setItem(26, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }

        inv2.setItem(27, itemCreator.CreateItemwithMaterial(Material.WATCH, 0, 1, "§6Online-Zeit", null));
        if (szeit.contains(p)) {
            inv2.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT", null));
        } else {
            inv2.setItem(35, itemCreator.CreateItemwithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT", null));
        }
        ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowLM = ArrowL.getItemMeta();
        ArrowLM.setDisplayName("§aVorherige Seite");
        ArrowL.setItemMeta(ArrowLM);
        SkullMeta SLM = (SkullMeta) ArrowL.getItemMeta();
        SLM.setOwner("MHF_ArrowLeft");
        ArrowL.setItemMeta(SLM);
        inv2.setItem(36, ArrowL);
        inv2.setItem(40, itemCreator.CreateItemwithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l2§6§l/§a§l2", null));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(p)) {
                inv2.setItem(a,
                        itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, design.get(p), 1, null, null));
            } else {
                inv2.setItem(a, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
            }
        }

        score2.put(p, inv2);

    }

    @EventHandler
    public void RideOnMe(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player horse = (Player) e.getRightClicked();
            Player rider = e.getPlayer();
            if (ridestate.contains(horse)) {
                horse.setPassenger(rider);
            } else {
                rider.sendMessage(prefix + "§cDer Spieler hat das Ride on me Feature nicht aktiviert!");
            }
        }
    }

    @EventHandler
    public void hitRider(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player rider = (Player) e.getEntity();
            Player hiter = (Player) e.getDamager();
            try {
                if (hiter.getPassenger().equals(rider)) {
                    Vector v = rider.getLocation().getDirection();
                    v.setY(1.2);
                    rider.leaveVehicle();
                    rider.setVelocity(v);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Inventory Design() {
        Inventory inv = Bukkit.createInventory(null, 18, "§9§lDesign");
        inv.setItem(1, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 0, 1, "§f§lWeiss", null));
        inv.setItem(2, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 1, 1, "§6§lOrange", null));
        inv.setItem(3, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 2, 1, "§5§lMagenta", null));
        inv.setItem(4, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 3, 1, "§b§lHellblau", null));
        inv.setItem(5, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 4, 1, "§e§lGelb", null));
        inv.setItem(6, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 5, 1, "§a§lHellgr§n", null));
        inv.setItem(7, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 6, 1, "§d§lPink", null));
        inv.setItem(9, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 7, 1, "§8§lDunkelgrau", null));
        inv.setItem(10, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 8, 1, "§7§lHellgrau", null));
        inv.setItem(11, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 9, 1, "§3§lAqua", null));
        inv.setItem(12, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 10, 1, "§5§lLila", null));
        inv.setItem(13, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 11, 1, "§9§lDunkelblau", null));
        inv.setItem(14, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 12, 1, "§fBraun", null));
        inv.setItem(15, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 13, 1, "§2§lDunkelgr§n", null));
        inv.setItem(16, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 14, 1, "§c§lRot", null));
        inv.setItem(17, itemCreator.CreateItemwithMaterial(Material.STAINED_GLASS, 15, 1, "§0§lSchwarz", null));
        return inv;
    }

    public static void ItemColToString(Player p) {
        if (design.containsKey(p)) {
            if (design.get(p) == 0) {
                color.put(p, "f");
            } else if (design.get(p) == 1) {
                color.put(p, "6");
            } else if (design.get(p) == 2) {
                color.put(p, "5");
            } else if (design.get(p) == 3) {
                color.put(p, "b");
            } else if (design.get(p) == 4) {
                color.put(p, "e");
            } else if (design.get(p) == 5) {
                color.put(p, "a");
            } else if (design.get(p) == 6) {
                color.put(p, "d");
            } else if (design.get(p) == 7) {
                color.put(p, "8");
            } else if (design.get(p) == 8) {
                color.put(p, "7");
            } else if (design.get(p) == 9) {
                color.put(p, "3");
            } else if (design.get(p) == 10) {
                color.put(p, "5");
            } else if (design.get(p) == 11) {
                color.put(p, "9");
            } else if (design.get(p) == 12) {
                color.put(p, "f");
            } else if (design.get(p) == 13) {
                color.put(p, "2");
            } else if (design.get(p) == 14) {
                color.put(p, "c");
            } else if (design.get(p) == 15) {
                color.put(p, "0");
            }
        } else {
            return;
        }
    }
}


