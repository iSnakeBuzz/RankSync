package com.isnakebuzz.ranksync.c;

import com.isnakebuzz.ranksync.a.Main;
import com.isnakebuzz.ranksync.b.API;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmds implements CommandExecutor{
    private final Main plugin;

    public Cmds(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        plugin.getCommand("setrank").setExecutor(this);
        plugin.getCommand("resetrank").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
        if (!p.hasPermission("syncrank.use")){
            p.sendMessage("This command protected");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("setrank")){
            if (args.length < 2){
                p.sendMessage("§cPlease use §e/setrank [Player] [Rank]");
                return true;
            }
            String p2 = args[0];
            String rank = args[1];
            
            API.setRank(p2, rank);
            
            p.sendMessage(c(plugin.getSettings().getString("Message")
                    .replaceAll("%player%", p2)
                    .replaceAll("%rank%", API.getRank(p2))
            ));
            API.sendSet(p2);
            
        }
        
        if (cmd.getName().equalsIgnoreCase("resetrank")){
            if (args.length < 1){
                p.sendMessage("§cPlease use §e/resetrank [Player]");
                return true;
            }
            String p2 = args[0];
            String rank = Main.settings.getString("DefaultRank");
            
            API.setRank(p2, rank);
            
            p.sendMessage(c(plugin.getSettings().getString("Message")
                    .replaceAll("%player%", p2)
                    .replaceAll("%rank%", rank)
            ));
            API.sendSet(p2);
            
        }
        return false;
    }
    
    private String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
