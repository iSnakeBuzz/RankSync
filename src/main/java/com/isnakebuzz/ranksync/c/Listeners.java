package com.isnakebuzz.ranksync.c;

import com.isnakebuzz.ranksync.a.Main;
import com.isnakebuzz.ranksync.b.API;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
    
    private Main plugin;
    
    public Listeners(Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void CreatePlayer(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!API.playerExists(p.getName())){
            API.createPlayer(p.getName());
        }
    }
    
    @EventHandler
    public void JoinPlayer(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String p2 = p.getName();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            API.sendSet(p2);
        }, 20);
    }
    
    public void init(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
