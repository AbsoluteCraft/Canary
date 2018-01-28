package com.boveybrawlers.Canary;

import com.boveybrawlers.Canary.commands.NicknameCommand;
import com.boveybrawlers.Canary.commands.WhoisCommand;
import com.boveybrawlers.Canary.listeners.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;


public class Canary extends JavaPlugin {

    public Stores store;

    public void onEnable() {
        this.saveDefaultConfig();
        this.createStores();

        this.registerCommands();
        this.registerListeners();
    }

    private void createStores() {
        this.store = new Stores(this);
    }

    private void registerCommands() {
        this.getCommand("nick").setExecutor(new NicknameCommand(this));
        this.getCommand("whois").setExecutor(new WhoisCommand(this));
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
    }

}
