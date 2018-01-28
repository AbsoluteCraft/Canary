package com.boveybrawlers.Canary.commands;

import com.boveybrawlers.Canary.Canary;
import com.boveybrawlers.Canary.lib.CanaryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * View a player's real username
 */
public class WhoisCommand implements CommandExecutor {

    private Canary plugin;

    public WhoisCommand(Canary plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("canary.whois")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to view player info");
            return true;
        }

        if(args.length == 1) {
            String name = args[0];
            CanaryPlayer canaryPlayer = CanaryPlayer.findByUsername(this.plugin, name);
            if(canaryPlayer == null) {
                sender.sendMessage(ChatColor.RED + "That player does not exist");
                return true;
            }

//            String nick = Nickname.getNickname(this.plugin, player.getPlayer());
            sender.sendMessage(canaryPlayer.getPlayerData().get("username").toString());
            return true;
        }

        sender.sendMessage("Usage: /nick <nickname|username>");
        return true;
    }

}
