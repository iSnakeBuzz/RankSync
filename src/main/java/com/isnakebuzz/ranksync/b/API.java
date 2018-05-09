package com.isnakebuzz.ranksync.b;

import com.isnakebuzz.ranksync.a.Main;
import java.sql.*;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class API{
    
    public static boolean playerExists(final String name) {
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM RankSync WHERE Name='" + name + "'");
            return rs.next() && rs.getString("Name") != null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void createPlayer(final String uuid) {
        if (!playerExists(uuid)) {
            MySQL.update("INSERT INTO RankSync (Name, Rank) VALUES ('" + uuid + "', '"+ Main.settings.getString("DefaultRank") +"');");
        }
    }
    
    public static void setRank(final String name, final String Rank) {
        if (playerExists(name)) {
            MySQL.update("UPDATE RankSync SET Rank='" + Rank + "' WHERE Name='" + name + "'");
        }
    }
    
    static String command = Main.settings.getString("SetRank");
    
    public static void sendSet(String p){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        command = command.replace("%group%", API.getRank(p))
        .replace("%name%", p);
        
        Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
            Bukkit.dispatchCommand(console, command);
        }, 20 * 10l);
    }
    
    public static void sendDelete(Player p){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = Main.settings.getString("ResetRank")
                .replaceAll("%name%", p.getName()
                .replaceAll("%default%", Main.settings.getString("DefaultRank"))
        );
        Bukkit.dispatchCommand(console, command);
    }
    
    public static String getRank(final String name) {
        String i = null;
        if (playerExists(name)) {
            try {
                final MySQL mysql = Main.mysql;
                final ResultSet rs = MySQL.query("SELECT * FROM RankSync WHERE Name='" + name + "'");
                if (rs.next()) {
                    rs.getString("Rank");
                }
                i = String.valueOf(rs.getString("Rank"));
            }
            catch (SQLException e) {
                Main.plugin.getLogger().log(Level.SEVERE, "Don't exist Player in the MySQL");
            }
        }
        return i;
    }
    
}
