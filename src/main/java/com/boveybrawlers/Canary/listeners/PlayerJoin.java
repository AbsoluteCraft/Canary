package com.boveybrawlers.Canary.listeners;

import com.boveybrawlers.Canary.Canary;
import com.boveybrawlers.Canary.lib.CanaryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Canary plugin;

    public PlayerJoin(Canary plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CanaryPlayer canaryPlayer = new CanaryPlayer(this.plugin, player);

        player.setDisplayName(canaryPlayer.getDisplayName());
    }

}
