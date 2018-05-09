package com.isnakebuzz.ranksync.a;

import com.isnakebuzz.ranksync.b.MySQL;
import com.isnakebuzz.ranksync.b.Settings;
import com.isnakebuzz.ranksync.c.Cmds;
import com.isnakebuzz.ranksync.c.Listeners;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static MySQL mysql;
    public static File f;
    public static YamlConfiguration settings;
    private Listeners liste;
    private Cmds cmd;

    public Main() {
        this.liste = new Listeners(this);
        this.cmd = new Cmds(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.plugin = this;
        Settings.get().a(plugin);
        f = new File(this.getDataFolder() + "//settings.yml");
        settings = YamlConfiguration.loadConfiguration(f);
        liste.init();
        cmd.init();
        this.mysqlinit();
        Main.mysql = new MySQL();
        MySQL.connect();
        MySQL.update("CREATE TABLE IF NOT EXISTS RankSync (Name VARCHAR(100), Rank text)");
        MySQL.connect();
    }

    public YamlConfiguration getSettings() {
        return settings;
    }


    public void mysqlinit() {
        final File file = new File(this.getDataFolder().getPath(), "settings.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        MySQL.host = config.getString("MySQL.HostName");
        MySQL.port = config.getString("MySQL.Port");
        MySQL.database = config.getString("MySQL.Database");
        MySQL.username = config.getString("MySQL.Username");
        MySQL.password = config.getString("MySQL.Password");
    }
}
